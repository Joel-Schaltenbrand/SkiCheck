package ch.masterplan.skicheck.views.admin;

import ch.masterplan.skicheck.data.User;
import ch.masterplan.skicheck.services.UserService;
import ch.masterplan.skicheck.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Optional;

@PageTitle("Admin")
@Route(value = "admin/:personID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class AdminView extends Div implements BeforeEnterObserver {

	private final String PERSON_ID = "personID";
	private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "admin/%s/edit";

	private final Grid<User> grid = new Grid<>(User.class, false);
	private final Button cancel = new Button("Abbrechen");
	private final Button save = new Button("Speichern");
	private final BeanValidationBinder<User> binder;
	private final UserService userService;
	private TextField firstName;
	private TextField lastName;
	private TextField email;
	private TextField phoneNumber;
	private Checkbox hasEquipment;
	private Checkbox hasPaid;
	private User user;

	public AdminView(UserService userService) {
		this.userService = userService;
		addClassNames("admin-view");

		// Create UI
		SplitLayout splitLayout = new SplitLayout();

		createGridLayout(splitLayout);
		createEditorLayout(splitLayout);

		add(splitLayout);

		// Configure Grid
		grid.addColumn("firstName").setAutoWidth(true);
		grid.addColumn("lastName").setAutoWidth(true);
		grid.addColumn("email").setAutoWidth(true);
		grid.addColumn("phoneNumber").setAutoWidth(true);
		LitRenderer<User> paid = LitRenderer.<User>of("<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>").withProperty("icon", pd -> pd.isHasPaid() ? "check" : "minus").withProperty("color", pd -> pd.isHasPaid() ? "var(--lumo-primary-text-color)" : "var(--lumo-disabled-text-color)");

		grid.addColumn(paid).setHeader("Gezahlt").setAutoWidth(true);

		LitRenderer<User> equipment = LitRenderer.<User>of("<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>").withProperty("icon", eq -> eq.isHasEquipment() ? "check" : "minus").withProperty("color", eq -> eq.isHasEquipment() ? "var(--lumo-primary-text-color)" : "var(--lumo-disabled-text-color)");

		grid.addColumn(equipment).setHeader("Ausrüstung vorhanden").setAutoWidth(true);

		grid.setItems(query -> userService.list(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

		// when a row is selected or deselected, populate form
		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null) {
				UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
			} else {
				clearForm();
				UI.getCurrent().navigate(AdminView.class);
			}
		});

		// Configure Form
		binder = new BeanValidationBinder<>(User.class);

		// Bind fields. This is where you'd define e.g. validation rules

		binder.bindInstanceFields(this);

		cancel.addClickListener(e -> {
			clearForm();
			refreshGrid();
		});

		save.addClickListener(e -> {
			try {
				if (this.user == null) {
					this.user = new User();
				}
				binder.writeBean(this.user);
				userService.update(this.user);
				clearForm();
				refreshGrid();
				Notification.show("Data updated");
				UI.getCurrent().navigate(AdminView.class);
			} catch (ObjectOptimisticLockingFailureException exception) {
				Notification n = Notification.show("Error updating the data. Somebody else has updated the record while you were making changes.");
				n.setPosition(Position.MIDDLE);
				n.addThemeVariants(NotificationVariant.LUMO_ERROR);
			} catch (ValidationException validationException) {
				Notification.show("Failed to update the data. Check again that all values are valid");
			}
		});
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		Optional<Long> personId = event.getRouteParameters().get(PERSON_ID).map(Long::parseLong);
		if (personId.isPresent()) {
			Optional<User> samplePersonFromBackend = userService.get(personId.get());
			if (samplePersonFromBackend.isPresent()) {
				populateForm(samplePersonFromBackend.get());
			} else {
				Notification.show(String.format("The requested samplePerson was not found, ID = %s", personId.get()), 3000, Notification.Position.BOTTOM_START);
				// when a row is selected but the data is no longer available,
				// refresh grid
				refreshGrid();
				event.forwardTo(AdminView.class);
			}
		}
	}

	private void createEditorLayout(SplitLayout splitLayout) {
		Div editorLayoutDiv = new Div();
		editorLayoutDiv.setClassName("editor-layout");

		Div editorDiv = new Div();
		editorDiv.setClassName("editor");
		editorLayoutDiv.add(editorDiv);

		FormLayout formLayout = new FormLayout();
		firstName = new TextField("Vorname");
		lastName = new TextField("Nachname");
		email = new TextField("Email");
		phoneNumber = new TextField("Telefonnummer");
		hasPaid = new Checkbox("Gezahlt");
		hasEquipment = new Checkbox("Ausrüstung vorhanden");
		formLayout.add(firstName, lastName, email, phoneNumber, hasPaid, hasEquipment);

		editorDiv.add(formLayout);
		createButtonLayout(editorLayoutDiv);

		splitLayout.addToSecondary(editorLayoutDiv);
	}

	private void createButtonLayout(Div editorLayoutDiv) {
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setClassName("button-layout");
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		buttonLayout.add(save, cancel);
		editorLayoutDiv.add(buttonLayout);
	}

	private void createGridLayout(SplitLayout splitLayout) {
		Div wrapper = new Div();
		wrapper.setClassName("grid-wrapper");
		splitLayout.addToPrimary(wrapper);
		wrapper.add(grid);
	}

	private void refreshGrid() {
		grid.select(null);
		grid.getDataProvider().refreshAll();
	}

	private void clearForm() {
		populateForm(null);
	}

	private void populateForm(User value) {
		this.user = value;
		binder.readBean(this.user);

	}
}
