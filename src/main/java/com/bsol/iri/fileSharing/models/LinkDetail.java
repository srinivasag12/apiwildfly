package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class LinkDetail {

	private String vessel;
	private Integer desc;
	private Integer userId;
	private Integer imo;
	private String vslOfficialNo;

	public String getVessel() {
		return vessel;
	}

	public Integer getDesc() {
		return desc;
	}

	public void setVessel(String vessel) {
		this.vessel = vessel;
	}

	public void setDesc(Integer desc) {
		this.desc = desc;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
		return "LinkDetail [vessel=" + vessel + ", desc=" + desc + ", userId=" + userId + ", imo=" + imo
				+ ", vslOfficialNo=" + vslOfficialNo + "]";
	}

}
