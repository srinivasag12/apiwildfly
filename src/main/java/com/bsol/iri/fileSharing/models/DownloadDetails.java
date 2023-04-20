package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.ArrayList;
import java.util.List;

public class DownloadDetails {
	private Object vesselName;
	private Object imo;
	private Object vesselOfficialNo;
	private Object DESCRIPTION;
	private Object expDate;

	public Object getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(Object dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	private List<com.bsol.iri.fileSharing.models.UploadedFiles> fileDetails = new ArrayList<UploadedFiles>();

	public Object getVesselName() {
		return vesselName;
	}

	public Object getImo() {
		return imo;
	}

	public Object getVesselOfficialNo() {
		return vesselOfficialNo;
	}

	public List<com.bsol.iri.fileSharing.models.UploadedFiles> getFileDetails() {
		return fileDetails;
	}

	public void setVesselName(Object vesselName) {
		this.vesselName = vesselName;
	}

	public void setImo(Object imo) {
		this.imo = imo;
	}

	public void setVesselOfficialNo(Object vesselOfficialNo) {
		this.vesselOfficialNo = vesselOfficialNo;
	}

	public void setFileDetails(List<com.bsol.iri.fileSharing.models.UploadedFiles> fileDetails) {
		this.fileDetails = fileDetails;
	}

	public Object getExpDate() {
		return expDate;
	}

	public void setExpDate(Object expDate) {
		this.expDate = expDate;
	}

	@Override
	public String toString() {
		return "DownloadDetails [vesselName=" + vesselName + ", imo=" + imo + ", vesselOfficialNo=" + vesselOfficialNo
				+ ", DESCRIPTION=" + DESCRIPTION + ", expDate=" + expDate + ", fileDetails=" + fileDetails + "]";
	}

}
