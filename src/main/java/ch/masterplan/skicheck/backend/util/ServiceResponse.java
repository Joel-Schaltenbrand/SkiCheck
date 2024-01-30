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

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class representing a service response containing information, error messages,
 * business objects, and the success status of an operation.
 *
 * @param <T> The type of business objects.
 */
public class ServiceResponse<T> {

	private ServiceMessage infoMessage;
	private ServiceMessage errorMessage;
	private List<T> businessObjects = new ArrayList<>();
	private boolean operationWasSuccessful;

	/**
	 * Retrieves the information message of the service response.
	 *
	 * @return The information message.
	 */
	public ServiceMessage getInfoMessage() {
		return infoMessage;
	}

	/**
	 * Sets the information message of the service response.
	 *
	 * @param message The information message to be set.
	 */
	public void setInfoMessage(ServiceMessage message) {
		infoMessage = message;
	}

	/**
	 * Retrieves the error message of the service response.
	 *
	 * @return The error message.
	 */
	public ServiceMessage getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message of the service response.
	 *
	 * @param message The error message to be set.
	 */
	public void setErrorMessage(ServiceMessage message) {
		errorMessage = message;
	}

	/**
	 * Checks if the service response has an information message.
	 *
	 * @return True if an information message is present, otherwise false.
	 */
	public boolean hasInfoMessage() {
		return infoMessage != null;
	}

	/**
	 * Checks if the service response has an error message.
	 *
	 * @return True if an error message is present, otherwise false.
	 */
	public boolean hasErrorMessage() {
		return errorMessage != null;
	}

	/**
	 * Adds a list of business objects to the existing list in the service response.
	 *
	 * @param businessObjects The list of business objects to be added.
	 */
	public void addBusinessObjects(List<T> businessObjects) {
		if (this.businessObjects == null) {
			this.businessObjects = new ArrayList<>();
		}
		this.businessObjects.addAll(businessObjects);
	}

	/**
	 * Adds a single business object to the existing list in the service response.
	 *
	 * @param businessObject The business object to be added.
	 */
	public void addBusinessObject(T businessObject) {
		if (this.businessObjects == null) {
			this.businessObjects = new ArrayList<>();
		}
		this.businessObjects.add(businessObject);
	}

	/**
	 * Retrieves the list of business objects from the service response.
	 *
	 * @return The list of business objects.
	 */
	public List<T> getBusinessObjects() {
		return this.businessObjects;
	}

	/**
	 * Sets the list of business objects in the service response.
	 *
	 * @param businessObjects The list of business objects to be set.
	 */
	public void setBusinessObjects(List<T> businessObjects) {
		this.businessObjects = businessObjects;
	}

	/**
	 * Checks if the service response has any business objects.
	 *
	 * @return True if business objects are present, otherwise false.
	 */
	public boolean hasBusinessObjects() {
		return this.businessObjects != null && !this.businessObjects.isEmpty();
	}

	/**
	 * Checks if the service response has any messages (information or error messages).
	 *
	 * @return True if any messages are present, otherwise false.
	 */
	public boolean hasAnyMessages() {
		return hasErrorMessage() || hasInfoMessage();
	}

	/**
	 * Retrieves the success status of the operation in the service response.
	 *
	 * @return True if the operation was successful, otherwise false.
	 */
	public boolean getOperationWasSuccessful() {
		return this.operationWasSuccessful;
	}

	/**
	 * Sets the success status of the operation in the service response.
	 *
	 * @param operationWasSuccessful The success status to be set.
	 */
	public void setOperationWasSuccessful(boolean operationWasSuccessful) {
		this.operationWasSuccessful = operationWasSuccessful;
	}
}
