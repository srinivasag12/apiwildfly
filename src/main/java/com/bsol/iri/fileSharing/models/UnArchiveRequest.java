package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class UnArchiveRequest {

	private Integer userId;
	private Integer fileId;

	public Integer getUserId() {
		return userId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "UnArchiveRequest [userId=" + userId + ", fileId=" + fileId + "]";
	}

}
