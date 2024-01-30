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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 */
public class UserDetailsServiceImpl implements UserDetailsService, HasLogger {

	private final UserRepository userRepository;

	/**
	 * Constructs a UserDetailsServiceImpl with the provided UserRepository.
	 *
	 * @param userRepository The UserRepository instance.
	 */
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private static List<GrantedAuthority> getAuthorities(UserEntity userEntity) {
		return userEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
	}

	/**
	 * Loads user details by the provided username.
	 *
	 * @param username The username to load details for.
	 * @return The UserDetails object for the specified user.
	 * @throws UsernameNotFoundException If no user with the given username is found.
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(username);
		log().debug("returning user details for username: {}", username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("No userEntity present with username: " + username);
		} else {
			return new User(userEntity.getUsername(), userEntity.getHashedPassword(), getAuthorities(userEntity));
		}
	}
}
