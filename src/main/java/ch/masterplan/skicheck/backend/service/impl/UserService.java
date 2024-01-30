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

package ch.masterplan.skicheck.backend.service.impl;

import ch.masterplan.skicheck.app.configuration.HasLogger;
import ch.masterplan.skicheck.backend.dao.IUserDAO;
import ch.masterplan.skicheck.backend.service.IUserService;
import ch.masterplan.skicheck.backend.util.ServiceFailureNote;
import ch.masterplan.skicheck.backend.util.ServiceMessage;
import ch.masterplan.skicheck.backend.util.ServiceResponse;
import ch.masterplan.skicheck.model.user.UserEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing user-related operations in the application.
 */
public class UserService implements IUserService, HasLogger {

	private final IUserDAO dao;
	private final LanguageService languageService;


	/**
	 * Constructs a UserService with the provided IUserDAO and LanguageService.
	 *
	 * @param dao             The IUserDAO instance.
	 * @param languageService The LanguageService instance.
	 */
	public UserService(IUserDAO dao, LanguageService languageService) {
		this.dao = dao;
		this.languageService = languageService;
	}

	/**
	 * Saves a user in the system.
	 *
	 * @param entity The user to be saved.
	 * @return ServiceResponse with the operation result and the saved user.
	 */
	@Override
	public ServiceResponse<UserEntity> save(UserEntity entity) {
		ServiceResponse<UserEntity> serviceResponse = getServiceResponse();
		try {
			log().debug("received user for saving");
			UserEntity userEntity = dao.save(entity);
			serviceResponse.setOperationWasSuccessful(true);
			if (userEntity != null) {
				serviceResponse.addBusinessObject(userEntity);
				log().debug("user {} has been saved", entity != null ? entity.getId() : "null");
				serviceResponse.setInfoMessage(new ServiceMessage(languageService.getMessage4Key("userService.message.saved")));
			} else {
				log().error("user could not been saved");
				serviceResponse.setOperationWasSuccessful(false);
				serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("userService.message.saveError")));
			}
		} catch (DataAccessException ex) {
			log().error(ex.getMessage());
			serviceResponse.setOperationWasSuccessful(false);
			serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("userService.message.saveError")));
		}
		return serviceResponse;
	}

	/**
	 * Deletes a user from the system.
	 *
	 * @param entity The user to be deleted.
	 * @return ServiceResponse with the operation result.
	 */
	@Override
	public ServiceResponse<UserEntity> delete(UserEntity entity) {
		ServiceResponse<UserEntity> serviceResponse = getServiceResponse();
		try {
			log().debug("received user {} for deleting", entity != null ? entity.getId() : "null");
			dao.delete(entity);
			serviceResponse.setOperationWasSuccessful(true);
			serviceResponse.setInfoMessage(new ServiceMessage(languageService.getMessage4Key("userService.message.deleted")));
		} catch (DataAccessException ex) {
			log().error("user could not been deleted");
			serviceResponse.setFailureNote(ServiceFailureNote.parse(ex.getClass()));
			serviceResponse.setOperationWasSuccessful(false);
		}
		return serviceResponse;
	}

	/**
	 * Retrieves a user by ID.
	 *
	 * @param id The ID of the user to be retrieved.
	 * @return ServiceResponse with the operation result and the retrieved user.
	 */
	@Override
	public ServiceResponse<UserEntity> getById(Long id) {
		ServiceResponse<UserEntity> serviceResponse = getServiceResponse();
		Optional<UserEntity> userEntity;
		if (id != null) {
			log().debug("looking for user by id: " + id);
			try {
				userEntity = dao.findById(id);
				serviceResponse.setOperationWasSuccessful(true);
				if (userEntity.isPresent()) {
					serviceResponse.addBusinessObject(userEntity.get());
					log().debug("found user by id: " + id);
				} else {
					log().warn("user with id {} not found", id);
					serviceResponse.setOperationWasSuccessful(true);
					serviceResponse.setInfoMessage(new ServiceMessage(languageService.getMessage4Key("userService.message.notfound")));
				}

			} catch (DataAccessException ex) {
				log().error(ex.getMessage());
				serviceResponse.setOperationWasSuccessful(false);
				serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("general.message.error")));
			}
		} else {
			log().warn("given user id was null");
			serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("userService.message.givenIdWasNull")));
		}
		return serviceResponse;
	}

	/**
	 * Retrieves all users in the system.
	 *
	 * @return ServiceResponse with the operation result and a list of all users.
	 */
	@Override
	public ServiceResponse<UserEntity> getAll() {
		ServiceResponse<UserEntity> serviceResponse = getServiceResponse();
		List<UserEntity> cronJobReminderReportEntities;

		log().debug("all user from database requested: ");
		try {
			cronJobReminderReportEntities = dao.findAll();
			serviceResponse.setOperationWasSuccessful(true);
			if (cronJobReminderReportEntities != null && !cronJobReminderReportEntities.isEmpty()) {
				serviceResponse.setBusinessObjects(cronJobReminderReportEntities);
				log().debug("found: " + cronJobReminderReportEntities.size() + " users");
			} else {
				log().debug("found: 0 users");
				serviceResponse.setOperationWasSuccessful(true);
				serviceResponse.setInfoMessage(new ServiceMessage(languageService.getMessage4Key("userService.message.notfound")));
			}
		} catch (DataAccessException ex) {
			log().error(ex.getMessage());
			serviceResponse.setOperationWasSuccessful(false);
			serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("general.message.error")));
		}

		return serviceResponse;
	}

	/**
	 * Retrieves a page of users.
	 *
	 * @param pageable Pagination information.
	 * @return A page of users.
	 */
	@Override
	public Page<UserEntity> list(Pageable pageable) {
		log().debug("Returning user entities for page: {}", pageable);
		return dao.findAll(pageable);
	}

	/**
	 * Retrieves a page of users considering a filter.
	 *
	 * @param pageable Pagination information.
	 * @param filter   The filter for user search.
	 * @return A page of users based on the specified filter.
	 */
	@Override
	public Page<UserEntity> list(Pageable pageable, Specification<UserEntity> filter) {
		log().debug("Returning user entities for page: {} with filter: {}", pageable, filter);
		return dao.findAll(pageable, filter);
	}

	/**
	 * Resets the payment status of all users in the system.
	 *
	 * @return ServiceResponse with the operation result.
	 */
	@Override
	public ServiceResponse<UserEntity> resetAllPaymentStatus() {
		ServiceResponse<UserEntity> serviceResponse = getServiceResponse();
		try {
			log().debug("resetting all payment status");
			dao.resetAllPaymentStatus();
			serviceResponse.setOperationWasSuccessful(true);
			serviceResponse.setInfoMessage(new ServiceMessage(languageService.getMessage4Key("userService.message.paymentStatusReset")));
		} catch (DataAccessException ex) {
			log().error(ex.getMessage());
			serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("userService.message.paymentStatusResetError")));
			serviceResponse.setOperationWasSuccessful(false);
		}
		return serviceResponse;
	}
}
