package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class CommunicationDetails {

	private String vessel;
	private String desc;
	private Integer uploadCount;
	private Integer downloadCount;
	private Integer imo;
	private String officalNo;

	public String getVessel() {
		return vessel;
	}

	public void setVessel(String vessel) {
		this.vessel = vessel;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getUploadCount() {
		return uploadCount;
	}

	public void setUploadCount(Integer uploadCount) {
		this.uploadCount = uploadCount;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public Integer getImo() {
		return imo;
	}

	public void setImo(Integer imo) {
		this.imo = imo;
	}

	public String getOfficalNo() {
		return officalNo;
	}

	public void setOfficalNo(String officalNo) {
		this.officalNo = officalNo;
	}

	@Override
	public String toString() {
		return "CommunicationDetails [vessel=" + vessel + ", desc=" + desc + ", uploadCount=" + uploadCount
				+ ", downloadCount=" + downloadCount + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
