package com.bsol.iri.fileSharing.repos;


/**
 * 
 * @author rupesh
 *	Repository
 */


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.bsol.iri.fileSharing.entity.FileShared;
import com.bsol.iri.fileSharing.entity.FileSharedIds;

public interface EmailRepo extends JpaRepository<FileShared, FileSharedIds> {

	@Procedure(name = "SEND_EMAIL")
	void SendEmail(@Param("p_to") String p_to, @Param("p_subject") String p_subject,
			@Param("p_message") String p_message);

}
