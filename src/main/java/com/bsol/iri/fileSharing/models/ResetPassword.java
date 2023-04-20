package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh Model class
 */

public class ResetPassword {

	private Integer userID;
	private Integer uniqueId;
	private String pwd;
	private String hint;

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(Integer uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	@Override
	public String toString() {
		return "ResetPassword [userID=" + userID + ", uniqueId=" + uniqueId + ", pwd=" + pwd + ", hint=" + hint + "]";
	}

}
