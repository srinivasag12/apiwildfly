package com.bsol.iri.fileSharing.repos;


/**
 * 
 * @author rupesh
 *	Repository
 */


import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bsol.iri.fileSharing.entity.DownloadLink;

public interface DownloadLinkRepo extends JpaRepository<DownloadLink, Integer> {

	DownloadLink findFirstByURL(String uRL);

//	List<DownloadLink> findByFolderIdIn(Collection<Integer> folderIdList, Pageable page);

	List<DownloadLink> findByFolderIdInAndLinkStatusIn(Collection<Integer> folderIdList, Collection<Integer> linkStatus,
			Pageable page);

//	Optional<DownloadLink> findbyuRL(String url);

	List<DownloadLink> findByCreatedBy(Integer userId);

	List<DownloadLink> findByFolderId(Integer folderId);

	DownloadLink findFirstByFolderIdAndLinkStatusIn(Integer folderId, Collection<Integer> linkStatus);

	DownloadLink findFirstByFolderIdAndEmailAndLinkStatusIn(Integer folderId, String email,
			Collection<Integer> linkStatus);

	DownloadLink findByFolderIdAndEmailAndLinkStatusInAndCreatedBy(Integer folderId, String email,
			Collection<Integer> linkStatus, Integer userId);

	List<DownloadLink> findByFolderIdAndLinkStatus(Integer folderId, Integer linkStatus);
	
	
	List<DownloadLink> findByLinkIdInAndLinkStatusIn(Collection<Integer> linkIds, Collection<Integer> statusCode);
	
	List<DownloadLink> findByFolderIdAndLinkStatusIn(Integer folderId, Collection<Integer> linkStatus);
}
