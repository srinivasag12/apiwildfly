package com.bsol.iri.fileSharing.repos;


/**
 * 
 * @author rupesh
 *	Repository
 */


import org.springframework.data.jpa.repository.JpaRepository;

import com.bsol.iri.fileSharing.entity.LinkOTP;

public interface LinkOtpRepo extends JpaRepository<LinkOTP, Integer> {

	Boolean existsByLinkIdAndOtp(Integer id, Integer otp);

}
