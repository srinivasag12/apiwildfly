package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.Date;

public class DashBoardLinkDetails {

	private String email;
	private String vesselname;
	private String desc;
	private Date sharedDate;
	private int linkStatus;
	private int loggedInStatus;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVesselname() {
		return vesselname;
	}

	public void setVesselname(String vesselname) {
		this.vesselname = vesselname;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getSharedDate() {
		return sharedDate;
	}

	public void setSharedDate(Date sharedDate) {
		this.sharedDate = sharedDate;
	}

	public int getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(int linkStatus) {
		this.linkStatus = linkStatus;
	}

	public int getLoggedInStatus() {
		return loggedInStatus;
	}

	public void setLoggedInStatus(int loggedInStatus) {
		this.loggedInStatus = loggedInStatus;
	}

	@Override
	public String toString() {
		return "DashBoardLinkDetails [email=" + email + ", vesselname=" + vesselname + ", desc=" + desc
				+ ", sharedDate=" + sharedDate + ", linkStatus=" + linkStatus + ", loggedInStatus=" + loggedInStatus
				+ "]";
	}

}
