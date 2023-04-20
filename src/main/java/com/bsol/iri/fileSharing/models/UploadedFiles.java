package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.math.BigInteger;

public class UploadedFiles {

	private String type;
	private BigInteger fileSize;
	private String name;
	private Integer fileId;
	private Integer isViewed;

	public String getType() {
		return type;
	}

	public BigInteger getFileSize() {
		return fileSize;
	}

	public String getName() {
		return name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setFileSize(BigInteger fileSize) {
		this.fileSize = fileSize;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getIsViewed() {
		return isViewed;
	}

	public void setIsViewed(Integer isViewed) {
		this.isViewed = isViewed;
	}

	@Override
	public String toString() {
		return "UploadedFiles [type=" + type + ", fileSize=" + fileSize + ", name=" + name + ", fileId=" + fileId
				+ ", isViewed=" + isViewed + "]";
	}


}
