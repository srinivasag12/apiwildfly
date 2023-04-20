package com.bsol.iri.fileSharing.repos;


/**
 * 
 * @author rupesh
 *	Repository
 */


import org.springframework.data.jpa.repository.JpaRepository;

import com.bsol.iri.fileSharing.entity.MaFileLocation;

public interface MaFileLocationRepo extends JpaRepository<MaFileLocation, Integer> {

}
