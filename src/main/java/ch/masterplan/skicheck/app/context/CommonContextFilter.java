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

package ch.masterplan.skicheck.app.context;

import ch.masterplan.skicheck.app.configuration.HasLogger;
import ch.masterplan.skicheck.model.util.Language;
import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Locale;

/**
 * Filter class for managing the common context of the application, including language settings.
 */
@WebFilter("/*")
public class CommonContextFilter extends AbstractApplicationContextFilter implements Filter, HasLogger {
	private static final String LANGUAGE_SESSION_KEY = "language";

	/**
	 * Executes actions before the filter chain is invoked, setting the language based on session, locale, and parameters.
	 *
	 * @param request  The HttpServletRequest object.
	 * @param response The HttpServletResponse object.
	 * @return Whether the request should continue to the filter chain.
	 */
	@Override
	protected boolean doBefore(HttpServletRequest request, HttpServletResponse response) {
		CommonContext context = CommonContext.get();

		String language = (String) request.getSession().getAttribute(LANGUAGE_SESSION_KEY);
		if (language != null) {
			setLanguageFromSession(language, context);
		} else {
			setLanguageFromLocale(request.getLocale(), context);
		}

		String paramValue = request.getParameter("locale");
		if (paramValue != null) {
			setLanguageFromParam(paramValue, context);
		}

		setDefaultLocale(context.getLocale());

		log().debug("Language set to {}", context.getLanguage());

		return true;
	}

	/**
	 * Executes actions after the filter chain is invoked, saving the language to the session.
	 *
	 * @param request  The HttpServletRequest object.
	 * @param response The HttpServletResponse object.
	 */
	@Override
	protected void doAfter(HttpServletRequest request, HttpServletResponse response) {
		CommonContext context = CommonContext.get();
		setLanguageToSession(request, context);
		log().debug("Language saved to session: {}", context.getLanguage());
	}

	private void setLanguageFromSession(String language, CommonContext context) {
		switch (language.substring(0, 2)) {
			case "fr" -> context.setLanguage(Language.FR);
			case "de" -> context.setLanguage(Language.DE);
			case "it" -> context.setLanguage(Language.IT);
			default -> context.setLanguage(Language.EN);
		}
	}

	private void setLanguageFromLocale(Locale locale, CommonContext context) {
		if (locale != null) {
			switch (locale.getLanguage()) {
				case "fr" -> context.setLanguage(Language.FR);
				case "de" -> context.setLanguage(Language.DE);
				case "it" -> context.setLanguage(Language.IT);
				default -> context.setLanguage(Language.EN);
			}
		} else {
			context.setLanguage(Language.DE);
		}
	}

	private void setLanguageFromParam(String paramValue, CommonContext context) {
		Language lang = switch (paramValue) {
			case "de" -> Language.DE;
			case "fr" -> Language.FR;
			case "it" -> Language.IT;
			case "en" -> Language.EN;
			default -> null;
		};
		if (lang != null) {
			context.setLanguage(lang);
		}
	}

	private void setDefaultLocale(Locale locale) {
		if (locale != null) {
			Locale.setDefault(locale);
		}
	}

	private void setLanguageToSession(HttpServletRequest request, CommonContext context) {
		String languageSessionValue = switch (context.getLanguage()) {
			case FR -> "fr";
			case DE -> "de";
			case IT -> "it";
			case EN -> "en";
		};
		request.getSession().setAttribute(LANGUAGE_SESSION_KEY, languageSessionValue);
	}
}
