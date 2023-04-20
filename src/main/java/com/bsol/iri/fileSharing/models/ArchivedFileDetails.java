package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */
import java.util.Date;

public class ArchivedFileDetails {

	private Integer fileId;
	private String fileName;
	private String vesselName;
	private String desc;
	private Date expiredOn;
	private Date archieveDate;
	private Integer imo;
	private String vesselOfficialNo;
	private String fileType;
	private char uploadDownload;

	public Date getArchieveDate() {
		return archieveDate;
	}

	public void setArchieveDate(Date archieveDate) {
		this.archieveDate = archieveDate;
	}

	public Integer getFileId() {
		return fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public String getVesselName() {
		return vesselName;
	}

	public String getDesc() {
		return desc;
	}

	public Date getExpiredOn() {
		return expiredOn;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setExpiredOn(Date expiredOn) {
		this.expiredOn = expiredOn;
	}

	public Integer getImo() {
		return imo;
	}

	public String getVesselOfficialNo() {
		return vesselOfficialNo;
	}

	public void setImo(Integer imo) {
		this.imo = imo;
	}

	public void setVesselOfficialNo(String vesselOfficialNo) {
		this.vesselOfficialNo = vesselOfficialNo;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public char getUploadDownload() {
		return uploadDownload;
	}

	public void setUploadDownload(char uploadDownload) {
		this.uploadDownload = uploadDownload;
	}

	@Override
	public String toString() {
		return "ArchivedFileDetails [fileId=" + fileId + ", fileName=" + fileName + ", vesselName=" + vesselName
				+ ", desc=" + desc + ", expiredOn=" + expiredOn + ", archieveDate=" + archieveDate + ", imo=" + imo
				+ ", vesselOfficialNo=" + vesselOfficialNo + ", fileType=" + fileType + ", uploadDownload="
				+ uploadDownload + "]";
	}

}
