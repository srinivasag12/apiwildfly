package com.bsol.iri.fileSharing.entity;

/**
 * 
 * @author rupesh
 *
 */
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "folder_details")
public class FolderDetails {

	@Id
	@TableGenerator(name = "idGenerator", table = "file_upload_seq_store", pkColumnName = "id_Seq", pkColumnValue = "pk_key_id", valueColumnName = "Seq_Value", initialValue = 999, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGenerator")
	@Column(name = "FOLDER_ID")
	private Integer folderId;

	@Column(name = "USER_ID")
	private Integer userId;

	@Column(name = "VESSEL_NAME")
	private String vesselName;

	@Column(name = "VESSEL_DESC")
	private Integer vesselDesc;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "ARCHIVED_ON")
	private Date archivedOn;

	@Column(name = "isArchive")
	private Integer isArchived;
	
	@Column(name = "VSL_IMO_NO")
	private Integer imo;
	
	@Column(name = "VSL_OFFICIAL_NO")
	private String vslOfficialNo;

	public Integer getFolderId() {
		return folderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getVesselName() {
		return vesselName;
	}

	public Integer getVesselDesc() {
		return vesselDesc;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public void setVesselDesc(Integer vesselDesc) {
		this.vesselDesc = vesselDesc;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getArchivedOn() {
		return archivedOn;
	}

	public Integer getIsArchived() {
		return isArchived;
	}

	public void setArchivedOn(Date archivedOn) {
		this.archivedOn = archivedOn;
	}

	public void setIsArchived(Integer isArchived) {
		this.isArchived = isArchived;
	}

	
	public Integer getImo() {
		return imo;
	}

	public String getVslOfficialNo() {
		return vslOfficialNo;
	}

	public void setImo(Integer imo) {
		this.imo = imo;
	}

	public void setVslOfficialNo(String vslOfficialNo) {
		this.vslOfficialNo = vslOfficialNo;
	}

	@Override
	public String toString() {
		return "FolderDetails [folderId=" + folderId + ", userId=" + userId + ", vesselName=" + vesselName
				+ ", vesselDesc=" + vesselDesc + ", createdOn=" + createdOn + ", archivedOn=" + archivedOn
				+ ", isArchived=" + isArchived + ", imo=" + imo + ", vslOfficialNo=" + vslOfficialNo + "]";
	}

}
