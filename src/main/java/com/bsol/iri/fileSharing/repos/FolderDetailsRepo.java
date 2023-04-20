package com.bsol.iri.fileSharing.repos;


/**
 * 
 * @author rupesh
 *	Repository
 */


import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bsol.iri.fileSharing.entity.FolderDetails;

public interface FolderDetailsRepo extends JpaRepository<FolderDetails, Integer> {

	List<FolderDetails> findByVesselNameAndVesselDescAndImoAndVslOfficialNo(String vesselName, Integer vesselDesc,
			Integer imo, String vslOfficialNo);

	List<FolderDetails> findByUserId(Integer userId, Pageable page);

	List<FolderDetails> findByUserId(Integer userId);

	List<FolderDetails> findByFolderIdIn(Collection<Integer> folderIds);

	FolderDetails findByUserIdAndFolderId(Integer folderId, Integer userId);

	List<FolderDetails> findByUserIdAndIsArchivedAndArchivedOnBetween(Integer userId, Integer isArchive, Date StartDate,
			Date endDate);
	
	List<FolderDetails> findByUserIdAndVesselNameAndVesselDescAndImoAndVslOfficialNo(Integer userid,String vesselName, Integer vesselDesc,
			Integer imo, String vslOfficialNo);

	FolderDetails findByVesselNameAndVesselDesc(String name, Integer desc);
	
	@Query(value = "select Distinct vessel_name from FOLDER_DETAILS where CREATED_ON between  TO_TIMESTAMP(SYSDATE-30, 'DD-MON-RR HH.MI.SSXFF AM') and  TO_TIMESTAMP(SYSDATE+1, 'DD-MON-RR HH.MI.SSXFF AM') ", nativeQuery = true)
	List<String> findDistinctVesselName();

	@Query(value = "select Distinct vessel_name from FOLDER_DETAILS where user_id = ?1 and CREATED_ON between  TO_TIMESTAMP(SYSDATE-30, 'DD-MON-RR HH.MI.SSXFF AM') and  TO_TIMESTAMP(SYSDATE+1, 'DD-MON-RR HH.MI.SSXFF AM') ", nativeQuery = true)
	List<String> findDistinctVesselNameByUserId(Integer UserId);
}
