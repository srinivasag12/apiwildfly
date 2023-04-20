package com.bsol.iri.fileSharing.entity;

/**
 * 
 * @author rupesh
 *
 */
import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class FileDownloadLinkId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer fileId;
	private Integer downloadLinkId;

	public Integer getFileId() {
		return fileId;
	}

	public Integer getDownloadLinkId() {
		return downloadLinkId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public void setDownloadLinkId(Integer downloadLinkId) {
		this.downloadLinkId = downloadLinkId;
	}

	public FileDownloadLinkId() {
	}

	public FileDownloadLinkId(Integer fileId, Integer downloadLinkId) {
		super();
		this.fileId = fileId;
		this.downloadLinkId = downloadLinkId;
	}

	@Override
	public String toString() {
		return "FileDownloadLinkId [fileId=" + fileId + ", downloadLinkId=" + downloadLinkId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 71;
		int result = 1;
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		result = prime * result + ((downloadLinkId == null) ? 0 : downloadLinkId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		FileDownloadLinkId other = (FileDownloadLinkId) obj;

		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;

		if (downloadLinkId == null) {
			if (other.downloadLinkId != null)
				return false;
		} else if (!downloadLinkId.equals(downloadLinkId))
			return false;

		return true;
	}

}
