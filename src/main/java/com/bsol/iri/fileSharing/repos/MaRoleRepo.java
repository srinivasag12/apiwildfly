package com.bsol.iri.fileSharing.repos;


/**
 * 
 * @author rupesh
 *	Repository
 */


import org.springframework.data.jpa.repository.JpaRepository;

import com.bsol.iri.fileSharing.entity.MaRoles;

public interface MaRoleRepo extends JpaRepository<MaRoles, Integer>{

}
