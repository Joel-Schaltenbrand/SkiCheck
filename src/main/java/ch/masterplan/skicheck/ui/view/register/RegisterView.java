package ch.masterplan.skicheck.ui.view.register;

import ch.masterplan.skicheck.backend.service.impl.UserDetailService;
import ch.masterplan.skicheck.backend.service.impl.UserService;
import ch.masterplan.skicheck.backend.util.ServiceResponse;
import ch.masterplan.skicheck.model.user.UserEntity;
import ch.masterplan.skicheck.ui.util.Notifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Route("register")
public class RegisterView extends FormLayout {

	private final UserService userService;
	private final Notifier notifier;

	private final Binder<UserEntity> binder = new Binder<>(UserEntity.class);

	public RegisterView(UserService userService, Notifier notifier) {
		this.userService = userService;
		this.notifier = notifier;

		add(new H1("Register"));

		TextField username = new TextField("Username");
		TextField firstName = new TextField("First Name");
		TextField lastName = new TextField("Last Name");
		EmailField email = new EmailField("Email");
		PasswordField password = new PasswordField("Password");
		Button registerButton = new Button("Register");

		binder.forField(username)
				.asRequired("Username is required")
				.bind(UserEntity::getUsername, UserEntity::setUsername);
		binder.forField(firstName)
				.asRequired("First name is required")
				.bind(UserEntity::getFirstName, UserEntity::setFirstName);
		binder.forField(lastName)
				.asRequired("Last name is required")
				.bind(UserEntity::getLastName, UserEntity::setLastName);
		binder.forField(email)
				.asRequired("Email is required")
				.withValidator(new EmailValidator("Invalid email address"))
				.bind(UserEntity::getEmail, UserEntity::setEmail);
		binder.forField(password)
				.asRequired("Password is required")
				.bind(null, (user, passwordValue) -> user.setHashedPassword(new BCryptPasswordEncoder().encode(passwordValue)));

		registerButton.addClickListener(event -> {
			UserEntity newUser = new UserEntity();
			binder.writeBeanIfValid(newUser);
			ServiceResponse<UserEntity> response = userService.save(newUser);
			if (response.getOperationWasSuccessful()) {
				UI.getCurrent().navigate("login");
				notifier.notifySuccess("User registration successful");
				clearFields();
			} else {
				notifier.notifyError("User registration failed");
			}
		});

		add(username, firstName, lastName, email, password, registerButton);
	}

	private void clearFields() {
		binder.readBean(null);
	}
}
