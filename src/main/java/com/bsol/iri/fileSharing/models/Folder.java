package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class Folder {

	private Integer folderId;
	private String vesselName;
	private String desc;
	private String vslOfficialNo;
	private Integer imo;

	public String getVesselName() {
		return vesselName;
	}

	public String getDesc() {
		return desc;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	public String getVslOfficialNo() {
		return vslOfficialNo;
	}

	public Integer getImo() {
		return imo;
	}

	public void setVslOfficialNo(String vslOfficialNo) {
		this.vslOfficialNo = vslOfficialNo;
	}

	public void setImo(Integer imo) {
		this.imo = imo;
	}

	@Override
	public String toString() {
		return "Folder [folderId=" + folderId + ", vesselName=" + vesselName + ", desc=" + desc + ", vslOfficialNo="
				+ vslOfficialNo + ", imo=" + imo + "]";
	}

}
