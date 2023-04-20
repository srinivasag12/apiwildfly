package com.bsol.iri.fileSharing.repos;


/**
 * 
 * @author rupesh
 *	Repository
 */


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bsol.iri.fileSharing.entity.MaUser;

public interface UserRepo extends JpaRepository<MaUser, Integer> {

	MaUser findByEmail(String email);

	List<MaUser> findAllByRole(Integer roleId);

	MaUser findByEmailAndStatus(String email, Integer status);

	List<MaUser> findAllByRoleAndStatus(Integer roleId, Integer statusCode);

}
