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

package ch.masterplan.skicheck.app.configuration;

import ch.masterplan.skicheck.app.security.AuthenticatedUser;
import ch.masterplan.skicheck.app.security.UserDetailsServiceImpl;
import ch.masterplan.skicheck.backend.dao.UserRepository;
import ch.masterplan.skicheck.backend.dao.impl.UserDAO;
import ch.masterplan.skicheck.backend.service.impl.LanguageService;
import ch.masterplan.skicheck.backend.service.impl.UserService;
import ch.masterplan.skicheck.ui.util.Notifier;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration implements HasLogger {

	/**
	 * Configures and provides the Notifier bean for displaying notifications.
	 *
	 * @param languageService The LanguageService bean.
	 * @return The initialized Notifier bean.
	 */
	@Bean
	@ConditionalOnMissingBean
	public Notifier notifier() {
		log().info("initializing notifier bean");
		return new Notifier();
	}

	/**
	 * Configures and provides the ApplicationConfiguration bean for application-wide configuration.
	 *
	 * @param passwordEncoder The PasswordEncoder bean.
	 * @return The initialized ApplicationConfiguration bean.
	 */
	@Bean
	@ConditionalOnMissingBean
	public ApplicationConfiguration applicationConfiguration(final PasswordEncoder passwordEncoder) {
		log().info("initializing application configuration bean");
		return new ApplicationConfiguration(passwordEncoder);
	}

	/**
	 * Configures and provides the PasswordEncoder bean for secure password storage.
	 *
	 * @return The initialized PasswordEncoder bean.
	 */
	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		log().info("initializing password encoder bean");
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configures and provides the AuthenticatedUser bean, representing the authenticated user.
	 *
	 * @param authenticationContext The AuthenticationContext bean.
	 * @param userRepository        The UserRepository bean.
	 * @return The initialized AuthenticatedUser bean.
	 */
	@Bean
	@ConditionalOnMissingBean
	public AuthenticatedUser authenticatedUser(final AuthenticationContext authenticationContext, final UserRepository userRepository) {
		log().info("initializing authenticated user bean");
		return new AuthenticatedUser(authenticationContext, userRepository);
	}

	/**
	 * Configures and provides the UserService bean for managing user-related operations.
	 *
	 * @param userDAO The UserDAO bean.
	 * @return The initialized UserService bean.
	 */
	@Bean
	@ConditionalOnMissingBean
	public UserService userService(final UserDAO userDAO, final LanguageService languageService) {
		log().info("initializing user service bean");
		return new UserService(userDAO, languageService);
	}

	/**
	 * Configures and provides the UserDetailsServiceImpl bean for Spring Security's UserDetailsService.
	 *
	 * @param userRepository The UserRepository bean.
	 * @return The initialized UserDetailsServiceImpl bean.
	 */
	@Bean
	@ConditionalOnMissingBean
	public UserDetailsServiceImpl userDetailsService(final UserRepository userRepository) {
		log().info("initializing user details service bean");
		return new UserDetailsServiceImpl(userRepository);
	}

	/**
	 * Configures and provides the UserDAO bean for interacting with user data.
	 *
	 * @param userRepository The UserRepository bean.
	 * @return The initialized UserDAO bean.
	 */
	@Bean
	@ConditionalOnMissingBean
	public UserDAO userDAO(final UserRepository userRepository) {
		log().info("initializing user dao bean");
		return new UserDAO(userRepository);
	}

	/**
	 * Configures and provides the LanguageService bean.
	 *
	 * @param messageSource The MessageSource bean.
	 * @return The initialized LanguageService bean.
	 */
	@Bean
	@ConditionalOnMissingBean
	public LanguageService languageService(final MessageSource messageSource) {
		log().info("initializing language service bean");
		return new LanguageService(messageSource);
	}

	/**
	 * Configures and provides the MessageSource bean for internationalization.
	 *
	 * @return The initialized MessageSource bean.
	 */
	@Bean
	@ConditionalOnMissingBean
	public MessageSource messageSource() {
		log().info("initializing message source bean");
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:i18n/messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(0);
		return messageSource;
	}
}
