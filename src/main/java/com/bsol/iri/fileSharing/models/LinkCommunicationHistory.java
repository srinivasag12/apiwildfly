package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.math.BigDecimal;
import java.math.BigInteger;

public class LinkCommunicationHistory {

	private String VESSEL_NAME;
	private Integer VSLDESC;
	private Integer CTIMES;
	private Integer IMO;
	private String OFFICIALNO;

	public Integer getVSLDESC() {
		return VSLDESC;
	}

	public void setVSLDESC(BigDecimal vSLDESC) {
		VSLDESC = vSLDESC.intValue();
	}

	public Integer getCTIMES() {
		return CTIMES;
	}

	public void setCTIMES(BigDecimal cTIMES) {
		CTIMES = cTIMES.intValue();
	}

	public String getVESSEL_NAME() {
		return VESSEL_NAME;
	}

	public void setVESSEL_NAME(String vESSEL_NAME) {
		VESSEL_NAME = vESSEL_NAME;
	}

	public Integer getIMO() {
		return IMO;
	}

	public void setIMO(BigDecimal iMO) {
		IMO = iMO.intValue();
	}

	public String getOFFICIALNO() {
		return OFFICIALNO;
	}

	public void setOFFICIALNO(String oFFICIALNO) {
		OFFICIALNO = oFFICIALNO;
	}

}
