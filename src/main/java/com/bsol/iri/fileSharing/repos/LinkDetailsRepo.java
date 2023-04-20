package com.bsol.iri.fileSharing.repos;

/**
 * 
 * @author rupesh
 *	Repository
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bsol.iri.fileSharing.entity.LinkDetails;

public interface LinkDetailsRepo extends JpaRepository<LinkDetails, Integer> {

	LinkDetails findByLink(String link);

	LinkDetails findByEmailAndLinkAndLinkType(String email, String link, Integer linkType);

	// List<LinkDetails> findByUserIdAndLinkStatusNotIn(Integer userId, Integer
	// linkStatus);
	List<LinkDetails> findByUserIdAndLinkStatusNot(Integer userId, Integer linkStatus, Pageable pageable);

	List<LinkDetails> findByUserIdAndLinkTypeAndLinkStatusIn(Integer userId, Integer linkType,
			ArrayList<Integer> linkStatus, Pageable pageable);

	List<LinkDetails> findByUserId(Integer userId, Pageable pageable);

	List<LinkDetails> findByEmailAndVesselNameAndLinkDescAndImoAndOfficialNo(String email, String vesselName,
			Integer linkDesc, Integer imo, String officialNo);

	List<LinkDetails> findByVesselNameAndLinkDescAndLinkType(String vesselName, String linkDesc, int linkType);

	List<LinkDetails> findByUserIdAndLinkTypeAndLinkStatus(Integer userId, Integer linkType, Integer linkStatus,
			Pageable pageable);

	List<LinkDetails> findByLinkIdIn(Collection<Integer> links);

	LinkDetails findByLinkIdAndUserId(Integer linkId, Integer userId);

	List<LinkDetails> findByUserIdAndIsArchiveAndArchivedOnBetween(Integer userId, Integer isArchive, Date StartDate,
			Date endDate);

	LinkDetails findByLinkIdAndUserIdAndIsViewed(Integer linkId, Integer userId, Integer isViewed);

	List<LinkDetails> findByLoggedInStatusAndIsViewed(Integer loggedInStaus, Integer isViewed, Pageable pageable);

	List<LinkDetails> findByLoggedInStatusAndLinkStatusAndIsArchive(Integer loggedInStaus, Integer linkStatus,
			Integer isArchieve, Pageable pageable);

	List<LinkDetails> findByVesselNameAndLinkDescAndLinkStatus(String vslname, Integer linkDesc, Integer linkStatus);

	List<LinkDetails> findByUserIdAndIsViewedAndCompletedOnAfter(Integer userId, Integer IsViewed, Date start,
			Pageable pageable);

	@Query(value = "select Distinct vessel_name from link_details where CREATED_AT between  TO_TIMESTAMP(SYSDATE-30, 'DD-MON-RR HH.MI.SSXFF AM') and  TO_TIMESTAMP(SYSDATE+1, 'DD-MON-RR HH.MI.SSXFF AM') ", nativeQuery = true)
	List<String> findDistinctVesselName();

	@Query(value = "select Distinct vessel_name from link_details where user_id = ?1 and CREATED_AT between  TO_TIMESTAMP(SYSDATE-30, 'DD-MON-RR HH.MI.SSXFF AM') and  TO_TIMESTAMP(SYSDATE+1, 'DD-MON-RR HH.MI.SSXFF AM') ", nativeQuery = true)
	List<String> findDistinctVesselNameByUserId(Integer UserId);

}