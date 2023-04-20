package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ForgetPassword {

	private String email;
	private Integer userId;
	private Integer otp;

	
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

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "ForgetPassword [email=" + email + ", userId=" + userId + ", otp=" + otp + "]";
	}

}
