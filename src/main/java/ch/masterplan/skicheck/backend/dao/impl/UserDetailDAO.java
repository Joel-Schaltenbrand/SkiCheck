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
import ch.masterplan.skicheck.backend.dao.IUserDetailDAO;
import ch.masterplan.skicheck.backend.dao.UserDetailRepository;
import ch.masterplan.skicheck.model.userdetail.UserDetailEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link IUserDetailDAO} interface for managing user detail entities.
 */
public class UserDetailDAO implements IUserDetailDAO, HasLogger {

	private final UserDetailRepository repository;

	/**
	 * Constructs a UserDetailDAO with the provided UserDetailRepository.
	 *
	 * @param repository The UserDetailRepository instance.
	 */
	public UserDetailDAO(UserDetailRepository repository) {
		this.repository = repository;
	}

	@Override
	public void delete(UserDetailEntity entity) throws DataAccessException {
		long startTime = System.nanoTime();
		this.repository.delete(entity);
		long endTime = System.nanoTime();
		log().debug("Deleting {} took {} milliseconds", entity, (endTime - startTime) / 1000000);
	}

	@Override
	public UserDetailEntity save(UserDetailEntity entity) throws DataAccessException {
		long startTime = System.nanoTime();
		UserDetailEntity userDetailEntity = repository.save(entity);
		long endTime = System.nanoTime();
		log().debug("Saving {} took {} milliseconds", entity, (endTime - startTime) / 1000000);
		return userDetailEntity;
	}

	@Override
	public List<UserDetailEntity> findAll() throws DataAccessException {
		long startTime = System.nanoTime();
		List<UserDetailEntity> userDetailEntities = repository.findAll();
		long endTime = System.nanoTime();
		log().debug("Getting all user details via findAll took {} milliseconds", (endTime - startTime) / 1000000);
		return userDetailEntities;
	}

	@Override
	public Optional<UserDetailEntity> findById(Long id) throws DataAccessException {
		long startTime = System.nanoTime();
		Optional<UserDetailEntity> userDetailEntity = repository.findById(id);
		long endTime = System.nanoTime();
		log().debug("Getting user details with id {} took {} milliseconds", id, (endTime - startTime) / 1000000);
		return userDetailEntity;
	}

	@Override
	public void resetAllPayments() {
		long startTime = System.nanoTime();
		repository.resetAllPayments();
		long endTime = System.nanoTime();
		log().debug("Deleting all user details took {} milliseconds", (endTime - startTime) / 1000000);
	}
}
