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

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Enumeration representing various failure notes that can occur during service operations.
 */
public enum ServiceFailureNote {
	// Failure note indicating a failure in establishing a connection to the database.
	DATABASE_CONNECTION_FAILED,

	// Failure note indicating a violation of database constraints (e.g., unique key constraint).
	DATABASE_CONSTRAINT_VIOLATION,

	// Failure note indicating illegal or invalid parameter values.
	ILLEGAL_PARAMETER_VALUES;

	/**
	 * Parses a DataAccessException class and returns the corresponding ServiceFailureNote.
	 *
	 * @param dataAccessExceptionClass The class of the DataAccessException.
	 * @return The parsed ServiceFailureNote.
	 */
	public static ServiceFailureNote parse(Class dataAccessExceptionClass) {
		if (DataIntegrityViolationException.class.equals(dataAccessExceptionClass)) {
			return DATABASE_CONSTRAINT_VIOLATION;
		} else {
			return DATABASE_CONNECTION_FAILED;
		}
	}
}
