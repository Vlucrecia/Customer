package com.atos.customer.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String firstName;
	private String lastName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Customer [id=").append("firstName: ").append(firstName).append(",")
				.append("lastName: ").append(lastName).append("]");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Customer customer = (Customer) obj;
		return StringUtils.equals(this.firstName, customer.firstName)
				&& StringUtils.equals(this.lastName, customer.lastName);
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
