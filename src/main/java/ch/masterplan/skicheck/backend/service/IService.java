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

package ch.masterplan.skicheck.backend.service;

import ch.masterplan.skicheck.app.configuration.HasLogger;
import ch.masterplan.skicheck.backend.util.ServiceResponse;

/**
 * Generic service interface providing basic CRUD operations for entities.
 *
 * @param <T> The type of the entity.
 */
public interface IService<T> extends HasLogger {

	/**
	 * Saves the provided entity.
	 *
	 * @param entity The entity to save.
	 * @return ServiceResponse containing information about the save operation.
	 */
	ServiceResponse<T> save(T entity);

	/**
	 * Deletes the provided entity.
	 *
	 * @param entity The entity to delete.
	 * @return ServiceResponse containing information about the delete operation.
	 */
	ServiceResponse<T> delete(T entity);

	/**
	 * Retrieves an entity by its unique identifier.
	 *
	 * @param id The unique identifier of the entity.
	 * @return ServiceResponse containing the retrieved entity or an error message.
	 */
	ServiceResponse<T> getById(Long id);

	/**
	 * Retrieves all entities of the specified type.
	 *
	 * @return ServiceResponse containing a list of all entities or an error message.
	 */
	ServiceResponse<T> getAll();

	/**
	 * Provides a default ServiceResponse instance.
	 *
	 * @return An empty ServiceResponse instance.
	 */
	default ServiceResponse<T> getServiceResponse() {
		return new ServiceResponse<>();
	}
}
