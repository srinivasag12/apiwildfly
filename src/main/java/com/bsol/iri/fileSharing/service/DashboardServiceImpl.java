package com.bsol.iri.fileSharing.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsol.iri.fileSharing.dao.DashBoardDao;
import com.bsol.iri.fileSharing.entity.MaDescription;
import com.bsol.iri.fileSharing.models.DashboardArchiveFilesToDelete;
import com.bsol.iri.fileSharing.models.GroupingDataForChart;
import com.bsol.iri.fileSharing.models.RecentReassigned;
import com.bsol.iri.fileSharing.models.UnviewdFolders;
import com.bsol.iri.fileSharing.repos.FolderDetailsRepo;
import com.bsol.iri.fileSharing.repos.LinkDetailsRepo;
import com.bsol.iri.fileSharing.repos.MaDescriptionRepo;
import com.bsol.iri.fileSharing.util.AppConstant;
import com.bsol.iri.fileSharing.util.DateTimeUtil;
import com.bsol.iri.fileSharing.util.SqlQueries;

@Service
@SuppressWarnings({ "deprecation", "unchecked" })
public class DashboardServiceImpl implements DashboardService {

	private static final Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);

	@Autowired
	private LinkDetailsRepo linkDetailsRepo;

	@Autowired
	private FolderDetailsRepo folderDetailsRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private MaDescriptionRepo maDescription;

	@Autowired
	private DashBoardDao dashBoardDao;

	@Override
	public List<UnviewdFolders> getUnviewdFiles(Integer userId, Integer days, Integer page, Integer pageSize,
			Integer toggle) {
		log.debug("Inside getAllUnviewdFiles with values days {} page# {} pageSize {} toggle value {}", days, page,
				pageSize, toggle);
		int start = page > 0 ? ((page * pageSize) - 1) : 1;
		int last = start + pageSize;
		String startDate = new SimpleDateFormat("dd-MM-YY").format(DateTimeUtil.subDaysToCurrectDate(days));
		log.info("Getting all inviewd files for userid {}, start page {}, last page {}, start date {}", userId, start,
				last, startDate);
		if (toggle == 1) {
			return dashBoardDao.getUnviewdUploadedFiles(userId, start, last, startDate);
		} else if (toggle == 0) {
			return dashBoardDao.getUnviewdDownloadedFiles(userId, start, last, startDate);
		} else {
			return null;
		}

	}

	@Override
	public List<UnviewdFolders> getAllUnviewdFiles(Integer days, Integer page, Integer pageSize, Integer toggle) {
		log.debug("Inside getAllUnviewdFiles with values days {} page# {} pageSize {} toggle value {}", days, page,
				pageSize, toggle);
		int start = page > 0 ? ((page * pageSize) - 1) : 1;
		int last = start + pageSize;
		String startDate = new SimpleDateFormat("dd-MM-YY").format(DateTimeUtil.subDaysToCurrectDate(days));
		log.info("Getting all inviewd uploaded files for start page {}, last page {}, start date {}", start, last,
				startDate);
		if (toggle == 1) {
			return dashBoardDao.getAllUnviewdUploadedFiles(start, last, startDate);
		} else if (toggle == 0) {
			return dashBoardDao.getAllUnviewdDownloadedFiles(start, last, startDate);
		} else {
			return null;
		}
	}

	@Override
	public List<DashboardArchiveFilesToDelete> userArchiveFileWillBeDeleted(Integer userId, Integer days, Integer page,
			Integer pageSize, Integer toggle) {
		log.debug("Inside userArchiveFileWillBeDeleted with values userId {} days {} page# {} pageSize {}", userId,
				days, page, pageSize);
		int start = page > 0 ? ((page * pageSize) - 1) : 1;
		int last = start + pageSize;
		String sqlQuery = null;
		if (toggle == 1)
			sqlQuery = SqlQueries.ARCHIVE_UPLOADED_FILE_GOING_TO_DELETE;
		else if (toggle == 0)
			sqlQuery = SqlQueries.ARCHIVE_DOWNLOADED_FILE_GOING_TO_DELETE;
		else
			return null;
		Query archiveFileToDeleteQuery = entityManager.createNativeQuery(sqlQuery).unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(DashboardArchiveFilesToDelete.class));
		log.info("Fetching archieve file details from row number {}  to {} fro userid {}", start, last, userId);
		archiveFileToDeleteQuery.setParameter("user_id", userId);
		archiveFileToDeleteQuery.setParameter("isarchive", AppConstant.TRUE);
		archiveFileToDeleteQuery.setParameter("isdeleted", AppConstant.FALSE);
		archiveFileToDeleteQuery.setParameter("dateFrom",
				new SimpleDateFormat("dd-MM-YY").format(DateTimeUtil.getTodaysDate()));
		archiveFileToDeleteQuery.setParameter("dateUpto",
				new SimpleDateFormat("dd-MM-YY").format(DateTimeUtil.addDaysToCurrectDate(days)));
		archiveFileToDeleteQuery.setParameter("pageStart", start);
		archiveFileToDeleteQuery.setParameter("pageEnd", last);

		return (List<DashboardArchiveFilesToDelete>) archiveFileToDeleteQuery.getResultList();

	}

	@Override
	public List<RecentReassigned> getAllRecentReassignedLinks(Integer page, Integer pageSize) {
		log.debug("Inside getAllRecentReassignedLinks with values page# {} pageSize {}", page, pageSize);
		int start = page > 0 ? ((page * pageSize) - 1) : 1;
		int last = start + pageSize;
		Query receentReassignedQuery = entityManager.createNativeQuery(SqlQueries.RECENT_RE_ASSIGNED)
				.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(RecentReassigned.class));
		log.info("Fetching all recent reassigned link details from row number {}  to {}", start, last);
		receentReassignedQuery.setParameter("pageStart", start);
		receentReassignedQuery.setParameter("pageEnd", last);

		List<RecentReassigned> recentReassigned = (List<RecentReassigned>) receentReassignedQuery.getResultList();

		return recentReassigned;
	}

	/**
	 * Toggle 1 for upload link toggle 0 for download link
	 */
	@Override
	public List<GroupingDataForChart> getVesselsBasedOnInspectioTypeFormanger(Integer toggle) {
		String vslCountSqlQury = null;
		List<GroupingDataForChart> groupDataList = new ArrayList<GroupingDataForChart>();
		List<MaDescription> discList = maDescription.findAll();

		// Toggle 1 for upload and toggle 0 for download

		if (toggle == 1) {
			vslCountSqlQury = SqlQueries.UNIQUE_UPLOAD_VSL_COUNT;
			log.info("Getting Unique upload vessel count based on inspection types for admin/manager");
		} else if (toggle == 0) {
			vslCountSqlQury = SqlQueries.UNIQUE_DOWNLOAD_VSL_COUNT;
			log.info("Getting Unique download vessel count based on inspection types for admin/manager");
		} else
			return groupDataList;
		for (MaDescription maDesc : discList) {
			GroupingDataForChart gdata = new GroupingDataForChart();
			gdata.setKey(maDesc.getDesc());
			Query vslCountQuery = entityManager.createNativeQuery(vslCountSqlQury).unwrap(NativeQuery.class);
			vslCountQuery.setParameter("descId", maDesc.getId());
			gdata.setValue(vslCountQuery.getSingleResult());
			groupDataList.add(gdata);
		}
		return groupDataList;

	}

	@Override
	public List<GroupingDataForChart> getVesselsBasedOnInspectioTypeForUser(Integer userId, Integer toggle) {
		List<GroupingDataForChart> groupDataList = new ArrayList<GroupingDataForChart>();
		String vslCountSqlQury = null;
		List<MaDescription> discList = maDescription.findAll();

		// Toggle 1 for upload and toggle 0 for download

		if (toggle == 1) {
			vslCountSqlQury = SqlQueries.UNIQUE_VSL_COUNT_BY_USER_UPLOAD;
			log.info("Getting Unique upload vessel count based on inspection types for user {}", userId);
		} else if (toggle == 0) {
			vslCountSqlQury = SqlQueries.UNIQUE_VSL_COUNT_BY_USER_DOWNLOAD;
			log.info("Getting Unique download vessel count based on inspection types for user {}", userId);
		} else
			return groupDataList;

		for (MaDescription maDesc : discList) {
			GroupingDataForChart gdata = new GroupingDataForChart();
			gdata.setKey(maDesc.getDesc());
			Query vslCountQuery = entityManager.createNativeQuery(vslCountSqlQury).unwrap(NativeQuery.class);
			vslCountQuery.setParameter("descId", maDesc.getId());
			vslCountQuery.setParameter("user_id", userId);
			gdata.setValue(vslCountQuery.getSingleResult());
			groupDataList.add(gdata);
		}
		return groupDataList;
	}

	@Override
	public List<GroupingDataForChart> getFileContByVslNameManager(Integer toggle) {
		log.info("Getting file count by vessel name for manager");
		if (toggle == 1)
			return getFileCountByVesselList(linkDetailsRepo.findDistinctVesselName(), toggle);
		else if (toggle == 0)
			return getFileCountByVesselList(folderDetailsRepo.findDistinctVesselName(), toggle);
		else
			return new ArrayList<GroupingDataForChart>();
	}

	@Override
	public List<GroupingDataForChart> getFileContByVslNameUser(Integer userId, Integer toggle) {
		log.info("Getting file count by vessel name for userid {}", userId);
		if (toggle == 1)
			return getFileCountByVesselList(linkDetailsRepo.findDistinctVesselNameByUserId(userId), toggle);
		else if (toggle == 0)
			return getFileCountByVesselList(folderDetailsRepo.findDistinctVesselNameByUserId(userId), toggle);
		else
			return new ArrayList<GroupingDataForChart>();
	}

	private List<GroupingDataForChart> getFileCountByVesselList(List<String> vslNameList, Integer toggle) {
		List<GroupingDataForChart> groupDataList = new ArrayList<GroupingDataForChart>();
		String fileCountSQLQuery = null;
		// Toggle 1 for upload and toggle 0 for download

		if (toggle == 1) {
			fileCountSQLQuery = SqlQueries.UPLOADED_FILES_COUNT_BY_VESSEL_NAME;
			log.info("Getting uploaded file count based on vessel name ");
		} else if (toggle == 0) {
			log.info("Getting downloaded file count based on vessel name ");
			fileCountSQLQuery = SqlQueries.DOWNLOADED_FILES_COUNT_BY_VESSEL_NAME;
		}
		Query fileCountQuery = entityManager.createNativeQuery(fileCountSQLQuery).unwrap(NativeQuery.class);
		for (String vslname : vslNameList) {
			log.info("Vessel name {}", vslname);
			GroupingDataForChart gdata = new GroupingDataForChart();
			gdata.setKey(vslname);
			fileCountQuery.setParameter("vslName", vslname);
			gdata.setValue(fileCountQuery.getSingleResult());
			groupDataList.add(gdata);
		}
		return groupDataList;
	}
}
