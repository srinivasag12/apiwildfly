package com.bsol.iri.fileSharing.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DashboardArchiveFilesToDelete {

	private String FILENAME;
	private Object ID;
	private Date ARCHIEVEDON;
	private Date WILLBEDELETEDON;
	private String SHAREDBY;
	private Object RN;

	public String getFILENAME() {
		return FILENAME;
	}

	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}

	public Object getID() {
		return ID;
	}

	public void setID(Object iD) {
		ID = iD;
	}

	public Date getARCHIEVEDON() {
		return ARCHIEVEDON;
	}

	public void setARCHIEVEDON(Date aRCHIEVEDON) {
		ARCHIEVEDON = aRCHIEVEDON;
	}

	public Date getWILLBEDELETEDON() {
		return WILLBEDELETEDON;
	}

	public void setWILLBEDELETEDON(Date wILLBEDELETEDON) {
		WILLBEDELETEDON = wILLBEDELETEDON;
	}

	public String getSHAREDBY() {
		return SHAREDBY;
	}

	public void setSHAREDBY(String sHAREDBY) {
		SHAREDBY = sHAREDBY;
	}

	@JsonIgnore
	public Object getRN() {
		return RN;
	}

	public void setRN(Object rN) {
		RN = rN;
	}

	@Override
	public String toString() {
		return "DashboardArchiveFilesToDelete [FILENAME=" + FILENAME + ", ID=" + ID + ", ARCHIEVEDON=" + ARCHIEVEDON
				+ ", WILLBEDELETEDON=" + WILLBEDELETEDON + ", SHAREDBY=" + SHAREDBY + "]";
	}

}
