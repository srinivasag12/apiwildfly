package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.ArrayList;
import java.util.List;

public class ShareFile {

	private List<Integer> files = new ArrayList<Integer>();
	private List<String> emails = new ArrayList<String>();
	private Integer folderId;
	private String comments;
	private int days;
	private Integer userId;

	public List<Integer> getFiles() {
		return files;
	}

	public List<String> getEmails() {
		return emails;
	}

	public String getComments() {
		return comments;
	}

	public void setFiles(List<Integer> files) {
		this.files = files;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ShareFile [files=" + files + ", emails=" + emails + ", folderId=" + folderId + ", comments=" + comments
				+ ", days=" + days + ", userId=" + userId + "]";
	}

}
