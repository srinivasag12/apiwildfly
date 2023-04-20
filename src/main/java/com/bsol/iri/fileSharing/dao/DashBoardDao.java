package com.bsol.iri.fileSharing.dao;

import java.util.List;

import com.bsol.iri.fileSharing.models.DashboardArchiveFilesToDelete;
import com.bsol.iri.fileSharing.models.UnviewdFolders;

public interface DashBoardDao {
	List<UnviewdFolders> getUnviewdUploadedFiles(Integer userId, Integer pageStart, Integer pagelast, String startDate);

	List<UnviewdFolders> getUnviewdDownloadedFiles(Integer userId, Integer pageStart, Integer pagelast,
			String startDate);

	List<UnviewdFolders> getAllUnviewdUploadedFiles(Integer pageStart, Integer pagelast, String startDate);

	List<UnviewdFolders> getAllUnviewdDownloadedFiles(Integer pageStart, Integer pagelast, String startDate);

}
