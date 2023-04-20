package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class SharedFileResponse {
	private Integer F_ID;
	private String FILE_NAME;
	private int ISFOLDER;
	private String FILE_TYPE;
	private Long FILE_SIZE;
	private String EMAIL;
	private String VESSEL_NAME;
	private String DESCRIPTION;
	private Integer LID;
	private String UPLOADEDBY;
	private Date UPLOADEDAT;
	private String VSL_OFFICIAL_NO;
	private Object VSL_IMO_NO;
	private Object ISVIEWED;

	public Integer getF_ID() {
		return F_ID;
	}

	public void setF_ID(BigDecimal f_ID) {
		F_ID = f_ID.intValue();
	}

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	public int getISFOLDER() {
		return ISFOLDER;
	}

	public void setISFOLDER(BigDecimal iSFOLDER) {
		ISFOLDER = iSFOLDER.intValue();
	}

	public String getFILE_TYPE() {
		return FILE_TYPE;
	}

	public void setFILE_TYPE(String fILE_TYPE) {
		FILE_TYPE = fILE_TYPE;
	}

	public Long getFILE_SIZE() {
		return FILE_SIZE;
	}

	public void setFILE_SIZE(BigDecimal fILE_SIZE) {
		FILE_SIZE = fILE_SIZE.longValue();
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getVESSEL_NAME() {
		return VESSEL_NAME;
	}

	public void setVESSEL_NAME(String vESSEL_NAME) {
		VESSEL_NAME = vESSEL_NAME;
	}

	public Integer getLID() {
		return LID;
	}

	public void setLID(BigDecimal lID) {
		LID = lID.intValue();
	}

	public String getUPLOADEDBY() {
		return UPLOADEDBY;
	}

	public void setUPLOADEDBY(String uPLOADEDBY) {
		UPLOADEDBY = uPLOADEDBY;
	}

	public Date getUPLOADEDAT() {
		return UPLOADEDAT;
	}

	public void setUPLOADEDAT(Date uPLOADEDAT) {
		UPLOADEDAT = uPLOADEDAT;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getVSL_OFFICIAL_NO() {
		return VSL_OFFICIAL_NO;
	}

	public Object getVSL_IMO_NO() {
		return VSL_IMO_NO;
	}

	public void setVSL_OFFICIAL_NO(String vSL_OFFICIAL_NO) {
		VSL_OFFICIAL_NO = vSL_OFFICIAL_NO;
	}

	public void setVSL_IMO_NO(Object vSL_IMO_NO) {
		VSL_IMO_NO = vSL_IMO_NO;
	}

	public Object getISVIEWED() {
		return ISVIEWED;
	}

	public void setISVIEWED(Object iSVIEWED) {
		ISVIEWED = iSVIEWED;
	}

	@Override
	public String toString() {
		return "SharedFileResponse [F_ID=" + F_ID + ", FILE_NAME=" + FILE_NAME + ", ISFOLDER=" + ISFOLDER
				+ ", FILE_TYPE=" + FILE_TYPE + ", FILE_SIZE=" + FILE_SIZE + ", EMAIL=" + EMAIL + ", VESSEL_NAME="
				+ VESSEL_NAME + ", DESCRIPTION=" + DESCRIPTION + ", LID=" + LID + ", UPLOADEDBY=" + UPLOADEDBY
				+ ", UPLOADEDAT=" + UPLOADEDAT + ", VSL_OFFICIAL_NO=" + VSL_OFFICIAL_NO + ", VSL_IMO_NO=" + VSL_IMO_NO
				+ ", ISVIEWED=" + ISVIEWED + "]";
	}

}
