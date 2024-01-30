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

import ch.masterplan.skicheck.model.util.Language;

import java.util.Locale;

/**
 * CommonContext class representing the context shared across the application threads.
 */
public class CommonContext {

	private static final ThreadLocal<CommonContext> commonContext = ThreadLocal.withInitial(CommonContext::new);
	private Language language = Language.DE;

	private CommonContext() {
		super();
	}

	/**
	 * Gets the instance of CommonContext for the current thread.
	 *
	 * @return The CommonContext instance.
	 */
	public static CommonContext get() {
		return commonContext.get();
	}

	/**
	 * Sets the CommonContext for the current thread.
	 *
	 * @param commonContext The CommonContext instance to set.
	 */
	public static void set(CommonContext commonContext) {
		CommonContext.commonContext.set(commonContext);
	}

	/**
	 * Gets the Locale based on the current language.
	 *
	 * @return The Locale corresponding to the current language.
	 */
	public Locale getLocale() {
		return Locale.of(this.language.name());
	}

	/**
	 * Gets the current language.
	 *
	 * @return The current language.
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 * Sets the language for the CommonContext.
	 *
	 * @param language The language to set.
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}
}
