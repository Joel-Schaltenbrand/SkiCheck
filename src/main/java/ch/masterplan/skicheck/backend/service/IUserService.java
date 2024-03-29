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

import ch.masterplan.skicheck.model.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Service interface for managing user entities.
 */
public interface IUserService extends IService<UserEntity> {

	/**
	 * Retrieves a page of user entities based on the provided Pageable.
	 *
	 * @param pageable The Pageable object specifying the page to retrieve.
	 * @return A Page containing user entities.
	 */
	Page<UserEntity> list(Pageable pageable);

	/**
	 * Retrieves a page of user entities based on the provided Pageable and filter specification.
	 *
	 * @param pageable The Pageable object specifying the page to retrieve.
	 * @param filter   The filter specification to apply.
	 * @return A Page containing user entities matching the filter.
	 */
	Page<UserEntity> list(Pageable pageable, Specification<UserEntity> filter);
}
