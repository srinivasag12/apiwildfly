package com.bsol.iri.fileSharing.models;
/**
 * 
 * @author rupesh
 *	Model class
 */
import java.util.Date;

public class AllLinkDetailsResponse {

	private String email;
	private Date genDateTime;
	private String link;
	private String linkDesc;
	private Date expiryDate;
	private int linkStatus;
	private Integer loggedInStatus;
	private String vesselName;
	private int extendedDays;
	private Integer imo;
	private String vslOfficialNo;
	private Integer isViewd;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getGenDateTime() {
		return genDateTime;
	}

	public void setGenDateTime(Date genDateTime) {
		this.genDateTime = genDateTime;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLinkDesc() {
		return linkDesc;
	}

	public void setLinkDesc(String linkDesc) {
		this.linkDesc = linkDesc;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
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

	public void setLoggedInStatus(Integer loggedInStatus) {
		this.loggedInStatus = loggedInStatus;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public int getExtendedDays() {
		return extendedDays;
	}

	public void setExtendedDays(int extendedDays) {
		this.extendedDays = extendedDays;
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

	public Integer getIsViewd() {
		return isViewd;
	}

	public void setIsViewd(Integer isViewd) {
		this.isViewd = isViewd;
	}

	@Override
	public String toString() {
		return "AllLinkDetailsResponse [email=" + email + ", genDateTime=" + genDateTime + ", link=" + link
				+ ", linkDesc=" + linkDesc + ", expiryDate=" + expiryDate + ", linkStatus=" + linkStatus
				+ ", loggedInStatus=" + loggedInStatus + ", vesselName=" + vesselName + ", extendedDays=" + extendedDays
				+ ", imo=" + imo + ", vslOfficialNo=" + vslOfficialNo + "]";
	}

}
