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

package ch.masterplan.skicheck.model.user;

import ch.masterplan.skicheck.model.userdetail.UserDetailEntity;
import ch.masterplan.skicheck.model.util.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

import java.util.Set;

/**
 * Entity class representing a user in the application.
 */
@Entity
@Table(name = "application_user")
public class UserEntity extends AbstractEntity {

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Email
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@JsonIgnore
	@Column(name = "hashed_password", nullable = false)
	private String hashedPassword;

	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	private Set<Role> roles;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_details_id", referencedColumnName = "id")
	private UserDetailEntity userDetails;

	/**
	 * Retrieves the full name of the user.
	 *
	 * @return The full name of the user.
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * Retrieves the username of the user.
	 *
	 * @return The username of the user.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of the user.
	 *
	 * @param username The username to be set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retrieves the first name of the user.
	 *
	 * @return The first name of the user.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the user.
	 *
	 * @param firstName The first name to be set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Retrieves the last name of the user.
	 *
	 * @return The last name of the user.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the user.
	 *
	 * @param lastName The last name to be set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Retrieves the email of the user.
	 *
	 * @return The email of the user.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of the user.
	 *
	 * @param email The email to be set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Retrieves the hashed password of the user.
	 *
	 * @return The hashed password of the user.
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * Sets the hashed password of the user.
	 *
	 * @param hashedPassword The hashed password to be set.
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	/**
	 * Retrieves the roles of the user.
	 *
	 * @return The roles of the user.
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles of the user.
	 *
	 * @param roles The roles to be set.
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Retrieves the user details of the user.
	 *
	 * @return The user details of the user.
	 */
	public UserDetailEntity getUserDetails() {
		return userDetails;
	}

	/**
	 * Sets the user details of the user.
	 *
	 * @param userDetails The user details to be set.
	 */
	public void setUserDetails(UserDetailEntity userDetails) {
		this.userDetails = userDetails;
	}
}
