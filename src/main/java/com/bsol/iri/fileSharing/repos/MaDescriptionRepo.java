package com.bsol.iri.fileSharing.repos;


/**
 * 
 * @author rupesh
 *	Repository
 */


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsol.iri.fileSharing.entity.MaDescription;

public interface MaDescriptionRepo extends JpaRepository<MaDescription, Integer> {

	List<MaDescription> findByIsActive(Integer isActive);

}
