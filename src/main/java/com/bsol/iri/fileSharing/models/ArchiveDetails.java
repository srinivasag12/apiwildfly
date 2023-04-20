package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */

import java.util.ArrayList;
import java.util.List;

public class ArchiveDetails {

	List<ArchivedLinkDetails> archiveLinkList = new ArrayList<ArchivedLinkDetails>();
	List<ArchivedFolderDetails> archiveFolderList = new ArrayList<ArchivedFolderDetails>();
	List<ArchivedFileDetails> archiveFile = new ArrayList<ArchivedFileDetails>();

	public ArchiveDetails(List<ArchivedLinkDetails> archiveLinkList, List<ArchivedFolderDetails> archiveFolderList,
			List<ArchivedFileDetails> arfile) {
		this.archiveLinkList = archiveLinkList;
		this.archiveFolderList = archiveFolderList;
		this.archiveFile = arfile;
	}

	public List<ArchivedLinkDetails> getArchiveLinkList() {
		return archiveLinkList;
	}

	public List<ArchivedFolderDetails> getArchiveFolderList() {
		return archiveFolderList;
	}

	public List<ArchivedFileDetails> getArfile() {
		return archiveFile;
	}

	public void setArchiveLinkList(List<ArchivedLinkDetails> archiveLinkList) {
		this.archiveLinkList = archiveLinkList;
	}

	public void setArchiveFolderList(List<ArchivedFolderDetails> archiveFolderList) {
		this.archiveFolderList = archiveFolderList;
	}

	public void setArfile(List<ArchivedFileDetails> arfile) {
		this.archiveFile = arfile;
	}

	@Override
	public String toString() {
		return "ArchiveDetails [archiveLinkList=" + archiveLinkList + ", archiveFolderList=" + archiveFolderList
				+ ", arfile=" + archiveFile + "]";
	}

}
