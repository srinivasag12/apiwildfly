package com.bsol.iri.fileSharing.mappers;

/**
 * 
 * @author rupesh
 * This class is used to map a UploadedFile class (Entiry) to other Object.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bsol.iri.fileSharing.entity.UploadedFiles;
import com.bsol.iri.fileSharing.models.ArchivedFileDetails;
import com.bsol.iri.fileSharing.util.AppConstant;
import com.bsol.iri.fileSharing.util.DateTimeUtil;

public class UploadedToArchieveFile {

	Map<Integer, String> vslName = null;
	Map<Integer, String> vslDesc = null;
	Map<Integer, String> vslOfficialNo = null;
	Map<Integer, Integer> vslImo = null;
	Set<Integer> excludedLink = null;
	Set<Integer> excludedFolder = null;

	public UploadedToArchieveFile(Map<Integer, String> vslName, Map<Integer, String> vslDesc,
			Map<Integer, String> vslOfficialNo, Map<Integer, Integer> vslImo, Set<Integer> excludedFolder,
			Set<Integer> excludedLink) {
		this.vslDesc = vslDesc;
		this.vslName = vslName;
		this.excludedLink = excludedLink;
		this.excludedFolder = excludedFolder;
		this.vslImo = vslImo;
		this.vslOfficialNo = vslOfficialNo;
	}

	public List<ArchivedFileDetails> getArchivedFiles(List<UploadedFiles> uploadedFiles) {
		List<ArchivedFileDetails> arcFileList = new ArrayList<ArchivedFileDetails>();
		for (UploadedFiles uploadedFile : uploadedFiles) {

			if (!((excludedLink.contains(uploadedFile.getLinkId()))
					|| (excludedFolder.contains(uploadedFile.getFolderId()))))
				arcFileList.add(getArchivedFile(uploadedFile));
		}
		return arcFileList;
	}

	public ArchivedFileDetails getArchivedFile(UploadedFiles uploadedFile) {
		ArchivedFileDetails arcFile = new ArchivedFileDetails();

		arcFile.setUploadDownload(uploadedFile.getFolderId() != null ? 'D' : 'U');

		arcFile.setFileId(uploadedFile.getFileId());
		arcFile.setFileName(uploadedFile.getFileName());
		arcFile.setVesselName(this.vslName
				.get(uploadedFile.getFolderId() != null ? uploadedFile.getFolderId() : uploadedFile.getLinkId()));
		arcFile.setDesc(this.vslDesc
				.get(uploadedFile.getFolderId() != null ? uploadedFile.getFolderId() : uploadedFile.getLinkId()));
		arcFile.setExpiredOn(uploadedFile.getArchivedDate() != null
				? DateTimeUtil.addDays(uploadedFile.getArchivedDate(), AppConstant.ARCHIVE_DELETED_IN_DAYS)
				: null);
		arcFile.setImo(this.vslImo
				.get(uploadedFile.getFolderId() != null ? uploadedFile.getFolderId() : uploadedFile.getLinkId()));
		arcFile.setVesselOfficialNo(this.vslOfficialNo
				.get(uploadedFile.getFolderId() != null ? uploadedFile.getFolderId() : uploadedFile.getLinkId()));
		arcFile.setArchieveDate(uploadedFile.getArchivedDate());
		arcFile.setFileType(uploadedFile.getFileType());
		return arcFile;
	}
}
