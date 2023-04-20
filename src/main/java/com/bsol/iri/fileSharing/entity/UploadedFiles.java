package com.bsol.iri.fileSharing.entity;

/**
 * 
 * @author rupesh
 *
 */
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "uploaded_files")
public class UploadedFiles {

	@Id
	@TableGenerator(name = "idGenerator", table = "file_upload_seq_store", pkColumnName = "id_Seq", pkColumnValue = "pk_key_id", valueColumnName = "Seq_Value", initialValue = 999, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGenerator")
	@Column(name = "f_id")
	private Integer fileId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "parent_id")
	private Integer parentId;

	@Column(name = "isFolder")
	private int isFolder;

	@Column(name = "isArchive")
	private int isArchive;

	@Column(name = "file_size")
	private BigInteger fileSize;

	@Column(name = "file_type")
	private String fileType;

	@Column(name = "isDeleted")
	private int isDeleted;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "link_id")
	private Integer linkId;

	@Column(name = "isShared")
	private int isShared;

	@Column(name = "FOLDER_ID")
	private Integer folderId;

	@Column(name = "ARCHIVED_DATE")
	private Date archivedDate;

	@Column(name = "ISVIEWED")
	private Integer isViewed;

	@Column(name = "VIEWED_AT")
	private Date viewdAt;

	public Integer getIsViewed() {
		return isViewed;
	}

	public void setIsViewed(Integer isViewed) {
		this.isViewed = isViewed;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public int getIsFolder() {
		return isFolder;
	}

	public void setIsFolder(int isFolder) {
		this.isFolder = isFolder;
	}

	public int getIsArchive() {
		return isArchive;
	}

	public void setIsArchive(int isArchive) {
		this.isArchive = isArchive;
	}

	public BigInteger getFileSize() {
		return fileSize;
	}

	public void setFileSize(BigInteger fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public int getIsShared() {
		return isShared;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	public void setIsShared(int isShared) {
		this.isShared = isShared;
	}

	public Date getArchivedDate() {
		return archivedDate;
	}

	public void setArchivedDate(Date archivedDate) {
		this.archivedDate = archivedDate;
	}

	public Date getViewdAt() {
		return viewdAt;
	}

	public void setViewdAt(Date viewdAt) {
		this.viewdAt = viewdAt;
	}

	@Override
	public String toString() {
		return "UploadedFiles [fileId=" + fileId + ", userId=" + userId + ", fileName=" + fileName + ", parentId="
				+ parentId + ", isFolder=" + isFolder + ", isArchive=" + isArchive + ", fileSize=" + fileSize
				+ ", fileType=" + fileType + ", isDeleted=" + isDeleted + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", linkId=" + linkId
				+ ", isShared=" + isShared + ", folderId=" + folderId + ", archivedDate=" + archivedDate + ", isViewed="
				+ isViewed + ", viewdAt=" + viewdAt + "]";
	}

}
