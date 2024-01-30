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

package ch.masterplan.skicheck.app.security;

import ch.masterplan.skicheck.app.configuration.HasLogger;
import ch.masterplan.skicheck.backend.dao.UserRepository;
import ch.masterplan.skicheck.model.user.UserEntity;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Represents an authenticated user in the application.
 */
public class AuthenticatedUser implements HasLogger {

	private final UserRepository userRepository;
	private final AuthenticationContext authenticationContext;

	/**
	 * Constructs an AuthenticatedUser with the provided AuthenticationContext and UserRepository.
	 *
	 * @param authenticationContext The AuthenticationContext instance.
	 * @param userRepository        The UserRepository instance.
	 */
	public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
		this.userRepository = userRepository;
		this.authenticationContext = authenticationContext;
	}

	/**
	 * Retrieves the authenticated user from the database.
	 *
	 * @return An Optional containing the authenticated user if present, otherwise an empty Optional.
	 */
	@Transactional
	public Optional<UserEntity> get() {
		log().debug("Returning authenticated user");
		return authenticationContext.getAuthenticatedUser(UserDetails.class).map(userDetails -> userRepository.findByUsername(userDetails.getUsername()));
	}

	/**
	 * Logs out the authenticated user.
	 */
	public void logout() {
		log().debug("Logging out authenticated user");
		authenticationContext.logout();
	}

}
