package com.bsol.iri.fileSharing.repos;


/**
 * 
 * @author rupesh
 *	Repository
 */


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bsol.iri.fileSharing.entity.UploadedFiles;

public interface UploadedFileRepo extends PagingAndSortingRepository<UploadedFiles, Integer> {

	List<UploadedFiles> findByUserIdAndCreatedOnAfter(Integer userId, Date logout, Pageable pageable);

	List<UploadedFiles> findByLinkId(Integer linkId);

	List<UploadedFiles> findByLinkIdAndFileIdIn(Integer linkId, List<Integer> fileIds);

	List<UploadedFiles> findByFolderId(Integer folderId);

	List<UploadedFiles> findByFileIdIn(List<Integer> fileIds);

	List<UploadedFiles> findByUserIdAndIsArchiveAndIsDeletedNot(Integer userId, int isArchieve, int isDeleted,
			Pageable pageable);

	UploadedFiles findByUserIdAndFileId(Integer userId, Integer fileId);

	List<UploadedFiles> findByUserIdAndCreatedOn(Integer userId, Date createdOn);

	List<UploadedFiles> findByUserIdAndIsArchiveAndIsDeletedAndArchivedDateBetween(Integer userId, int isArchieve,
			int isDeleted, Date start, Date End);
}
