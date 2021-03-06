/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.devnexus.ting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

/**
 * The persistent class for the users database table.
 *
 */
@Entity
public class User extends BaseModelObject implements Serializable, SocialUserDetails {

	private static final long serialVersionUID = 1L;

	@XmlID
	@NotNull
	@Size(min = 3, max = 50)
	@Column(unique = true)
	private String username;

	@Size(max = 120)
	@JsonIgnore
	private String password;

	@NotNull
	@Size(max = 50)
	private String firstName;

	@NotNull
	@Size(max = 50)
	private String lastName;

	@Size(max = 50)
	@Email
	@JsonIgnore
	private String email;

	@XmlAttribute
	@JsonIgnore
	// FIXME @XmlJavaTypeAdapter(JaxbDateAdapter.class)
	private Date registrationDate;

	@XmlAttribute
	@JsonIgnore
	// FIXME @XmlJavaTypeAdapter(JaxbDateAdapter.class)
	private Date lastLoginDate;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "user")
	@XmlTransient
	@JsonIgnore
	@BatchSize(size = 20)
	private Set<UserAuthority> userAuthorities = new HashSet<>(0);

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "user")
	@XmlTransient
	@JsonIgnore
	@BatchSize(size = 20)
	private Set<MobileSignIn> mobileTokens = new HashSet<>(0);

	// ~~~~Constructor~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public User() {
	}

	// ~~~~Getters and
	// Setters~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the registrationDate
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate
	 *            the registrationDate to set
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		final Collection<GrantedAuthority> authorities = new java.util.ArrayList<GrantedAuthority>();
		for (GrantedAuthority authority : this.getUserAuthorities()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + authority.getAuthority()));
		}
		// authorities.addAll(this.getUserAuthorities());
		return authorities;
	}

	public Set<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public Set<MobileSignIn> getMobileTokens() {
		return mobileTokens;
	}

	public void setMobileTokens(Set<MobileSignIn> mobileTokens) {
		this.mobileTokens = mobileTokens;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User{" + "username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", registrationDate=" + registrationDate + ", lastLoginDate="
				+ lastLoginDate + '}';
	}

	@Override
	public String getUserId() {
		return this.getUsername();
	}

}
