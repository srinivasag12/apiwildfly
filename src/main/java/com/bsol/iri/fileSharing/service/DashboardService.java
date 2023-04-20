package com.bsol.iri.fileSharing.service;

import java.util.List;

import com.bsol.iri.fileSharing.models.DashboardArchiveFilesToDelete;
import com.bsol.iri.fileSharing.models.GroupingDataForChart;
import com.bsol.iri.fileSharing.models.RecentReassigned;
import com.bsol.iri.fileSharing.models.UnviewdFolders;

public interface DashboardService {

	List<UnviewdFolders> getUnviewdFiles(Integer userId, Integer days, Integer page, Integer pageSize, Integer toggle);

	List<UnviewdFolders> getAllUnviewdFiles(Integer days, Integer page, Integer pageSize, Integer toggle);

	List<DashboardArchiveFilesToDelete> userArchiveFileWillBeDeleted(Integer userId, Integer days, Integer page,
			Integer pageSize, Integer toggle);

	List<RecentReassigned> getAllRecentReassignedLinks(Integer page, Integer pageSize);

	List<GroupingDataForChart> getVesselsBasedOnInspectioTypeFormanger(Integer toggle);

	List<GroupingDataForChart> getVesselsBasedOnInspectioTypeForUser(Integer UserId, Integer toggle);

	Object getFileContByVslNameManager(Integer toggle);

	List<GroupingDataForChart> getFileContByVslNameUser(Integer UserId,Integer toggle);

}
