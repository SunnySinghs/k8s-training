package com.sourabh.java.springbootapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("email")
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", email=" + email + "]";
	}
	
	
}
