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

package ch.masterplan.skicheck.ui.view.home;

import ch.masterplan.skicheck.backend.service.impl.LanguageService;
import ch.masterplan.skicheck.ui.view.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import java.io.Serial;

/**
 * View for the home page.
 */
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
@PageTitle("Home")
public class HomeView extends VerticalLayout {
	@Serial
	private static final long serialVersionUID = 238765298576298365L;
	private final transient LanguageService languageService;

	/**
	 * Constructs the home view.
	 *
	 * @param languageService LanguageService instance for retrieving language-related content.
	 */
	public HomeView(LanguageService languageService) {
		this.languageService = languageService;
		setPageConfig();
		addContent();
	}

	private void addContent() {
		addImage();
		addTitle();
		addParagraph();
	}

	private void addImage() {
		Image img = new Image("images/ski_icon.png", "ski icon");
		img.setWidth("200px");
		add(img);
	}

	private void addTitle() {
		H2 header = new H2(languageService.getMessage4Key("homeView.title"));
		header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
		add(header);
	}

	private void addParagraph() {
		add(new Paragraph(languageService.getMessage4Key("homeView.paragraph")));
	}

	private void setPageConfig() {
		setSpacing(false);
		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("text-align", "center");
	}
}
