package com.bsol.iri.fileSharing.repos;

/**
 * 
 * @author rupesh
 *	Repository
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bsol.iri.fileSharing.entity.FileDownloadLink;
import com.bsol.iri.fileSharing.entity.FileDownloadLinkId;

public interface FileDownloadLinkRepo extends JpaRepository<FileDownloadLink, FileDownloadLinkId> {

	List<FileDownloadLink> findByDownloadLinkId(Integer downloadLinkId);

	List<FileDownloadLink> findByFileId(Integer fileId);

	@Query(value = "select (select count(FILE_ID)  from file_download_link where download_link_id = ?1  ) - (select count(FILE_ID)  from file_download_link where download_link_id =  ?1  and isviewed =1 ) as result  from dual", nativeQuery = true)
	Integer findDiffrent(Integer linkId);
}
