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

package ch.masterplan.skicheck.ui.util;

/**
 * Interface for notifying users about success, warnings, and errors.
 */
public interface INotifier {

	/**
	 * Notifies success with the provided HTML message.
	 *
	 * @param messageHtml The HTML message for success notification.
	 */
	void notifySuccess(String messageHtml);

	/**
	 * Notifies success as a tray notification with the provided HTML message.
	 *
	 * @param messageHtml The HTML message for success tray notification.
	 */
	void notifySuccessAsTray(String messageHtml);

	/**
	 * Notifies a warning with the provided HTML message.
	 *
	 * @param messageHtml The HTML message for warning notification.
	 */
	void notifyWarning(String messageHtml);

	/**
	 * Notifies an error with the provided HTML message.
	 *
	 * @param messageHtml The HTML message for error notification.
	 */
	void notifyError(String messageHtml);
}
