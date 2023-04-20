package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThirdPartyUpload {

	private String vesselName;
	private String desc;
	private Date expirtyDate;
	private Integer imo;
	private String vslOfficialNo;
	
	List<DashBoardSharedFile> uploadedFile = new ArrayList<DashBoardSharedFile>();

	public String getVesselName() {
		return vesselName;
	}

	public String getDesc() {
		return desc;
	}

	public Date getExpirtyDate() {
		return expirtyDate;
	}

	public List<DashBoardSharedFile> getUploadedFile() {
		return uploadedFile;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setExpirtyDate(Date expirtyDate) {
		this.expirtyDate = expirtyDate;
	}

	public void setUploadedFile(List<DashBoardSharedFile> uploadedFile) {
		this.uploadedFile = uploadedFile;
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
		return "ThirdPartyUpload [vesselName=" + vesselName + ", desc=" + desc + ", expirtyDate=" + expirtyDate
				+ ", imo=" + imo + ", vslOfficialNo=" + vslOfficialNo + ", uploadedFile=" + uploadedFile + "]";
	}

}
