package com.bsol.iri.fileSharing.models.thirdParty;

/**
 * 
 * @author rupesh
 */


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class Login {

	private String username;
	private String password;
	private String url;
	private Integer linkType;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	@Override
	public String toString() {
		return "Login [username=" + username + ", password=" + password + ", url=" + url + ", linkType=" + linkType
				+ "]";
	}

	
}
