package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class FileId {

	private Integer fileId;
	private Integer idCode;
	private Integer linkId;

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getIdCode() {
		return idCode;
	}

	public void setIdCode(Integer idCode) {
		this.idCode = idCode;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	@Override
	public String toString() {
		return "FileId [fileId=" + fileId + ", IdCode=" + idCode + ", linkId=" + linkId + "]";
	}

}
