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

import ch.masterplan.skicheck.model.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

/**
 * Interface for Data Access Objects (DAO) handling CRUD operations for UserEntity entities.
 */
public interface IUserDAO extends IEntityCrudDAO<UserEntity> {

	/**
	 * Retrieves a user entity by its ID.
	 *
	 * @param id The ID of the user entity to retrieve.
	 * @return An Optional containing the user entity if found, otherwise an empty Optional.
	 */
	Optional<UserEntity> findById(Long id);

	/**
	 * Retrieves a page of user entities.
	 *
	 * @param pageable The Pageable object specifying the page and size.
	 * @return A Page containing user entities.
	 */
	Page<UserEntity> findAll(Pageable pageable);

	/**
	 * Retrieves a filtered and paginated list of user entities.
	 *
	 * @param pageable The Pageable object specifying the page and size.
	 * @param filter   The Specification object representing the filter criteria.
	 * @return A Page containing filtered user entities.
	 */
	Page<UserEntity> findAll(Pageable pageable, Specification<UserEntity> filter);

	/**
	 * Retrieves a user entity by its username.
	 */
	void resetAllPaymentStatus();
}
