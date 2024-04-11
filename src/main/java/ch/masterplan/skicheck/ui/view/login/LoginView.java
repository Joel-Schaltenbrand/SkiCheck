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

package ch.masterplan.skicheck.ui.view.login;

import ch.masterplan.skicheck.app.security.AuthenticatedUser;
import ch.masterplan.skicheck.backend.service.impl.LanguageService;
import ch.masterplan.skicheck.ui.view.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.io.Serial;

/**
 * View for handling user login.
 */
@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login", layout = MainLayout.class)
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
	@Serial
	private static final long serialVersionUID = 4568874689764L;
	private final transient AuthenticatedUser authenticatedUser;
	private final transient LanguageService languageService;

	/**
	 * Constructs the login view.
	 *
	 * @param authenticatedUser AuthenticatedUser instance for user authentication.
  	 * @param languageService   LanguageService instance for retrieving language-related content.
	 */
	public LoginView(AuthenticatedUser authenticatedUser, LanguageService languageService) {
		this.authenticatedUser = authenticatedUser;
		this.languageService = languageService;

		LoginForm login = new LoginForm();

		login.setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

		login.setI18n(generateI18n());

		login.addForgotPasswordListener(e -> UI.getCurrent().navigate("register"));
		login.setForgotPasswordButtonVisible(true);
		setHorizontalComponentAlignment(Alignment.CENTER, login);

		add(login);
	}

	private LoginI18n generateI18n() {
		LoginI18n i18n = LoginI18n.createDefault();
		LoginI18n.Header i18nHeader = new LoginI18n.Header();
		i18nHeader.setTitle(languageService.getMessage4Key("general.login"));
		i18nHeader.setDescription(languageService.getMessage4Key("login.description"));
		i18n.setHeader(i18nHeader);

		LoginI18n.Form i18nForm = i18n.getForm();
		i18nForm.setTitle(languageService.getMessage4Key("general.title"));
		i18nForm.setUsername(languageService.getMessage4Key("general.username"));
		i18nForm.setPassword(languageService.getMessage4Key("general.password"));
		i18nForm.setSubmit(languageService.getMessage4Key("login.submit"));
		i18nForm.setForgotPassword(languageService.getMessage4Key("general.register"));
		i18n.setForm(i18nForm);

		LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
		i18nErrorMessage.setTitle(languageService.getMessage4Key("login.error.title"));
		i18nErrorMessage.setMessage(languageService.getMessage4Key("login.error.description"));
		i18n.setErrorMessage(i18nErrorMessage);

		return i18n;
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (authenticatedUser.get().isPresent()) {
			event.forwardTo("");
		}
	}
}
