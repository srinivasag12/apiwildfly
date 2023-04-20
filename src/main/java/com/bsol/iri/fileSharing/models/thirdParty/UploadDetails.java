package com.bsol.iri.fileSharing.models.thirdParty;


/**
 * 
 * @author rupesh
 */


public class UploadDetails {

	private String email;
	private Integer fileOwner;
	private Integer linkId;

	public UploadDetails(String email, Integer fileOwner, Integer linkId) {

		this.email = email;
		this.fileOwner = fileOwner;
		this.linkId = linkId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFileOwner() {
		return fileOwner;
	}

	public void setFileOwner(Integer fileOwner) {
		this.fileOwner = fileOwner;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	@Override
	public String toString() {
		return "UploadDetails [email=" + email + ", fileOwner=" + fileOwner + ", linkId=" + linkId + "]";
	}

}
