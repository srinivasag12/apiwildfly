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
@Table(name = "ma_description")
public class MaDescription {

	@Id
	@TableGenerator(name = "idGenerator", table = "file_upload_seq_store", pkColumnName = "id_Seq", pkColumnValue = "pk_key_id", valueColumnName = "Seq_Value", initialValue = 999, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGenerator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "DESCRIPTION")
	private String desc;
	
	@Column(name = "isActive")
	private Integer isActive;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "Created_by")
	private String createdBy;

	@Column(name = "Updated_by")
	private String updatedBy;

	public Integer getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	
	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "MaDescription [id=" + id + ", desc=" + desc + ", isActive=" + isActive + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
	}

}
