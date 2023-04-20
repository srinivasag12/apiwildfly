package com.bsol.iri.fileSharing.models;

import java.util.ArrayList;
import java.util.List;

public class PrevouslyUploadedFiles {

	List<DashBoardSharedFile> uploadedFiles = new ArrayList<DashBoardSharedFile>();
	List<String> archieveFiles = new ArrayList<String>();

	public List<DashBoardSharedFile> getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFiles(List<DashBoardSharedFile> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	public List<String> getArchieveFiles() {
		return archieveFiles;
	}

	public void setArchieveFiles(List<String> archieveFiles) {
		this.archieveFiles = archieveFiles;
	}

	@Override
	public String toString() {
		return "PrevouslyUploadedFiles [uploadedFiles=" + uploadedFiles + ", archieveFiles=" + archieveFiles + "]";
	}

}
