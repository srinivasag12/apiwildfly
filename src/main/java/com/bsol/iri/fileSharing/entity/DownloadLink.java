package com.bsol.iri.fileSharing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author rupesh
 *
 */
@Entity
@Table(name = "Download_link")
public class DownloadLink {

	@Id
	@Column(name = "LINK_ID")
	@TableGenerator(name = "idGenerator", table = "file_upload_seq_store", pkColumnName = "id_Seq", pkColumnValue = "pk_key_id", valueColumnName = "Seq_Value", initialValue = 999, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGenerator")
	private Integer linkId;

	@Column(name = "URL")
	private String URL;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "CREATED_BY")
	private Integer createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "LINK_STATUS")
	private Integer linkStatus;

	@Column(name = "EXPIRED_ON")
	private Date expired_on;

	@Column(name = "FOLDER_ID")
	private Integer folderId;

	@Column(name = "EXTENDED_DAYS")
	private int extDays;

	@Column(name = "LOGGED_IN_STATUS")
	private Integer loggedInStatus;

	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	public Integer getLinkId() {
		return linkId;
	}

	public String getURL() {
		return URL;
	}

	public String getEmail() {
		return email;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedOn(Date created_on) {
		this.createdOn = created_on;
	}

	public Date getExpired_on() {
		return expired_on;
	}

	public void setExpired_on(Date expired_on) {
		this.expired_on = expired_on;
	}

	public int getExtDays() {
		return extDays;
	}

	public void setExtDays(int extDays) {
		this.extDays = extDays;
	}

	public Integer getLinkStatus() {
		return linkStatus;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setLinkStatus(Integer linkStatus) {
		this.linkStatus = linkStatus;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	public Integer getLoggedInStatus() {
		return loggedInStatus;
	}

	public void setLoggedInStatus(Integer loggedInStatus) {
		this.loggedInStatus = loggedInStatus;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "DownloadLink [linkId=" + linkId + ", URL=" + URL + ", email=" + email + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + ", linkStatus=" + linkStatus + ", expired_on=" + expired_on
				+ ", folderId=" + folderId + ", extDays=" + extDays + ", loggedInStatus=" + loggedInStatus
				+ ", updatedOn=" + updatedOn + "]";
	}

}
