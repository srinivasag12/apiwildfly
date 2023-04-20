package com.bsol.iri.fileSharing.mappers;

/**
 * 
 * @author rupesh
 * This class will use to map conver a Entiry uploadedFiles to UploadedFiles (DTO)
 */
import java.util.ArrayList;
import java.util.List;

import com.bsol.iri.fileSharing.models.UploadedFiles;

public class UploadFilesMapper {
	public UploadFilesMapper() {
		// TODO Auto-generated constructor stub
	}

	public List<UploadedFiles> mapUploadedFiles(List<com.bsol.iri.fileSharing.entity.UploadedFiles> upFileList) {

		List<UploadedFiles> files = new ArrayList<UploadedFiles>();
		for (com.bsol.iri.fileSharing.entity.UploadedFiles uploadedFiles : upFileList) {
			files.add(mapUploadFile(uploadedFiles));
		}
		return files;
	}

	public UploadedFiles mapUploadFile(com.bsol.iri.fileSharing.entity.UploadedFiles upFile) {
		UploadedFiles uf = new UploadedFiles();
		uf.setFileId(upFile.getFileId());
		uf.setName(upFile.getFileName());
		uf.setType(upFile.getFileType());
		uf.setFileSize(upFile.getFileSize());
		uf.setIsViewed(upFile.getIsViewed());
		return uf;
	}

}
