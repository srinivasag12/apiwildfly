package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.Date;

public class ArchivedLinkDetails {

	private Integer linkId;
	private String vesselName;
	private String desc;
	private Date expiredOn;
	private Date archieveDate;
	private Integer imo;
	private String vslOfficialNo;

	public Integer getLinkId() {
		return linkId;
	}

	public String getVesselName() {
		return vesselName;
	}

	public String getDesc() {
		return desc;
	}

	public Date getExpiredOn() {
		return expiredOn;
	}

	public Date getArchieveDate() {
		return archieveDate;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setExpiredOn(Date expiredOn) {
		this.expiredOn = expiredOn;
	}

	public void setArchieveDate(Date archieveDate) {
		this.archieveDate = archieveDate;
	}

	
	public Integer getImo() {
		return imo;
	}

	public String getVslOfficialNo() {
		return vslOfficialNo;
	}

	public void setImo(Integer imo) {
		this.imo = imo;
	}

	public void setVslOfficialNo(String vslOfficialNo) {
		this.vslOfficialNo = vslOfficialNo;
	}

	@Override
	public String toString() {
		return "ArchivedLinkDetails [linkId=" + linkId + ", vesselName=" + vesselName + ", desc=" + desc
				+ ", expiredOn=" + expiredOn + ", archieveDate=" + archieveDate + ", imo=" + imo + ", vslOfficialNo="
				+ vslOfficialNo + "]";
	}

}
