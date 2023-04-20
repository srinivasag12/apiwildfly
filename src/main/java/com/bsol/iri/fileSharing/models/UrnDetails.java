package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class UrnDetails {

	private String urn;

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	@Override
	public String toString() {
		return "UrnDetails [urn=" + urn + "]";
	}

}
