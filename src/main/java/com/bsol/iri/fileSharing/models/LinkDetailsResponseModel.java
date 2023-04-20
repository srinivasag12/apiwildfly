package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class LinkDetailsResponseModel {

	private String username;
	private String email;
	private String vesselName;
	private String linkDesc;
	private Integer linkType;
	private Integer linkId;

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

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	@Override
	public String toString() {
		return "LinkDetailsResponseModel [username=" + username + ", email=" + email + ", vesselName=" + vesselName
				+ ", linkDesc=" + linkDesc + ", linkType=" + linkType + ", linkId=" + linkId + "]";
	}

}
