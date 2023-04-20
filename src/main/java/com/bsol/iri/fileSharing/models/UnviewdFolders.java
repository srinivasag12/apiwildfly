package com.bsol.iri.fileSharing.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UnviewdFolders {

	private String VESSELNAME;

	private String EMAIL;
	private String DESCRIPTION;
	private String VSLOFFICIALNO;
	private Object IMO;
	private Date COMPLETEDON;
	private Object RN;
	private String OWNER;

	public String getVESSELNAME() {
		return VESSELNAME;
	}

	public void setVESSELNAME(String vESSELNAME) {
		VESSELNAME = vESSELNAME;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getVSLOFFICIALNO() {
		return VSLOFFICIALNO;
	}

	public void setVSLOFFICIALNO(String vSLOFFICIALNO) {
		VSLOFFICIALNO = vSLOFFICIALNO;
	}

	public Object getIMO() {
		return IMO;
	}

	public void setIMO(Object iMO) {
		IMO = iMO;
	}

	public Date getCOMPLETEDON() {
		return COMPLETEDON;
	}

	public void setCOMPLETEDON(Date cOMPLETEDON) {
		COMPLETEDON = cOMPLETEDON;
	}

	@JsonIgnore
	public Object getRN() {
		return RN;
	}

	public void setRN(Object rN) {
		RN = rN;
	}

	public String getOWNER() {
		return OWNER;
	}

	public void setOWNER(String oWNER) {
		OWNER = oWNER;
	}

	@Override
	public String toString() {
		return "UnviewdFolders [VESSELNAME=" + VESSELNAME + ", EMAIL=" + EMAIL + ", DESCRIPTION=" + DESCRIPTION
				+ ", VSLOFFICIALNO=" + VSLOFFICIALNO + ", IMO=" + IMO + ", COMPLETEDON=" + COMPLETEDON + ", RN=" + RN
				+ "]";
	}

}
