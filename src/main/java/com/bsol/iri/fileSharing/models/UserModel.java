package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UserModel {

	private String firstname;
	private String lastName;
	private Integer roleId;
	private String email;
	private String pwd;
	private Integer status;
	private Integer userId;

	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserModel [firstname=" + firstname + ", lastName=" + lastName + ", roleId=" + roleId + ", email="
				+ email + ", pwd=" + pwd + ", status=" + status + ", userId=" + userId + "]";
	}
}
