package com.bsol.iri.fileSharing.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bsol.iri.fileSharing.models.UnviewdFolders;
import com.bsol.iri.fileSharing.util.AppConstant;
import com.bsol.iri.fileSharing.util.SqlQueries;

@Repository
@SuppressWarnings({ "deprecation", "unchecked" })
public class DashboardDaoImpl implements DashBoardDao {

	private static final Logger log = LoggerFactory.getLogger(DashboardDaoImpl.class);

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<UnviewdFolders> getUnviewdUploadedFiles(Integer userId, Integer pageStart, Integer pagelast,
			String startDate) {
		log.info("Getting the unviewd uploaded files for the userid {} from date {}", userId, startDate);
		Query unViewedFolderQuery = entityManager.createNativeQuery(SqlQueries.DASHBOARD_UNVIEWD_FOLDERS_UPLOAD)
				.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(UnviewdFolders.class));
		log.info("Fetching details from row number {}  to {}", pageStart, pagelast);
		unViewedFolderQuery.setParameter("user_id", userId);
		unViewedFolderQuery.setParameter("ISVIEWED", AppConstant.FALSE);
		unViewedFolderQuery.setParameter("datetostart", startDate);
		unViewedFolderQuery.setParameter("pageStart", pageStart);
		unViewedFolderQuery.setParameter("pageEnd", pagelast);

		return (List<UnviewdFolders>) unViewedFolderQuery.getResultList();

	}

	@Override
	public List<UnviewdFolders> getUnviewdDownloadedFiles(Integer userId, Integer pageStart, Integer pagelast,
			String startDate) {
		Query unViewedFolderQuery = entityManager.createNativeQuery(SqlQueries.DASHBOARD_UNVIEWD_FOLDERS_DOWNLOAD)
				.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(UnviewdFolders.class));
		log.info("Fetching unviewed downloaded files from row number {}  to {} starting from data {} for the user {}", pageStart, pagelast,startDate,userId);
		unViewedFolderQuery.setParameter("user_id", userId);
		unViewedFolderQuery.setParameter("logged_in_status", AppConstant.NOT_YET_LOGGED_IN);
		unViewedFolderQuery.setParameter("datetostart", startDate);
		unViewedFolderQuery.setParameter("pageStart", pageStart);
		unViewedFolderQuery.setParameter("pageEnd", pagelast);

		return (List<UnviewdFolders>) unViewedFolderQuery.getResultList();
	}

	@Override
	public List<UnviewdFolders> getAllUnviewdUploadedFiles(Integer pageStart, Integer pagelast, String startDate) {
		Query allUnviewedFolderQuery = entityManager.createNativeQuery(SqlQueries.DASHBOARD_All_UPLOAD_UNVIEWD_FOLDERS)
				.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(UnviewdFolders.class));
		log.info("Fetching all unviewed uploaded files from row number {}  to {} starting from date {}", pageStart, pagelast, startDate);
		allUnviewedFolderQuery.setParameter("ISVIEWED", AppConstant.FALSE);
		allUnviewedFolderQuery.setParameter("datetostart", startDate);
		allUnviewedFolderQuery.setParameter("pageStart", pageStart);
		allUnviewedFolderQuery.setParameter("pageEnd", pagelast);
		return (List<UnviewdFolders>) allUnviewedFolderQuery.getResultList();
	}

	@Override
	public List<UnviewdFolders> getAllUnviewdDownloadedFiles(Integer pageStart, Integer pagelast, String startDate) {
		Query allUnviewedFolderQuery = entityManager
				.createNativeQuery(SqlQueries.DASHBOARD_All_DOWNLOAD_UNVIEWD_FOLDERS).unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(UnviewdFolders.class));
		log.info("Fetching all unviewed downloaded files from row number {}  to {} starting from date {}", pageStart, pagelast,startDate);
		allUnviewedFolderQuery.setParameter("logged_in_status", AppConstant.NOT_YET_LOGGED_IN);
		allUnviewedFolderQuery.setParameter("datetostart", startDate);
		allUnviewedFolderQuery.setParameter("pageStart", pageStart);
		allUnviewedFolderQuery.setParameter("pageEnd", pagelast);
		return (List<UnviewdFolders>) allUnviewedFolderQuery.getResultList();
	}

}
