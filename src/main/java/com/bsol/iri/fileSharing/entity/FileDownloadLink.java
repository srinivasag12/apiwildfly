package com.bsol.iri.fileSharing.entity;

/**
 * 
 * @author rupesh
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "file_download_link")
@IdClass(value = FileDownloadLinkId.class)
public class FileDownloadLink implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FILE_ID")
	private Integer fileId;

	@Id
	@Column(name = "DOWNLOAD_LINK_ID")
	private Integer downloadLinkId;

	@Column(name = "ISVIEWED")
	private Integer isViewed;

	@Column(name = "VIEWEDON")
	private Date viewedOn;

	public Integer getFileId() {
		return fileId;
	}

	public Integer getDownloadLinkId() {
		return downloadLinkId;
	}

	public void setDownloadLinkId(Integer downloadLinkId) {
		this.downloadLinkId = downloadLinkId;
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

	public Date getViewedOn() {
		return viewedOn;
	}

	public void setViewedOn(Date viewedOn) {
		this.viewedOn = viewedOn;
	}

	@Override
	public String toString() {
		return "FileDownloadLink [fileId=" + fileId + ", DownloadLinkId=" + downloadLinkId + "]";
	}

}
