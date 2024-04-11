/*
 * MIT License
 *
 * Copyright (c) 2024 Masterplan AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ch.masterplan.skicheck.ui.view.admin;

import ch.masterplan.skicheck.app.configuration.ApplicationConfiguration;
import ch.masterplan.skicheck.backend.service.IUserDetailService;
import ch.masterplan.skicheck.backend.service.IUserService;
import ch.masterplan.skicheck.backend.service.impl.LanguageService;
import ch.masterplan.skicheck.backend.util.ServiceResponse;
import ch.masterplan.skicheck.model.user.Equipment;
import ch.masterplan.skicheck.model.user.Role;
import ch.masterplan.skicheck.model.user.UserEntity;
import ch.masterplan.skicheck.model.userdetail.UserDetailEntity;
import ch.masterplan.skicheck.ui.util.INotifier;
import ch.masterplan.skicheck.ui.util.Notifier;
import ch.masterplan.skicheck.ui.view.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.PageRequest;

import java.io.Serial;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * View for the admin page.
 */
@PageTitle("Admin")
@Route(value = "admin/:personID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class AdminView extends Div implements BeforeEnterObserver {
	@Serial
	private static final long serialVersionUID = 1289795467231L;
	private static final String PERSON_ID = "personID";
	private static final String PERSON_EDIT_ROUTE_TEMPLATE = "admin/%s/edit";
	private final transient LanguageService languageService;
	private final transient ApplicationConfiguration config;
	private final Grid<UserEntity> grid = new Grid<>(UserEntity.class, false);
	private final Button resetPayment = new Button();
	private final Button cancel = new Button();
	private final Button save = new Button();
	private final Button delete = new Button();
	private final BeanValidationBinder<UserEntity> binder;
	private final BeanValidationBinder<UserDetailEntity> binderDetails;
	private final transient IUserService userService;
	private final transient IUserDetailService userDetailService;
	private final transient INotifier notifier;
	private TextField firstName;
	private TextField lastName;
	private TextField username;
	private EmailField email;
	private MultiSelectComboBox<Equipment> equipment;
	private Checkbox hasPaid;
	private MultiSelectComboBox<Role> roles;
	private Button resetPassword;
	private UserEntity userEntity;
	private UserDetailEntity userDetailEntity;

	/**
	 * Constructs the admin view.
	 *
	 * @param userService UserService instance for managing user-related operations.
	 */
	public AdminView(LanguageService languageService, ApplicationConfiguration config,
					IUserService userService, IUserDetailService userDetailService, Notifier notifier) {
		this.languageService = languageService;
		this.config = config;
		this.userService = userService;
		this.userDetailService = userDetailService;
		this.notifier = notifier;
		addClassNames("admin-view");

		// Generate Layout
		SplitLayout splitLayout = new SplitLayout();
		createGridLayout(splitLayout);
		createEditorLayout(splitLayout);
		add(splitLayout);
		configGrid();

		// Bind fields.
		binder = new BeanValidationBinder<>(UserEntity.class);
		binder.bindInstanceFields(this);
		binder.forField(firstName).asRequired(languageService.getMessage4Key("adminView.notification.firstname.required"))
				.bind(UserEntity::getFirstName, UserEntity::setFirstName);
		binder.forField(lastName).asRequired(languageService.getMessage4Key("adminView.notification.lastname.required"))
				.bind(UserEntity::getLastName, UserEntity::setLastName);
		binder.forField(username).asRequired(languageService.getMessage4Key("adminView.notification.username.required"))
				.bind(UserEntity::getUsername, UserEntity::setUsername);
		binder.forField(email).asRequired(languageService.getMessage4Key("adminView.notification.email.required"))
				.bind(UserEntity::getEmail, UserEntity::setEmail);
		binder.forField(roles).bind(UserEntity::getRoles, UserEntity::setRoles);

		binderDetails = new BeanValidationBinder<>(UserDetailEntity.class);
		binderDetails.bindInstanceFields(this);
		binderDetails.forField(hasPaid).bind(UserDetailEntity::hasPaid, UserDetailEntity::setHasPaid);
		binderDetails.forField(equipment).bind(UserDetailEntity::getEquipment, UserDetailEntity::setEquipment);

		// Configure and style components
		setButtonText();
		setResetPasswordButton();
		setCancelButton();
		setResetButton();
		setSaveButton();
		setDeleteButton();
	}

	private void setDeleteButton() {
		delete.addClickListener(e -> {
			if (this.userEntity != null) {
				ServiceResponse<UserEntity> response = userService.delete(this.userEntity);
				if (response.getOperationWasSuccessful()) {
					notifier.notifySuccessAsTray(languageService.getMessage4Key("adminView.notification.deleteSuccess"));
				} else {
					notifier.notifyError(response.getErrorMessage().getMessage());
				}
				clearForm();
				refreshGrid();
			} else {
				notifier.notifyError(languageService.getMessage4Key("adminView.notification.noUserSelected"));
			}
		});
	}

	private void setResetPasswordButton() {
		resetPassword.addClickListener(e -> {
			if (this.userEntity != null) {
				this.userEntity.setHashedPassword(config.getDefaultHashedPassword());
				binder.readBean(this.userEntity);
				notifier.notifyWarning(languageService.getMessage4Key("adminView.notification.passwordReset", config.getDefaultPassword()));
			} else {
				notifier.notifyError(languageService.getMessage4Key("adminView.notification.noUserSelected"));
			}
		});
	}

	private void configGrid() {
		grid.addColumn("firstName").setAutoWidth(true).setHeader(languageService.getMessage4Key("general.firstname"));
		grid.addColumn("lastName").setAutoWidth(true).setHeader(languageService.getMessage4Key("general.lastname"));
		grid.addColumn("username").setAutoWidth(true).setHeader(languageService.getMessage4Key("general.username"));
		grid.addColumn("email").setAutoWidth(true).setHeader(languageService.getMessage4Key("general.email"));
		grid.addComponentColumn(user -> createStatusIcon(user.getUserDetails().hasPaid()))
				.setHeader(languageService.getMessage4Key("general.hasPaid")).setAutoWidth(true);

		grid.addComponentColumn(user -> {
			Set<Equipment> eqSet = user.getUserDetails().getEquipment();

			VerticalLayout enumLayout = new VerticalLayout();

			for (Equipment enumValue : eqSet) {
				Span enumSpan = new Span(enumValue.toString());
				enumSpan.getElement().getThemeList().add("badge primary pill");
				enumLayout.add(enumSpan);
			}
			return enumLayout;
		}).setHeader(languageService.getMessage4Key("general.equipment")).setAutoWidth(true);

		grid.setItems(query ->
				userService.list(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null) {
				UI.getCurrent().navigate(String.format(PERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
			} else {
				clearForm();
				UI.getCurrent().navigate(AdminView.class);
			}
		});
	}

	private Icon createStatusIcon(boolean status) {
		Icon icon;
		if (status) {
			icon = VaadinIcon.CHECK.create();
			icon.getElement().getThemeList().add("badge success");
		} else {
			icon = VaadinIcon.CLOSE_SMALL.create();
			icon.getElement().getThemeList().add("badge error");
		}
		icon.getStyle().set("padding", "var(--lumo-space-xs");
		return icon;
	}

	private void setButtonText() {
		resetPayment.setText(languageService.getMessage4Key("adminView.button.resetPayment"));
		cancel.setText(languageService.getMessage4Key("general.cancel"));
		save.setText(languageService.getMessage4Key("general.save"));
		delete.setText(languageService.getMessage4Key("general.delete"));
	}

	private void setSaveButton() {
		save.addClickListener(e -> {
			boolean isNewUser = false;
			try {
				if (this.userEntity == null) {
					this.userEntity = new UserEntity();
					this.userEntity.setHashedPassword(config.getDefaultHashedPassword());
					this.userEntity.setRoles(Collections.singleton(Role.USER));
					isNewUser = true;
				}
				binderDetails.writeBean(this.userDetailEntity);
				ServiceResponse<UserDetailEntity> responseDetails = userDetailService.save(this.userDetailEntity);
				if (!responseDetails.getOperationWasSuccessful()) {
					notifier.notifyError(responseDetails.getErrorMessage().getMessage());
					return;
				}
				this.userEntity.setUserDetails(responseDetails.getBusinessObjects().getFirst());
				binder.writeBean(this.userEntity);
				ServiceResponse<UserEntity> response = userService.save(this.userEntity);
				if (response.getOperationWasSuccessful()) {
					notifier.notifySuccessAsTray(languageService.getMessage4Key("adminView.notification.saveSuccess"));
					if (isNewUser) {
						notifier.notifyWarning(languageService.getMessage4Key("adminView.notification.changePassword", config.getDefaultPassword()));
					}

				} else {
					notifier.notifyError(response.getErrorMessage().getMessage());
				}
				clearForm();
				refreshGrid();
			} catch (ValidationException exception) {
				notifier.notifyError(languageService.getMessage4Key("adminView.notification.validationError"));
			}
		});
	}

	private void setResetButton() {
		resetPayment.addClickListener(e -> {
			ServiceResponse<UserDetailEntity> response = userDetailService.resetAllPaymentStatus();
			if (response.getOperationWasSuccessful()) {
				notifier.notifySuccess(response.getInfoMessage().getMessage());
			} else {
				notifier.notifyError(response.getErrorMessage().getMessage());
			}
			refreshGrid();
		});
	}

	private void setCancelButton() {
		cancel.addClickListener(e -> {
			clearForm();
			refreshGrid();
		});
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		Optional<Long> personId = event.getRouteParameters().get(PERSON_ID).map(Long::parseLong);
		if (personId.isPresent()) {
			ServiceResponse<UserEntity> response = userService.getById(personId.get());
			if (response.getOperationWasSuccessful()) {
				populateForm(response.getBusinessObjects().getFirst(), response.getBusinessObjects().getFirst().getUserDetails());
			} else {
				notifier.notifyError(languageService.getMessage4Key("adminView.notification.notfound", personId.get()));
				refreshGrid();
				event.forwardTo(AdminView.class);
			}
		}
		delete.setVisible(this.userEntity != null);
		cancel.setVisible(this.userEntity != null);
	}

	private void createEditorLayout(SplitLayout splitLayout) {
		Div editorLayoutDiv = new Div();
		editorLayoutDiv.setClassName("editor-layout");

		Div editorDiv = new Div();
		editorDiv.setClassName("editor");
		editorLayoutDiv.add(editorDiv);

		FormLayout formLayout = new FormLayout();
		firstName = new TextField(languageService.getMessage4Key("general.firstname"));
		lastName = new TextField(languageService.getMessage4Key("general.lastname"));
		username = new TextField(languageService.getMessage4Key("general.username"));
		email = new EmailField(languageService.getMessage4Key("general.email"));
		hasPaid = new Checkbox(languageService.getMessage4Key("general.hasPaid"));
		equipment = new MultiSelectComboBox<>(languageService.getMessage4Key("general.equipment"));
		equipment.setItems(Equipment.values());
		roles = new MultiSelectComboBox<>(languageService.getMessage4Key("general.roles"));
		roles.setItems(Role.values());
		resetPassword = new Button(languageService.getMessage4Key("general.resetPassword"));

		formLayout.add(firstName, lastName, username, email, hasPaid, equipment, roles, resetPassword);

		editorDiv.add(formLayout);
		createButtonLayout(editorLayoutDiv);

		splitLayout.addToSecondary(editorLayoutDiv);
	}

	private void createButtonLayout(Div editorLayoutDiv) {
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setClassName("button-layout");
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		resetPayment.addThemeVariants(ButtonVariant.LUMO_ERROR);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		buttonLayout.add(save, cancel, delete, resetPayment);
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
		populateForm(null, null);
	}

	private void populateForm(UserEntity value, UserDetailEntity value2) {
		this.userEntity = value;
		this.userDetailEntity = value2;
		binder.readBean(this.userEntity);
		binderDetails.readBean(this.userDetailEntity);
	}
}
