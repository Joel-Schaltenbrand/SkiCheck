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

package ch.masterplan.skicheck.ui.view;

import ch.masterplan.skicheck.app.configuration.HasLogger;
import ch.masterplan.skicheck.app.security.AuthenticatedUser;
import ch.masterplan.skicheck.backend.service.impl.LanguageService;
import ch.masterplan.skicheck.model.language.UILanguage;
import ch.masterplan.skicheck.model.user.UserEntity;
import ch.masterplan.skicheck.model.util.Language;
import ch.masterplan.skicheck.ui.view.account.AccountView;
import ch.masterplan.skicheck.ui.view.admin.AdminView;
import ch.masterplan.skicheck.ui.view.home.HomeView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other view.
 */
public class MainLayout extends AppLayout implements HasLogger {

	private final AuthenticatedUser authenticatedUser;
	private final AccessAnnotationChecker accessChecker;
	private final LanguageService languageService;
	private H2 viewTitle;

	/**
	 * Creates a new main layout.
	 *
	 * @param authenticatedUser The authenticated user.
	 * @param accessChecker     The access checker.
	 * @param languageService   The language service.
	 */
	public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, LanguageService languageService) {
		this.authenticatedUser = authenticatedUser;
		this.accessChecker = accessChecker;
		this.languageService = languageService;

		setPrimarySection(Section.DRAWER);
		addDrawerContent();
		addHeaderContent();
	}

	private void addHeaderContent() {
		DrawerToggle toggle = new DrawerToggle();

		viewTitle = new H2();
		viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

		addToNavbar(true, toggle, viewTitle);
	}

	private void addDrawerContent() {
		H1 appName = new H1(languageService.getMessage4Key("general.title"));
		appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
		Header header = new Header(appName);

		Scroller scroller = new Scroller(createNavigation());

		addToDrawer(header, scroller, createFooter());
	}

	private Component generateLanguage() {
		HorizontalLayout languageButtonsLayout = new HorizontalLayout();
		languageButtonsLayout.getStyle().set("gap", "10px");
		languageButtonsLayout.getStyle().set("align-items", "center");
		languageButtonsLayout.getStyle().set("border", "1px solid var(--lumo-contrast-10pct)");
		languageButtonsLayout.getStyle().set("border-radius", "10px");
		languageButtonsLayout.getStyle().set("padding", "5px");

		List<Button> buttons = new ArrayList<>();
		Language langCurrent = languageService.getCurrentLanguage();
		int i = 0;
		for (UILanguage uiLang : languageService.getLanguagesForUI()) {
			Button buttonLang = new Button();
			buttonLang.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
			buttonLang.getStyle().set("cursor", "pointer");
			removeBG(buttonLang);
			buttonLang.setText(uiLang.getCode().name());
			buttonLang.addClickListener(event -> {
				languageService.saveCurrentLanguage(uiLang);
				UI.getCurrent().getPage().reload();
			});
			if (uiLang.getCode().equals(langCurrent)) {
				buttonLang.getStyle().set("cursor", "default");
				buttonLang.getStyle().set("font-weight", "bold");
				buttonLang.getStyle().set("color", "var(--lumo-primary-color)");
				buttonLang.setEnabled(false);
			}
			if (i != 0) {
				Button divider = new Button("|");
				divider.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
				removeBG(divider);
				divider.setEnabled(false);
				buttons.add(divider);
			}
			buttons.add(buttonLang);
			i++;
		}

		languageButtonsLayout.removeAll();
		languageButtonsLayout.add(buttons.toArray(new Button[0]));
		return languageButtonsLayout;
	}

	private void removeBG(Button button) {
		button.getStyle().set("box-shadow", "none");
		button.getStyle().set("background-image", "none");
	}

	private SideNav createNavigation() {
		SideNav nav = new SideNav();

		if (accessChecker.hasAccess(HomeView.class)) {
			nav.addItem(new SideNavItem("Home", HomeView.class, LineAwesomeIcon.HOME_SOLID.create()));
		}

		if (accessChecker.hasAccess(AdminView.class)) {
			nav.addItem(new SideNavItem("Admin", AdminView.class, LineAwesomeIcon.COLUMNS_SOLID.create()));
		}

		return nav;
	}

	private Footer createFooter() {
		Footer layout = new Footer();
		VerticalLayout footerLayout = new VerticalLayout();

		Optional<UserEntity> maybeUser = authenticatedUser.get();
		if (maybeUser.isPresent()) {
			UserEntity user = maybeUser.get();

			Avatar avatar = new Avatar(user.getFullName());
			avatar.setThemeName("xsmall");
			avatar.getElement().setAttribute("tabindex", "-1");

			MenuBar userMenu = new MenuBar();
			userMenu.setThemeName("tertiary-inline contrast");

			MenuItem userName = userMenu.addItem("");
			Button account = new Button("Account", LineAwesomeIcon.USER.create());
			account.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
			removeBG(account);
			userName.getSubMenu().addItem(account, e -> UI.getCurrent().navigate(AccountView.class));
			userName.getSubMenu().addSeparator();
			Div div = new Div();
			div.add(avatar);
			div.add(user.getFullName());
			div.add(new Icon("lumo", "dropdown"));
			div.getElement().getStyle().set("display", "flex");
			div.getElement().getStyle().set("align-items", "center");
			div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
			div.getElement().getStyle().set("padding", "5px");
			userName.add(div);
			Button logout = new Button(languageService.getMessage4Key("general.logout"), LineAwesomeIcon.SIGN_OUT_ALT_SOLID.create());
			logout.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
			removeBG(logout);
			userName.getSubMenu().addItem(logout, e -> authenticatedUser.logout());

			footerLayout.add(userMenu);
		} else {
			Anchor loginLink = new Anchor("login", languageService.getMessage4Key("general.login"));
			Anchor registerLink = new Anchor("register", languageService.getMessage4Key("general.register"));
			footerLayout.add(loginLink, registerLink);
		}
		footerLayout.add(generateLanguage());
		layout.add(footerLayout);

		return layout;
	}

	@Override
	protected void afterNavigation() {
		super.afterNavigation();
		viewTitle.setText(getCurrentPageTitle());
	}

	private String getCurrentPageTitle() {
		PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
		return title == null ? "" : title.value();
	}
}
