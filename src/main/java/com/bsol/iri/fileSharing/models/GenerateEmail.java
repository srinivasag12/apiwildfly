package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.ArrayList;
import java.util.List;

public class GenerateEmail {

	private List<String> email = new ArrayList<>();
	private Integer userId;
	private String vesselName;
	private Integer desc;
	private String vesselOfficialNo;
	private Integer imoNo;
	private Integer linkType;
	private int days;

	public List<String> getEmail() {
		return email;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getVesselName() {
		return vesselName;
	}

	public Integer getDesc() {
		return desc;
	}

	public String getVesselOfficialNo() {
		return vesselOfficialNo;
	}

	public Integer getImoNo() {
		return imoNo;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public int getDays() {
		return days;
	}

	public void setEmail(List<String> email) {
		this.email = email;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public void setDesc(Integer desc) {
		this.desc = desc;
	}

	public void setVesselOfficialNo(String vesselOfficialNo) {
		this.vesselOfficialNo = vesselOfficialNo;
	}

	public void setImoNo(Integer imoNo) {
		this.imoNo = imoNo;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "GenerateEmail [email=" + email + ", userId=" + userId + ", vesselName=" + vesselName + ", desc=" + desc
				+ ", vesselOfficialNo=" + vesselOfficialNo + ", imoNo=" + imoNo + ", linkType=" + linkType + ", days="
				+ days + "]";
	}

}
