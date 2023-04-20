package com.bsol.iri.fileSharing.entity;

/**
 * 
 * @author rupesh
 *
 */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "file_shared")
@IdClass(value = FileSharedIds.class)
@NamedStoredProcedureQuery(name = "SEND_EMAIL", 
procedureName = "send_email", parameters = {
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_to", type = String.class),
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_subject", type = String.class),
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_message", type = String.class),
})
public class FileShared {

	@Id
	@Column(name = "file_id")
	private Integer fileId;

	@Id
	@Column(name = "Shared_to")
	private Integer sharedTo;

	@Id
	@Column(name = "shared_by")
	private Integer sharedBy;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getSharedTo() {
		return sharedTo;
	}

	public void setSharedTo(Integer sharedTo) {
		this.sharedTo = sharedTo;
	}

	public Integer getSharedBy() {
		return sharedBy;
	}

	public void setSharedBy(Integer sharedBy) {
		this.sharedBy = sharedBy;
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

	@Override
	public String toString() {
		return "FileShared [fileId=" + fileId + ", sharedTo=" + sharedTo + ", sharedBy=" + sharedBy + ", createdBy="
				+ createdBy + ", updatedBy=" + updatedBy + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ "]";
	}

}
