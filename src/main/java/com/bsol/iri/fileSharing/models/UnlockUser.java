package com.bsol.iri.fileSharing.models;

/**
 * Created 19 July 2022
 * @author rupesh
 *	used - in manager side unlock user.
 */
public class UnlockUser {

	private String email;
	private Integer userId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UnlockUser [email=" + email + ", userId=" + userId + "]";
	}

}
