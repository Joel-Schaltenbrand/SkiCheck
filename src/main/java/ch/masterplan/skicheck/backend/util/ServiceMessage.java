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

package ch.masterplan.skicheck.backend.util;

/**
 * Utility class representing a service message with a property and a message.
 */
public class ServiceMessage {

	private String property;
	private String message;

	/**
	 * Default constructor.
	 */
	public ServiceMessage() {
		super();
	}

	/**
	 * Constructor with a message.
	 *
	 * @param message The message to be set.
	 */
	public ServiceMessage(String message) {
		super();
		this.message = message;
	}

	/**
	 * Constructor with a property and a message.
	 *
	 * @param property The property to be set.
	 * @param message  The message to be set.
	 */
	public ServiceMessage(String property, String message) {
		super();
		this.property = property;
		this.message = message;
	}

	/**
	 * Retrieves the property of the service message.
	 *
	 * @return The property.
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * Sets the property of the service message.
	 *
	 * @param property The property to be set.
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * Retrieves the message of the service message.
	 *
	 * @return The message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message of the service message.
	 *
	 * @param message The message to be set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
