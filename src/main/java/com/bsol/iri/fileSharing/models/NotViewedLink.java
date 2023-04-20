package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class NotViewedLink {

	private String vessel_NAME;
	private String description;
	private Integer vsl_IMO_NO;
	private String vsl_OFFICIAL_NO;
	private String email;
	private Integer lid;

	public String getVessel_NAME() {
		return vessel_NAME;
	}

	public void setVessel_NAME(String vessel_NAME) {
		this.vessel_NAME = vessel_NAME;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getVsl_IMO_NO() {
		return vsl_IMO_NO;
	}

	public void setVsl_IMO_NO(Integer vsl_IMO_NO) {
		this.vsl_IMO_NO = vsl_IMO_NO;
	}

	public String getVsl_OFFICIAL_NO() {
		return vsl_OFFICIAL_NO;
	}

	public void setVsl_OFFICIAL_NO(String vsl_OFFICIAL_NO) {
		this.vsl_OFFICIAL_NO = vsl_OFFICIAL_NO;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	@Override
	public String toString() {
		return "NotViewedLink [vessel_NAME=" + vessel_NAME + ", description=" + description + ", vsl_IMO_NO="
				+ vsl_IMO_NO + ", vsl_OFFICIAL_NO=" + vsl_OFFICIAL_NO + ", email=" + email + ", lid=" + lid + "]";
	}

}
