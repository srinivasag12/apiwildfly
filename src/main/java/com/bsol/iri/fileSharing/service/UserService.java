package com.bsol.iri.fileSharing.service;


import java.io.IOException;

/**
 * @author rupesh
 */


import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.models.AllLinkDetailsResponse;
import com.bsol.iri.fileSharing.models.ArchiveDetails;
import com.bsol.iri.fileSharing.models.ArchiveFolder;
import com.bsol.iri.fileSharing.models.ArchiveLink;
import com.bsol.iri.fileSharing.models.CommunicationDetails;
import com.bsol.iri.fileSharing.models.DashBoardData;
import com.bsol.iri.fileSharing.models.DashBoardLinkDetails;
import com.bsol.iri.fileSharing.models.DashBoardSharedFile;
import com.bsol.iri.fileSharing.models.Description;
import com.bsol.iri.fileSharing.models.Folder;
import com.bsol.iri.fileSharing.models.LinkDetail;
import com.bsol.iri.fileSharing.models.LinkHistory;
import com.bsol.iri.fileSharing.models.LinkIdArray;
import com.bsol.iri.fileSharing.models.LinkStatusChangeReq;
import com.bsol.iri.fileSharing.models.LinkValidity;
import com.bsol.iri.fileSharing.models.PrevouslyUploadedFiles;
import com.bsol.iri.fileSharing.models.RemoveSpecificFile;
import com.bsol.iri.fileSharing.models.ShareFile;
import com.bsol.iri.fileSharing.models.SharedFileResponse;
import com.bsol.iri.fileSharing.models.SharedToThirdPartyFiles;
import com.bsol.iri.fileSharing.models.UnArchiveRequest;
import com.bsol.iri.fileSharing.models.UpdatePwd;
import com.bsol.iri.fileSharing.models.UserModel;

public interface UserService {


	Map<String, String> authUser(UserModel user);

	ResponseEntity<Map<String, String>> logoutUser(Integer userId,HttpServletRequest request) throws DataNotFoundException;

	List<SharedFileResponse> getSharedFiles(Integer userId) throws DataNotFoundException;

	List<AllLinkDetailsResponse> getAllLinkDetails(Integer userId,int linkId, int page, int pageSise, String status)
			throws DataNotFoundException, CustomMessageException;

	Boolean updateLinkStatus(LinkStatusChangeReq status);

	Integer regenOTP(String URN) throws DataNotFoundException, CustomMessageException;

	Boolean extendLinkValidity(LinkValidity linkValidity);

	Boolean cancelRequest(String status);

	List<DashBoardData> getRecentThirdPartyUploadedFile(Integer userId, int pageNo, int pageSize)
			throws DataNotFoundException;

	List<DashBoardSharedFile> getRecentSharedFile(Integer userId, int pageNo, int pageSize)
			throws DataNotFoundException;

	List<DashBoardLinkDetails> getSharedLinkStatus(Integer userId, int pageNo, int pageSize)
			throws DataNotFoundException, CustomMessageException;

	Boolean completeDownload(Integer linkId) throws DataNotFoundException;

	Integer createLink(LinkDetail linkDetails) throws CustomMessageException;

	ResponseEntity<Map<String, String>> uploadFile(MultipartFile file, Integer fileOwner, Integer folderId) throws IOException;

	Set<String> sharedFiles(ShareFile shareFile) throws CustomMessageException;

	List<Folder> getSharedLink(Integer userId, int page, int pageSize) throws CustomMessageException;

	PrevouslyUploadedFiles getUploadedFiles(Integer folderId) throws DataNotFoundException;

	boolean extendDownlink(LinkValidity linkval);

	List<AllLinkDetailsResponse> getAllDownLink(Integer userId, Integer pageNo, Integer pageSize, String status) throws CustomMessageException;

	Boolean updateDownLinkStatus(LinkStatusChangeReq status) throws CustomMessageException;

	ArchiveDetails getArchiveFile(Integer userId) throws CustomMessageException;

	Integer unArchiveFile(UnArchiveRequest unArchive) throws CustomMessageException;

	Boolean archieveFile(Integer fileId) throws DataNotFoundException;

	Integer archiveFolder(ArchiveFolder archiveFolder) throws CustomMessageException;

	Integer unArchiveFolder(ArchiveFolder archiveFolder) throws CustomMessageException;

	List<Integer> archiveLink(ArchiveLink archieveLink) throws CustomMessageException;

	List<Integer> unArchiveLink(ArchiveLink archieveLink) throws CustomMessageException;

	List<Description> getDescription() throws CustomMessageException;

	Map<Integer, String> getDiscriptionMap() throws CustomMessageException;

	List<SharedToThirdPartyFiles> sharedToThirdParty(Integer userId) throws CustomMessageException;

	Boolean removeSpecificFileFromSharedDownloadLink(RemoveSpecificFile fileDetails);

	Boolean removeSharedFileFromAllUser(Integer fileId);
	
	
	String getDesctiptionById(Integer descId) throws CustomMessageException;

	List<CommunicationDetails> getLinkComunication(Integer userId) throws CustomMessageException;

	List<LinkHistory> getLinkHistory(char type, String vessel, Integer descId) throws CustomMessageException;

	Boolean linkViewed(LinkIdArray linkIds) throws CustomMessageException;

	Boolean mergeFiles(ShareFile shareFile);

	Boolean updatePwd(UpdatePwd updatePwd) throws CustomMessageException;

	Boolean checkFile(Integer fileId);

	String checkAccountStatus(String email);

	String getPWDHint(String email);

}
