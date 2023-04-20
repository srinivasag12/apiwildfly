package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class LinkIdWithEmail {

	private Integer LINKID;
	private String EMAIL;

	public Integer getLINKID() {
		return LINKID;
	}

	public void setLINKID(java.math.BigDecimal lINKID) {
		LINKID = lINKID.intValue();
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	@Override
	public String toString() {
		return "LinkIdWithEmail [LINKID=" + LINKID + ", EMAIL=" + EMAIL + "]";
	}

}
