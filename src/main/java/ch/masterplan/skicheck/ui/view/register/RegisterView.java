package ch.masterplan.skicheck.ui.view.register;

import ch.masterplan.skicheck.backend.service.impl.LanguageService;
import ch.masterplan.skicheck.backend.service.impl.UserService;
import ch.masterplan.skicheck.backend.util.ServiceResponse;
import ch.masterplan.skicheck.model.user.Equipment;
import ch.masterplan.skicheck.model.user.Role;
import ch.masterplan.skicheck.model.user.UserEntity;
import ch.masterplan.skicheck.model.userdetail.UserDetailEntity;
import ch.masterplan.skicheck.ui.util.Notifier;
import ch.masterplan.skicheck.ui.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@Route(value = "register", layout = MainLayout.class)
@AnonymousAllowed
@PageTitle("Register")
public class RegisterView extends VerticalLayout {

	private final UserService userService;
	private final Notifier notifier;
	private final LanguageService languageService;

	private final Binder<UserEntity> userBinder = new Binder<>(UserEntity.class);
	private final Binder<UserDetailEntity> userDetailBinder = new Binder<>(UserDetailEntity.class);

	public RegisterView(UserService userService, Notifier notifier, LanguageService languageService) {
		this.userService = userService;
		this.notifier = notifier;
		this.languageService = languageService;

		H2 title = new H2(languageService.getMessage4Key("general.register"));
		TextField username = createTextField(languageService.getMessage4Key("general.username"));
		TextField firstName = createTextField(languageService.getMessage4Key("general.firstname"));
		TextField lastName = createTextField(languageService.getMessage4Key("general.lastname"));
		EmailField email = createEmailField(languageService.getMessage4Key("general.email"));
		PasswordField password = createPasswordField(languageService.getMessage4Key("general.password"));
		password.setPattern("^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$");
		password.setHelperText(languageService.getMessage4Key("register.password.helper"));
		MultiSelectComboBox<Equipment> equipment = createEquipmentComboBox(languageService.getMessage4Key("general.equipment"));
		Button registerButton = new Button(languageService.getMessage4Key("general.register"));
		registerButton.addClickListener(buttonClickEvent -> registerUser());
		registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		userBinder.forField(username).asRequired(languageService.getMessage4Key("error.usernameRequired"))
				.bind(UserEntity::getUsername, UserEntity::setUsername);
		userBinder.forField(firstName).asRequired(languageService.getMessage4Key("error.firstnameRequired"))
				.bind(UserEntity::getFirstName, UserEntity::setFirstName);
		userBinder.forField(lastName).asRequired(languageService.getMessage4Key("error.lastnameRequired"))
				.bind(UserEntity::getLastName, UserEntity::setLastName);
		userBinder.forField(email).asRequired(languageService.getMessage4Key("error.emailRequired")).withValidator(new EmailValidator(languageService.getMessage4Key("error.emailInvalid")))
				.bind(UserEntity::getEmail, UserEntity::setEmail);
		userBinder.forField(password).asRequired(languageService.getMessage4Key("error.passwordRequired")).bind(UserEntity::getHashedPassword, (user, passwordValue) -> user.setHashedPassword(new BCryptPasswordEncoder().encode(passwordValue)));
		userDetailBinder.forField(equipment).bind(UserDetailEntity::getEquipment, UserDetailEntity::setEquipment);

		FormLayout formLayout = new FormLayout();
		formLayout.add(title, firstName, lastName, username, email, password, equipment, registerButton);
		formLayout.setMaxWidth("1000px");
		formLayout.setColspan(title, 2);
		formLayout.setColspan(registerButton, 2);
		formLayout.getStyle().set("padding", "var(--lumo-space-l)");
		formLayout.getStyle().set("background", "var(--lumo-base-color) linear-gradient(var(--lumo-tint-5pct), var(--lumo-tint-5pct))");

		setHorizontalComponentAlignment(Alignment.CENTER, formLayout);

		add(formLayout);
	}

	private TextField createTextField(String label) {
		TextField textField = new TextField(label);
		textField.setWidth("100%");
		return textField;
	}

	private EmailField createEmailField(String label) {
		EmailField emailField = new EmailField(label);
		emailField.setWidth("100%");
		return emailField;
	}

	private PasswordField createPasswordField(String label) {
		PasswordField passwordField = new PasswordField(label);
		passwordField.setWidth("100%");
		return passwordField;
	}

	private MultiSelectComboBox<Equipment> createEquipmentComboBox(String label) {
		MultiSelectComboBox<Equipment> comboBox = new MultiSelectComboBox<>(label);
		comboBox.setItems(Equipment.values());
		comboBox.setWidth("100%");
		return comboBox;
	}

	private void registerUser() {
		UserEntity newUser = new UserEntity();
		if (userBinder.writeBeanIfValid(newUser)) {
			UserDetailEntity newUserDetail = new UserDetailEntity();
			userDetailBinder.writeBeanIfValid(newUserDetail);
			newUser.setUserDetails(newUserDetail);
			newUser.setRoles(Collections.singleton(Role.USER));
			ServiceResponse<UserEntity> response = userService.save(newUser);
			if (response.getOperationWasSuccessful()) {
				getUI().ifPresent(ui -> ui.navigate("login"));
				notifier.notifySuccess(languageService.getMessage4Key("user.registration.success"));
				clearFields();
			} else {
				notifier.notifyError(languageService.getMessage4Key("user.registration.error"));
			}
		}
	}

	private void clearFields() {
		userBinder.readBean(null);
	}
}
