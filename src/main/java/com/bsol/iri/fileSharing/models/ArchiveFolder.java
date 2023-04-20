package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class ArchiveFolder {

	private Integer userId;
	private Integer folderId;

	public Integer getUserId() {
		return userId;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	@Override
	public String toString() {
		return "ArchiveFolder [userId=" + userId + ", folderId=" + folderId + "]";
	}

}
