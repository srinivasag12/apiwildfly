package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.math.BigInteger;
import java.util.Date;

public class DashBoardSharedFile {

	private BigInteger id;
	private String email;
	private String type;
	private Date sharedDate;
	private BigInteger fileSize;
	private String name;

	public String getEmail() {
		return email;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Date getSharedDate() {
		return sharedDate;
	}

	public void setSharedDate(Date sharedDate) {
		this.sharedDate = sharedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public BigInteger getFileSize() {
		return fileSize;
	}

	public void setFileSize(BigInteger fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "DashBoardSharedFile [id=" + id + ", email=" + email + ", type=" + type + ", sharedDate=" + sharedDate
				+ ", fileSize=" + fileSize + ", name=" + name + "]";
	}

}
