package com.bsol.iri.fileSharing.repos;


/**
 * 
 * @author rupesh
 *	Repository
 */


import org.springframework.data.jpa.repository.JpaRepository;

import com.bsol.iri.fileSharing.entity.FileShared;
import com.bsol.iri.fileSharing.entity.FileSharedIds;

public interface FileSharedRepo extends JpaRepository<FileShared, FileSharedIds> {

}
