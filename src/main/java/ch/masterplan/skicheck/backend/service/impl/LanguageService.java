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

package ch.masterplan.skicheck.backend.service.impl;

import ch.masterplan.skicheck.app.configuration.HasLogger;
import ch.masterplan.skicheck.app.context.CommonContext;
import ch.masterplan.skicheck.model.language.UILanguage;
import ch.masterplan.skicheck.model.util.Language;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Service class for managing language-related operations in the application.
 */
public class LanguageService implements HasLogger {

	private final MessageSource messageSource;

	/**
	 * Constructs a LanguageService with the provided MessageSource.
	 *
	 * @param messageSource The MessageSource instance.
	 */
	public LanguageService(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Retrieves a list of UILanguage objects representing supported languages for the UI.
	 *
	 * @return A list of UILanguage objects.
	 */
	public List<UILanguage> getLanguagesForUI() {
		log().debug("Returning languages for UI");
		List<UILanguage> languages = new ArrayList<>();

		for (Language language : Language.values()) {
			UILanguage uiLanguage = switch (language) {
				case DE -> new UILanguage(messageSource.getMessage("language.de", null, Locale.getDefault()), language);
				case EN -> new UILanguage(messageSource.getMessage("language.en", null, Locale.getDefault()), language);
				case FR -> new UILanguage(messageSource.getMessage("language.fr", null, Locale.getDefault()), language);
				case IT -> new UILanguage(messageSource.getMessage("language.it", null, Locale.getDefault()), language);
			};
			languages.add(uiLanguage);
		}

		return languages;
	}

	/**
	 * Retrieves the current language from the CommonContext.
	 *
	 * @return The current language.
	 */
	public Language getCurrentLanguage() {
		CommonContext context = CommonContext.get();
		log().debug("Returning current language: {}", context.getLanguage());
		return context.getLanguage();
	}

	/**
	 * Saves the specified UILanguage as the current language in the CommonContext.
	 *
	 * @param language The UILanguage to be saved.
	 */
	public void saveCurrentLanguage(UILanguage language) {
		CommonContext context = CommonContext.get();
		context.setLanguage(language.getCode());
		log().debug("Saving current language: {}", context.getLanguage());
		CommonContext.set(context);
	}

	/**
	 * Retrieves a localized message for the specified key using the current locale.
	 *
	 * @param key  The message key.
	 * @param args Optional arguments to be replaced in the message.
	 * @return The localized message.
	 */
	public String getMessage4Key(String key, Object... args) {
		Locale locale = CommonContext.get().getLocale();
		log().debug("Returning message for key: {} with locale: {}", key, locale);
		return getMessage4Key(key, locale, args);
	}

	/**
	 * Retrieves a localized message for the specified key using the provided locale.
	 *
	 * @param key    The message key.
	 * @param locale The locale for localization.
	 * @param args   Optional arguments to be replaced in the message.
	 * @return The localized message.
	 */
	public String getMessage4Key(String key, Locale locale, Object... args) {
		log().debug("Returning message for key: {} with locale: {}", key, locale);
		return messageSource.getMessage(key, args, locale);
	}
}
