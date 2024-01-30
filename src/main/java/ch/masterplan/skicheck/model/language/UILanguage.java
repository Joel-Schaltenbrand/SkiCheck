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

package ch.masterplan.skicheck.model.language;

import ch.masterplan.skicheck.model.util.Language;

/**
 * Model class representing a UI language with a name and language code.
 */
public class UILanguage {

	private String name;
	private Language code;

	/**
	 * Constructor to initialize the UI language with a name and language code.
	 *
	 * @param name The name of the UI language.
	 * @param code The language code of the UI language.
	 */
	public UILanguage(String name, Language code) {
		this.code = code;
		this.name = name;
	}

	/**
	 * Retrieves the name of the UI language.
	 *
	 * @return The name of the UI language.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the UI language.
	 *
	 * @param name The name to be set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves the language code of the UI language.
	 *
	 * @return The language code of the UI language.
	 */
	public Language getCode() {
		return code;
	}

	/**
	 * Sets the language code of the UI language.
	 *
	 * @param code The language code to be set.
	 */
	public void setCode(Language code) {
		this.code = code;
	}
}
