package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class LocationDetails {

	private String baseDirectory;
	private String personalDirectory;
	private String SharedDirectory;

	public LocationDetails() {
		// TODO Auto-generated constructor stub
	}
	
	

	public LocationDetails(String baseDirectory, String personalDirectory, String sharedDirectory) {
		this.baseDirectory = baseDirectory;
		this.personalDirectory = personalDirectory;
		this.SharedDirectory = sharedDirectory;
	}



	public String getBaseDirectory() {
		return baseDirectory;
	}

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public String getPersonalDirectory() {
		return personalDirectory;
	}

	public void setPersonalDirectory(String personalDirectory) {
		this.personalDirectory = personalDirectory;
	}

	public String getSharedDirectory() {
		return this.SharedDirectory;
	}

	public void setSharedDirectory(String sharedDirectory) {
		this.SharedDirectory = sharedDirectory;
	}

	@Override
	public String toString() {
		return "LocationDetails [baseDirectory=" + baseDirectory + ", personalDirectory=" + personalDirectory
				+ ", SharedDirectory=" + SharedDirectory + "]";
	}

}
