package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.Date;

public class SharedToThirdPartyFiles {

	private Object F_ID;
	private Object FILE_NAME;
	private Object FILE_SIZE;
	private Object FILE_TYPE;
	private Object DOWNLOAD_LINK_ID;
	private Object EMAIL;
	private Date CREATED_ON;
	private Date EXPIRED_ON;
	private Object FOLDER_ID;
	private Object VESSEL_NAME;
	private Object VSL_IMO_NO;
	private Object VSL_OFFICIAL_NO;
	private Object DESCRIPTION;
	private Object ISVIEWED;
	private Object VIEWEDON;

	public Object getISVIEWED() {
		return ISVIEWED;
	}

	public Object getVIEWEDON() {
		return VIEWEDON;
	}

	public void setVIEWEDON(Object vIEWEDON) {
		VIEWEDON = vIEWEDON;
	}

	public void setISVIEWED(Object iSVIEWED) {
		ISVIEWED = iSVIEWED;
	}

	public Object getF_ID() {
		return F_ID;
	}

	public Object getFILE_NAME() {
		return FILE_NAME;
	}

	public Object getFILE_SIZE() {
		return FILE_SIZE;
	}

	public Object getDOWNLOAD_LINK_ID() {
		return DOWNLOAD_LINK_ID;
	}

	public Object getEMAIL() {
		return EMAIL;
	}

	public Date getCREATED_ON() {
		return CREATED_ON;
	}

	public Date getEXPIRED_ON() {
		return EXPIRED_ON;
	}

	public Object getFOLDER_ID() {
		return FOLDER_ID;
	}

	public Object getVESSEL_NAME() {
		return VESSEL_NAME;
	}

	public Object getVSL_IMO_NO() {
		return VSL_IMO_NO;
	}

	public Object getVSL_OFFICIAL_NO() {
		return VSL_OFFICIAL_NO;
	}

	public Object getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setF_ID(Object f_ID) {
		F_ID = f_ID;
	}

	public void setFILE_NAME(Object fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	public void setFILE_SIZE(Object fILE_SIZE) {
		FILE_SIZE = fILE_SIZE;
	}

	public void setDOWNLOAD_LINK_ID(Object dOWNLOAD_LINK_ID) {
		DOWNLOAD_LINK_ID = dOWNLOAD_LINK_ID;
	}

	public void setEMAIL(Object eMAIL) {
		EMAIL = eMAIL;
	}

	public void setCREATED_ON(Date cREATED_ON) {
		CREATED_ON = cREATED_ON;
	}

	public void setEXPIRED_ON(Date eXPIRED_ON) {
		EXPIRED_ON = eXPIRED_ON;
	}

	public void setFOLDER_ID(Object fOLDER_ID) {
		FOLDER_ID = fOLDER_ID;
	}

	public void setVESSEL_NAME(Object vESSEL_NAME) {
		VESSEL_NAME = vESSEL_NAME;
	}

	public void setVSL_IMO_NO(Object vSL_IMO_NO) {
		VSL_IMO_NO = vSL_IMO_NO;
	}

	public void setVSL_OFFICIAL_NO(Object vSL_OFFICIAL_NO) {
		VSL_OFFICIAL_NO = vSL_OFFICIAL_NO;
	}

	public void setDESCRIPTION(Object dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public Object getFILE_TYPE() {
		return FILE_TYPE;
	}

	public void setFILE_TYPE(Object fILE_TYPE) {
		FILE_TYPE = fILE_TYPE;
	}

	@Override
	public String toString() {
		return "SharedToThirdPartyFiles [F_ID=" + F_ID + ", FILE_NAME=" + FILE_NAME + ", FILE_SIZE=" + FILE_SIZE
				+ ", FILE_TYPE=" + FILE_TYPE + ", DOWNLOAD_LINK_ID=" + DOWNLOAD_LINK_ID + ", EMAIL=" + EMAIL
				+ ", CREATED_ON=" + CREATED_ON + ", EXPIRED_ON=" + EXPIRED_ON + ", FOLDER_ID=" + FOLDER_ID
				+ ", VESSEL_NAME=" + VESSEL_NAME + ", VSL_IMO_NO=" + VSL_IMO_NO + ", VSL_OFFICIAL_NO=" + VSL_OFFICIAL_NO
				+ ", DESCRIPTION=" + DESCRIPTION + ", ISVIEWED=" + ISVIEWED + ", VIEWEDON=" + VIEWEDON + "]";
	}

}
