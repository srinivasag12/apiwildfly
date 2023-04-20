package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class LinkDetailsModel {

	private Integer userId;
	private String username;
	private String email;
	private String vesselName;
	private String linkDesc;
	private Date ExpiryDate;
	private int isExtended;
	private int linkStatus;
	private int cancelRequest;
	private int loggedInStatus;
	private int linkType;
	private Integer linkId;
	private Integer isViewed;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getLinkDesc() {
		return linkDesc;
	}

	public void setLinkDesc(String linkDesc) {
		this.linkDesc = linkDesc;
	}

	public Date getExpiryDate() {
		return ExpiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		ExpiryDate = expiryDate;
	}

	public int getIsExtended() {
		return isExtended;
	}

	public void setIsExtended(int isExtended) {
		this.isExtended = isExtended;
	}

	public int getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(int linkStatus) {
		this.linkStatus = linkStatus;
	}

	public int getCancelRequest() {
		return cancelRequest;
	}

	public void setCancelRequest(int cancelRequest) {
		this.cancelRequest = cancelRequest;
	}

	public int getLoggedInStatus() {
		return loggedInStatus;
	}

	public void setLoggedInStatus(int loggedInStatus) {
		this.loggedInStatus = loggedInStatus;
	}

	public int getLinkType() {
		return linkType;
	}

	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public Integer getIsViewed() {
		return isViewed;
	}

	public void setIsViewed(Integer isViewed) {
		this.isViewed = isViewed;
	}

	@Override
	public String toString() {
		return "LinkDetailsModel [userId=" + userId + ", username=" + username + ", email=" + email + ", vesselName="
				+ vesselName + ", linkDesc=" + linkDesc + ", ExpiryDate=" + ExpiryDate + ", isExtended=" + isExtended
				+ ", linkStatus=" + linkStatus + ", cancelRequest=" + cancelRequest + ", loggedInStatus="
				+ loggedInStatus + ", linkType=" + linkType + ", linkId=" + linkId + "]";
	}

}
