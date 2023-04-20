package com.bsol.iri.fileSharing.models;

/**
 * @author rupesh
 * Model class
 */
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RecentReassigned {

	private String REASSIGNEDTO;
	private String VESSELNAME;
	private String DESCRIPTION;
	private Date REASSIGNEDON;
	private String VSLOFFICIALNO;
	private Object VSLIMO;
	private String SUBMITTEDBY;
	private Date COMPLETEDON;
	private Object RN;

	public String getREASSIGNEDTO() {
		return REASSIGNEDTO;
	}

	public void setREASSIGNEDTO(String rEASSIGNEDTO) {
		REASSIGNEDTO = rEASSIGNEDTO;
	}

	public String getVESSELNAME() {
		return VESSELNAME;
	}

	public void setVESSELNAME(String vESSELNAME) {
		VESSELNAME = vESSELNAME;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public Date getREASSIGNEDON() {
		return REASSIGNEDON;
	}

	public void setREASSIGNEDON(Date rEASSIGNEDON) {
		REASSIGNEDON = rEASSIGNEDON;
	}

	public String getVSLOFFICIALNO() {
		return VSLOFFICIALNO;
	}

	public void setVSLOFFICIALNO(String vSLOFFICIALNO) {
		VSLOFFICIALNO = vSLOFFICIALNO;
	}

	public Object getVSLIMO() {
		return VSLIMO;
	}

	public void setVSLIMO(Object vSLIMO) {
		VSLIMO = vSLIMO;
	}

	public String getSUBMITTEDBY() {
		return SUBMITTEDBY;
	}

	public void setSUBMITTEDBY(String sUBMITTEDBY) {
		SUBMITTEDBY = sUBMITTEDBY;
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

	@Override
	public String toString() {
		return "RecentReassigned [REASSIGNEDTO=" + REASSIGNEDTO + ", VESSELNAME=" + VESSELNAME + ", DESCRIPTION="
				+ DESCRIPTION + ", REASSIGNEDON=" + REASSIGNEDON + ", VSLOFFICIALNO=" + VSLOFFICIALNO + ", VSLIMO="
				+ VSLIMO + ", SUBMITTEDBY=" + SUBMITTEDBY + ", COMPLETEDON=" + COMPLETEDON + "]";
	}

}
