package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.ArrayList;
import java.util.List;

public class ArchiveLink {

	private Integer userId;
	private List<Integer> linkId = new ArrayList<Integer>();

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Integer> getLinkId() {
		return linkId;
	}

	public void setLinkId(List<Integer> linkId) {
		this.linkId = linkId;
	}

	@Override
	public String toString() {
		return "ArchiveLink [userId=" + userId + ", linkId=" + linkId + "]";
	}

}
