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

package ch.masterplan.skicheck.backend.dao;

import org.springframework.dao.DataAccessException;

/**
 * Extension of IEntityDAO interface for CRUD (Create, Read, Update, Delete) operations on entities.
 *
 * @param <T> The type of entity.
 */
public interface IEntityCrudDAO<T> extends IEntityDAO<T> {

	/**
	 * Deletes the specified entity.
	 *
	 * @param entity The entity to be deleted.
	 * @throws DataAccessException If an error occurs while deleting the entity.
	 */
	void delete(T entity) throws DataAccessException;

	/**
	 * Saves or updates the specified entity.
	 *
	 * @param entity The entity to be saved or updated.
	 * @return The saved or updated entity.
	 * @throws DataAccessException If an error occurs while saving or updating the entity.
	 */
	T save(T entity) throws DataAccessException;
}
