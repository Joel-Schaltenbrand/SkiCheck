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
import ch.masterplan.skicheck.ui.view.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Security configuration class for the Vaadin-based web application.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity implements HasLogger {

	/**
	 * Configures HTTP security for the application.
	 *
	 * @param http The HttpSecurity object to configure.
	 * @throws Exception If an exception occurs during configuration.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log().debug("Configuring HTTP security");

		// Allow access to PNG images
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll());

		// Allow access to SVG icons from the line-awesome addon
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll());

		// Delegate to the VaadinWebSecurity parent class for further configuration
		super.configure(http);

		// Set the LoginView as the login view for the application
		setLoginView(http, LoginView.class);
	}
}
