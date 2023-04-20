package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class RemoveSpecificFile {

	private Integer downloadLinkId;
	private Integer fileId;

	public Integer getDownloadLinkId() {
		return downloadLinkId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setDownloadLinkId(Integer downloadLinkId) {
		this.downloadLinkId = downloadLinkId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "RemoveSpecificFile [downloadLinkId=" + downloadLinkId + ", fileId=" + fileId + "]";
	}

}
