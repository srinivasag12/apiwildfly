package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class SftpPropDetails {

	private String remoteHost;
	private String username;
	private String password;
	private String knownHostsFileLoc;
	private String privateKey;

	public SftpPropDetails(String remoteHost, String username, String password, String knownHostsFileLoc,
			String privateKey) {
		super();
		this.remoteHost = remoteHost;
		this.username = username;
		this.password = password;
		this.knownHostsFileLoc = knownHostsFileLoc;
		this.privateKey = privateKey;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getKnownHostsFileLoc() {
		return knownHostsFileLoc;
	}

	public String getPrivateKey() {
		return privateKey;
	}

}
