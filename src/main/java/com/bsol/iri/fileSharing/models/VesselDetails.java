package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class VesselDetails {

	private Object VESSEL_NAME;
	private Object VSL_OFFICIAL_NO;
	private Object VSL_IMO_NO;
	private Object DESCRIPTION;
	private Object EXPIRED_ON;

	public Object getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(Object dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public Object getVESSEL_NAME() {
		return VESSEL_NAME;
	}

	public Object getVSL_OFFICIAL_NO() {
		return VSL_OFFICIAL_NO;
	}

	public Object getVSL_IMO_NO() {
		return VSL_IMO_NO;
	}

	public void setVESSEL_NAME(Object vESSEL_NAME) {
		VESSEL_NAME = vESSEL_NAME;
	}

	public void setVSL_OFFICIAL_NO(Object vSL_OFFICIAL_NO) {
		VSL_OFFICIAL_NO = vSL_OFFICIAL_NO;
	}

	public void setVSL_IMO_NO(Object vSL_IMO_NO) {
		VSL_IMO_NO = vSL_IMO_NO;
	}

	public Object getEXPIRED_ON() {
		return EXPIRED_ON;
	}

	public void setEXPIRED_ON(Object eXPIRED_ON) {
		EXPIRED_ON = eXPIRED_ON;
	}

	@Override
	public String toString() {
		return "VesselDetails [VESSEL_NAME=" + VESSEL_NAME + ", VSL_OFFICIAL_NO=" + VSL_OFFICIAL_NO + ", VSL_IMO_NO="
				+ VSL_IMO_NO + ", DESCRIPTION=" + DESCRIPTION + ", EXPIRED_ON=" + EXPIRED_ON + "]";
	}

}
