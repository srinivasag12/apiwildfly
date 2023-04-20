package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.Date;

public class LinkHistory {

	private Integer linkId;
	private String email;
	private Date createdDate;
	private Date ExpiryDate;
	private Integer imo;
	private String vslOfficialNo;
	private Date submittedOn;

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getExpiryDate() {
		return ExpiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		ExpiryDate = expiryDate;
	}

	public Integer getImo() {
		return imo;
	}

	public void setImo(Integer imo) {
		this.imo = imo;
	}

	public String getVslOfficialNo() {
		return vslOfficialNo;
	}

	public void setVslOfficialNo(String vslOfficialNo) {
		this.vslOfficialNo = vslOfficialNo;
	}
	
	

	public Date getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
	}

	@Override
	public String toString() {
		return "LinkHistory [linkId=" + linkId + ", email=" + email + ", createdDate=" + createdDate + ", ExpiryDate="
				+ ExpiryDate + ", imo=" + imo + ", vslOfficialNo=" + vslOfficialNo + ", submittedOn=" + submittedOn
				+ "]";
	}

}
