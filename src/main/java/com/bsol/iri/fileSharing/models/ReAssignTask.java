package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.ArrayList;
import java.util.List;

public class ReAssignTask {

	private Integer loggedInUser;
	private Integer newUser;
	private List<Integer> linkId = new ArrayList<Integer>();

	public Integer getLoggedInUser() {
		return loggedInUser;
	}

	public Integer getNewUser() {
		return newUser;
	}

	public void setLoggedInUser(Integer loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public void setNewUser(Integer newUser) {
		this.newUser = newUser;
	}

	public List<Integer> getLinkId() {
		return linkId;
	}

	public void setLinkId(List<Integer> linkId) {
		this.linkId = linkId;
	}

	@Override
	public String toString() {
		return "ReAssignTask [loggedInUser=" + loggedInUser + ", newUser=" + newUser + ", linkId=" + linkId + "]";
	}

}
