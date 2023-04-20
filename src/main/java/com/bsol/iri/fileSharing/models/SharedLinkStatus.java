package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.Date;

public class SharedLinkStatus {

	private String EMAIL;
	private Date createdTime;
	private String status;

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SharedLinkStatus [EMAIL=" + EMAIL + ", createdTime=" + createdTime + ", status=" + status + "]";
	}

}
