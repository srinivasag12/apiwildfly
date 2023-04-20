package com.bsol.iri.fileSharing.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author rupesh Model class
 */

@JsonInclude(value = Include.NON_NULL)
public class UpdatePwd {

	private Integer userId;
	private String pwd;
	private String oldPwd;
	private String pwdHint;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getPwdHint() {
		return pwdHint;
	}

	public void setPwdHint(String pwdHint) {
		this.pwdHint = pwdHint;
	}

	@Override
	public String toString() {
		return "UpdatePwd [userId=" + userId + ", pwd=" + pwd + ", oldPwd=" + oldPwd + ", pwdHint=" + pwdHint + "]";
	}

}
