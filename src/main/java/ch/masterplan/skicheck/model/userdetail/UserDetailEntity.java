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

package ch.masterplan.skicheck.model.userdetail;

import ch.masterplan.skicheck.model.user.Equipment;
import ch.masterplan.skicheck.model.user.UserEntity;
import ch.masterplan.skicheck.model.util.AbstractEntity;
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

import java.io.Serial;
import java.util.Set;

/**
 * Entity class representing the details of a user.
 */
@Entity
@Table(name = "user_details")
public class UserDetailEntity extends AbstractEntity {

	@Serial
	private static final long serialVersionUID = 27863522765598725L;

	@Column(name = "has_paid", nullable = false)
	private boolean hasPaid;

	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_equipment", joinColumns = @JoinColumn(name = "user_id"))
	private Set<Equipment> equipment;

	@OneToOne(mappedBy = "userDetails")
	private UserEntity user;

	/**
	 * Checks if the user has paid.
	 *
	 * @return true if the user has paid, false otherwise.
	 */
	public boolean hasPaid() {
		return hasPaid;
	}

	/**
	 * Sets whether the user has paid.
	 *
	 * @param hasPaid true if the user has paid, false otherwise.
	 */
	public void setHasPaid(boolean hasPaid) {
		this.hasPaid = hasPaid;
	}

	/**
	 * Retrieves the user's equipment.
	 *
	 * @return The user's equipment.
	 */
	public Set<Equipment> getEquipment() {
		return equipment;
	}

	/**
	 * Sets the user's equipment.
	 *
	 * @param equipment The equipment to be set.
	 */
	public void setEquipment(Set<Equipment> equipment) {
		this.equipment = equipment;
	}

	/**
	 * Retrieves the user.
	 *
	 * @return The user.
	 */
	public UserEntity getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user The user to be set.
	 */
	public void setUser(UserEntity user) {
		this.user = user;
	}
}
