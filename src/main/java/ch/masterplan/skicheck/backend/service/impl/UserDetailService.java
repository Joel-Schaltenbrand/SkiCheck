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
import ch.masterplan.skicheck.backend.dao.IUserDetailDAO;
import ch.masterplan.skicheck.backend.service.IUserDetailService;
import ch.masterplan.skicheck.backend.util.ServiceMessage;
import ch.masterplan.skicheck.backend.util.ServiceResponse;
import ch.masterplan.skicheck.model.userdetail.UserDetailEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link IUserDetailService} interface for managing user detail entities.
 */
public class UserDetailService implements IUserDetailService, HasLogger {

	private final IUserDetailDAO dao;
	private final LanguageService languageService;

	/**
	 * Constructs a UserDetailService with the provided IUserDetailDAO and LanguageService.
	 *
	 * @param dao             The IUserDetailDAO instance.
	 * @param languageService The LanguageService instance.
	 */
	public UserDetailService(IUserDetailDAO dao, LanguageService languageService) {
		this.dao = dao;
		this.languageService = languageService;
	}

	@Override
	public ServiceResponse<UserDetailEntity> save(UserDetailEntity entity) {
		ServiceResponse<UserDetailEntity> serviceResponse = getServiceResponse();
		try {
			log().debug("received user details for saving");
			UserDetailEntity userDetailEntity = dao.save(entity);
			serviceResponse.setOperationWasSuccessful(true);
			if (userDetailEntity != null) {
				serviceResponse.addBusinessObject(userDetailEntity);
				log().debug("user details {} has been saved", entity != null ? entity.getId() : "null");
				serviceResponse.setInfoMessage(new ServiceMessage(languageService.getMessage4Key("userDetailService.message.saved")));
			} else {
				log().error("user details could not been saved");
				serviceResponse.setOperationWasSuccessful(false);
				serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("userDetailService.message.saveError")));
			}
		} catch (DataAccessException ex) {
			log().error(ex.getMessage());
			serviceResponse.setOperationWasSuccessful(false);
			serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("userDetailService.message.saveError")));
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse<UserDetailEntity> delete(UserDetailEntity entity) {
		ServiceResponse<UserDetailEntity> serviceResponse = getServiceResponse();
		try {
			log().debug("received user details {} for deleting", entity != null ? entity.getId() : "null");
			dao.delete(entity);
			serviceResponse.setOperationWasSuccessful(true);
			serviceResponse.setInfoMessage(new ServiceMessage(languageService.getMessage4Key("userDetailService.message.deleted")));
		} catch (DataAccessException ex) {
			log().error(ex.getMessage());
			serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("userDetailService.message.deletedError")));
			serviceResponse.setOperationWasSuccessful(false);
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse<UserDetailEntity> getById(Long id) {
		ServiceResponse<UserDetailEntity> serviceResponse = getServiceResponse();
		Optional<UserDetailEntity> userDetailEntity;
		if (id != null) {
			log().debug("looking for user by id: {}", id);
			try {
				userDetailEntity = dao.findById(id);
				serviceResponse.setOperationWasSuccessful(true);
				if (userDetailEntity.isPresent()) {
					serviceResponse.addBusinessObject(userDetailEntity.get());
					log().debug("found user details by id: {}", id);
				} else {
					log().warn("user details with id {} not found", id);
					serviceResponse.setOperationWasSuccessful(true);
					serviceResponse.setInfoMessage(new ServiceMessage(languageService.getMessage4Key("userDetailService.message.notfound")));
				}

			} catch (DataAccessException ex) {
				log().error(ex.getMessage());
				serviceResponse.setOperationWasSuccessful(false);
				serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("general.message.error")));
			}
		} else {
			log().warn("given user details id was null");
			serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("userDetailService.message.givenIdWasNull")));
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse<UserDetailEntity> getAll() {
		ServiceResponse<UserDetailEntity> serviceResponse = getServiceResponse();
		List<UserDetailEntity> userDetailEntities;

		log().debug("all user details from database requested: ");
		try {
			userDetailEntities = dao.findAll();
			serviceResponse.setOperationWasSuccessful(true);
			if (userDetailEntities != null && !userDetailEntities.isEmpty()) {
				serviceResponse.setBusinessObjects(userDetailEntities);
				log().debug("found: {} user details", userDetailEntities.size());
			} else {
				log().debug("found: 0 user details");
				serviceResponse.setOperationWasSuccessful(true);
				serviceResponse.setInfoMessage(new ServiceMessage(languageService.getMessage4Key("userDetailService.message.notfound")));
			}
		} catch (DataAccessException ex) {
			log().error(ex.getMessage());
			serviceResponse.setOperationWasSuccessful(false);
			serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("general.message.error")));
		}

		return serviceResponse;
	}

	@Override
	public ServiceResponse<UserDetailEntity> resetAllPaymentStatus() {
		ServiceResponse<UserDetailEntity> serviceResponse = getServiceResponse();
		try {
			log().debug("resetting payment status for all users");
			dao.resetAllPayments();
			serviceResponse.setOperationWasSuccessful(true);
			serviceResponse.setInfoMessage(new ServiceMessage(languageService.getMessage4Key("userDetailService.message.paymentStatusReset")));
		} catch (DataAccessException ex) {
			log().error(ex.getMessage());
			serviceResponse.setOperationWasSuccessful(false);
			serviceResponse.setErrorMessage(new ServiceMessage(languageService.getMessage4Key("userDetailService.message.paymentStatusResetError")));
		}
		return serviceResponse;
	}
}
