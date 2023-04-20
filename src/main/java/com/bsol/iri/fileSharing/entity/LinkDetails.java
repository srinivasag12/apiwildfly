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
@Table(name = "link_details")
public class LinkDetails {

	@Id
	@TableGenerator(name = "idGenerator", table = "file_upload_seq_store", pkColumnName = "id_Seq", pkColumnValue = "pk_key_id", valueColumnName = "Seq_Value", initialValue = 999, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGenerator")
	@Column(name = "link_id")
	private Integer linkId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "email")
	private String email;

	@Column(name = "vessel_name")
	private String vesselName;

	@Column(name = "link_desc")
	private Integer linkDesc;

	@Column(name = "link")
	private String link;

	@Column(name = "expiry_date")
	private Date expiryDate;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "isExtended")
	private int isExtended;

	@Column(name = "link_status")
	private int linkStatus;

	@Column(name = "cancel_request")
	private int cancelRequest;

	@Column(name = "logged_in_status")
	private int loggedInStatus;

	@Column(name = "link_type")
	private int linkType;

	@Column(name = "EXTENDED_DAYS")
	private int extended_days;

	@Column(name = "ISARCHIVE")
	private Integer isArchive;

	@Column(name = "ARCHIVED_ON")
	private Date archivedOn;

	@Column(name = "VSL_IMO_NO")
	private Integer imo;

	@Column(name = "VSL_OFFICIAL_NO")
	private String officialNo;;

	@Column(name = "ISVIEWED")
	private Integer isViewed;

	@Column(name = "VIEWED_AT")
	private Date viewdAt;

	@Column(name = "COMPLETED_ON")
	private Date completedOn;

	@Column(name = "REASSIGNED_ON")
	private Date reassignedOn;

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public Integer getLinkDesc() {
		return linkDesc;
	}

	public void setLinkDesc(Integer linkDesc) {
		this.linkDesc = linkDesc;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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

	public int getIsExtended() {
		return isExtended;
	}

	public void setIsExtended(int isExtended) {
		this.isExtended = isExtended;
	}

	public int getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(int linkStatus) {
		this.linkStatus = linkStatus;
	}

	public int getLoggedInStatus() {
		return loggedInStatus;
	}

	public void setLoggedInStatus(int loggedInStatus) {
		this.loggedInStatus = loggedInStatus;
	}

	public Integer getIsViewed() {
		return isViewed;
	}

	public void setIsViewed(Integer isViewed) {
		this.isViewed = isViewed;
	}

	public int getLinkType() {
		return linkType;
	}

	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}

	public int getCancelRequest() {
		return cancelRequest;
	}

	public void setCancelRequest(int cancelRequest) {
		this.cancelRequest = cancelRequest;
	}

	public int getExtended_days() {
		return extended_days;
	}

	public void setExtended_days(int extended_days) {
		this.extended_days = extended_days;
	}

	public Integer getIsArchive() {
		return isArchive;
	}

	public Date getArchivedOn() {
		return archivedOn;
	}

	public void setIsArchive(Integer isArchive) {
		this.isArchive = isArchive;
	}

	public void setArchivedOn(Date archivedOn) {
		this.archivedOn = archivedOn;
	}

	public Integer getImo() {
		return imo;
	}

	public String getOfficialNo() {
		return officialNo;
	}

	public void setImo(Integer imo) {
		this.imo = imo;
	}

	public void setOfficialNo(String officialNo) {
		this.officialNo = officialNo;
	}

	public Date getViewdAt() {
		return viewdAt;
	}

	public void setViewdAt(Date viewdAt) {
		this.viewdAt = viewdAt;
	}

	public Date getCompletedOn() {
		return completedOn;
	}

	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}

	public Date getReassignedOn() {
		return reassignedOn;
	}

	public void setReassignedOn(Date reassignedOn) {
		this.reassignedOn = reassignedOn;
	}

	@Override
	public String toString() {
		return "LinkDetails [linkId=" + linkId + ", userId=" + userId + ", email=" + email + ", vesselName="
				+ vesselName + ", linkDesc=" + linkDesc + ", link=" + link + ", expiryDate=" + expiryDate
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + ", isExtended=" + isExtended + ", linkStatus=" + linkStatus + ", cancelRequest="
				+ cancelRequest + ", loggedInStatus=" + loggedInStatus + ", linkType=" + linkType + ", extended_days="
				+ extended_days + ", isArchive=" + isArchive + ", archivedOn=" + archivedOn + ", imo=" + imo
				+ ", officialNo=" + officialNo + ", isViewed=" + isViewed + ", viewdAt=" + viewdAt + ", completedOn="
				+ completedOn + ", reassignedOn=" + reassignedOn + "]";
	}

}
