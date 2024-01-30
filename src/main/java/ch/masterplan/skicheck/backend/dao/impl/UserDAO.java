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

package ch.masterplan.skicheck.backend.dao.impl;

import ch.masterplan.skicheck.app.configuration.HasLogger;
import ch.masterplan.skicheck.backend.dao.IUserDAO;
import ch.masterplan.skicheck.backend.dao.UserRepository;
import ch.masterplan.skicheck.model.user.UserEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) implementation for CRUD operations on UserEntity entities.
 */
@Component
public class UserDAO implements IUserDAO, HasLogger {

	private final UserRepository repository;

	/**
	 * Constructs a UserDAO with the provided UserRepository.
	 *
	 * @param repository The UserRepository instance.
	 */
	public UserDAO(UserRepository repository) {
		this.repository = repository;
	}

	/**
	 * Deletes the specified user entity.
	 *
	 * @param entity The user entity to be deleted.
	 * @throws DataAccessException If an error occurs while deleting the user entity.
	 */
	@Override
	public void delete(UserEntity entity) throws DataAccessException {
		long startTime = System.nanoTime();
		this.repository.delete(entity);
		long endTime = System.nanoTime();
		log().debug("Deleting {} took {} milliseconds", entity, (endTime - startTime) / 1000000);
	}

	/**
	 * Saves or updates the specified user entity.
	 *
	 * @param entity The user entity to be saved or updated.
	 * @return The saved or updated user entity.
	 * @throws DataAccessException If an error occurs while saving or updating the user entity.
	 */
	@Override
	public UserEntity save(UserEntity entity) throws DataAccessException {
		long startTime = System.nanoTime();
		UserEntity userEntity = repository.save(entity);
		long endTime = System.nanoTime();
		log().debug("Saving {} took {} milliseconds", entity, (endTime - startTime) / 1000000);
		return userEntity;
	}

	/**
	 * Retrieves a user entity by its ID.
	 *
	 * @param id The ID of the user entity to retrieve.
	 * @return An Optional containing the user entity if found, otherwise an empty Optional.
	 * @throws DataAccessException If an error occurs while retrieving the user entity.
	 */
	@Override
	public Optional<UserEntity> findById(Long id) throws DataAccessException {
		long startTime = System.nanoTime();
		Optional<UserEntity> userEntity = repository.findById(id);
		long endTime = System.nanoTime();
		log().debug("Getting user object by ID '{}' took {} milliseconds", id, (endTime - startTime) / 1000000);
		return userEntity;
	}

	/**
	 * Retrieves a list of all user entities.
	 *
	 * @return A list containing all user entities.
	 * @throws DataAccessException If an error occurs while retrieving user entities.
	 */
	@Override
	public List<UserEntity> findAll() throws DataAccessException {
		long startTime = System.nanoTime();
		List<UserEntity> userEntities = repository.findAll();
		long endTime = System.nanoTime();
		log().debug("Getting all user objects via findAll took {} milliseconds", (endTime - startTime) / 1000000);
		return userEntities;
	}

	/**
	 * Retrieves a page of user entities.
	 *
	 * @param pageable The Pageable object specifying the page and size.
	 * @return A Page containing user entities.
	 */
	@Override
	public Page<UserEntity> findAll(Pageable pageable) {
		long startTime = System.nanoTime();
		Page<UserEntity> userEntities = repository.findAll(pageable);
		long endTime = System.nanoTime();
		log().debug("Getting all user objects via findAll with pageable took {} milliseconds", (endTime - startTime) / 1000000);
		return userEntities;
	}

	/**
	 * Retrieves a filtered and paginated list of user entities.
	 *
	 * @param pageable The Pageable object specifying the page and size.
	 * @param filter   The Specification object representing the filter criteria.
	 * @return A Page containing filtered user entities.
	 */
	@Override
	public Page<UserEntity> findAll(Pageable pageable, Specification<UserEntity> filter) {
		long startTime = System.nanoTime();
		Page<UserEntity> userEntities = repository.findAll(filter, pageable);
		long endTime = System.nanoTime();
		log().debug("Getting all user objects via findAll with filter took {} milliseconds", (endTime - startTime) / 1000000);
		return userEntities;
	}

	/**
	 * Resets the payment status of all users.
	 */
	@Override
	public void resetAllPaymentStatus() {
		long startTime = System.nanoTime();
		repository.resetAllPaymentStatus();
		long endTime = System.nanoTime();
		log().debug("Resetting all payment status took {} milliseconds", (endTime - startTime) / 1000000);
	}
}
