package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.math.BigInteger;
import java.util.Date;

public class DashBoardData {

	private String email;
	private String type;
	private Date createdTime;
	private BigInteger fileSize;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public BigInteger getFileSize() {
		return fileSize;
	}

	public void setFileSize(BigInteger fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "DashBoardData [email=" + email + ", type=" + type + ", createdTime=" + createdTime + ", fileSize="
				+ fileSize + "]";
	}

}
