package com.bsol.iri.fileSharing.service;

/**
 * 
 * @author rupesh
 *	
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bsol.iri.fileSharing.entity.LinkDetails;
import com.bsol.iri.fileSharing.entity.LoginDetailsHistory;
import com.bsol.iri.fileSharing.entity.MaDescription;
import com.bsol.iri.fileSharing.entity.MaRoles;
import com.bsol.iri.fileSharing.entity.MaUser;
import com.bsol.iri.fileSharing.entity.UploadedFiles;
import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.loginDetails.UserLoginDetailsService;
import com.bsol.iri.fileSharing.mappers.HistoryMapper;
import com.bsol.iri.fileSharing.models.CommunicationDetails;
import com.bsol.iri.fileSharing.models.Description;
import com.bsol.iri.fileSharing.models.InspectorAuditor;
import com.bsol.iri.fileSharing.models.LinkCommunicationHistory;
import com.bsol.iri.fileSharing.models.ManagerLinkFiles;
import com.bsol.iri.fileSharing.models.NotViewedLink;
import com.bsol.iri.fileSharing.models.ReAssignTask;
import com.bsol.iri.fileSharing.models.RoleIdDesc;
import com.bsol.iri.fileSharing.models.UnlockUser;
import com.bsol.iri.fileSharing.models.UserModel;
import com.bsol.iri.fileSharing.repos.EmailRepo;
import com.bsol.iri.fileSharing.repos.LinkDetailsRepo;
import com.bsol.iri.fileSharing.repos.LoginDetailsHistoryRepo;
import com.bsol.iri.fileSharing.repos.MaDescriptionRepo;
import com.bsol.iri.fileSharing.repos.MaRoleRepo;
import com.bsol.iri.fileSharing.repos.UploadedFileRepo;
import com.bsol.iri.fileSharing.repos.UserRepo;
import com.bsol.iri.fileSharing.util.AppConstant;
import com.bsol.iri.fileSharing.util.DateTimeUtil;
import com.bsol.iri.fileSharing.util.SqlQueries;

@Service
@SuppressWarnings({ "deprecation", "unchecked" })
public class ManagerServiceImpl implements ManagerService {

	private static final Logger log = LoggerFactory.getLogger(ManagerServiceImpl.class);

	@Autowired
	private LinkDetailsRepo linkDetailsRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private UploadedFileRepo uploadedFileRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private MaDescriptionRepo descriptionRepo;

	@Autowired
	private MaRoleRepo maRolesRepo;

	@Autowired
	private UserLoginDetailsService userLoginDetailsService;

	@Autowired
	private LoginDetailsHistoryRepo loginHistoryRepo;

	@Autowired
	EmailRepo emailRepo;

	@Override
	public Boolean reasignTask(ReAssignTask reAssignTask) throws CustomMessageException {

		log.info("Inside reasignTask");
		log.debug("Inside reasignTask with values {}", reAssignTask);
		if (reAssignTask.getLinkId().size() <= 0)
			throw new CustomMessageException("Linkid list should not be Empty");

		List<LinkDetails> linkDetailsList = new ArrayList<LinkDetails>();
		for (Integer linkId : reAssignTask.getLinkId()) {
			Optional<LinkDetails> linkDetailsOptional = linkDetailsRepo.findById(linkId);
			if (linkDetailsOptional.isPresent()) {
				LinkDetails linkDetails = linkDetailsOptional.get();
				String username = userRepo.findById(reAssignTask.getLoggedInUser()).get().getEmail();
				linkDetails.setUserId(reAssignTask.getNewUser());
				linkDetails.setUpdatedAt(DateTimeUtil.getTodaysDate());
				linkDetails.setUpdatedBy(username);
				linkDetails.setReassignedOn(DateTimeUtil.getTodaysDate());

				// updating user details in uploaded files
				log.info("Finding all files of linkId {}", linkId);
				List<UploadedFiles> uploadedFileList = uploadedFileRepo.findByLinkId(linkId);
				for (UploadedFiles uploadedFiles : uploadedFileList) {
					uploadedFiles.setUserId(reAssignTask.getNewUser());
				}
				log.info("Updating all files with the new userId {}", reAssignTask.getNewUser());
				uploadedFileRepo.saveAll(uploadedFileList);
				linkDetailsList.add(linkDetails);

			}
		}

		linkDetailsRepo.saveAll(linkDetailsList);

		if (linkDetailsRepo.saveAll(linkDetailsList).size() > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<InspectorAuditor> getAllUserName() {
		log.info("Inside getAllUserName( ) methode");
		List<InspectorAuditor> auditList = new ArrayList<InspectorAuditor>();

		log.info("Finding the user with role Inspector or Auditor");
		List<MaUser> userList = userRepo.findAllByRole(AppConstant.INSPECTOR_OR_AUDITOR_ROLE);
		log.info("Seting the response");
		for (MaUser maUser : userList) {
			InspectorAuditor usr = new InspectorAuditor();
			usr.setId(maUser.getUser_id());
			usr.setName(maUser.getEmail());
			auditList.add(usr);
		}
		return auditList;
	}

	@Override
	public List<NotViewedLink> getUnViewedLinks(Integer page, Integer pageSize) throws CustomMessageException {

		log.debug("Inside getUnViewedLinks with page - {} and pageSize - {} ", page, pageSize);
		List<NotViewedLink> notViewedLinkList = new ArrayList<NotViewedLink>();
		List<LinkDetails> LinkDetailList = linkDetailsRepo.findByLoggedInStatusAndLinkStatusAndIsArchive(
				AppConstant.COMPLETED_LOGGED_IN, AppConstant.COMPLETED_LINK, AppConstant.FALSE,
				PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "updatedBy")));

		for (LinkDetails linkDetails : LinkDetailList) {
			NotViewedLink link = new NotViewedLink();

			link.setVessel_NAME(linkDetails.getVesselName());
			link.setEmail(linkDetails.getEmail());
			link.setVsl_IMO_NO(linkDetails.getImo());
			link.setDescription(userService.getDesctiptionById(linkDetails.getLinkDesc()));
			link.setVsl_OFFICIAL_NO(linkDetails.getOfficialNo());
			link.setLid(linkDetails.getLinkId());
			notViewedLinkList.add(link);
		}
		return notViewedLinkList;
	}

	@Override
	public List<ManagerLinkFiles> getAllFilesToRevies(Integer linkId)
			throws DataNotFoundException, CustomMessageException {
		log.debug("Inside getUnViewedLinks with linkId - {}  ", linkId);
		List<UploadedFiles> uploadedFiles = uploadedFileRepo.findByLinkId(linkId);
		if (uploadedFiles.isEmpty()) {
			log.error("No file associated with the link id {}", linkId);
			throw new CustomMessageException("No file found, with given link");
		}
		List<ManagerLinkFiles> sharedFileDetails = new ArrayList<ManagerLinkFiles>();
		for (UploadedFiles uploadedFile : uploadedFiles) {
			if (uploadedFile.getIsDeleted() == AppConstant.FALSE && uploadedFile.getIsArchive() == AppConstant.FALSE) {
				ManagerLinkFiles dsFile = new ManagerLinkFiles();
				dsFile.setId(BigInteger.valueOf(uploadedFile.getFileId()));
				dsFile.setFileSize(uploadedFile.getFileSize());
				dsFile.setName(uploadedFile.getFileName());
				dsFile.setType(uploadedFile.getFileType());
				dsFile.setSharedDate(uploadedFile.getCreatedOn());
				dsFile.setIsViewed(uploadedFile.getIsViewed());
				sharedFileDetails.add(dsFile);
			}

		}
		LinkDetails lds = linkDetailsRepo.findById(linkId).get();
		lds.setIsViewed(AppConstant.TRUE);
		lds.setViewdAt(DateTimeUtil.getTodaysDate());
		linkDetailsRepo.save(lds);
		return sharedFileDetails;
	}

	@Override
	public Integer addUpdateDescription(Description desc) throws CustomMessageException {
		log.debug("Inside addUpdateDescription with Description - {}  ", desc);
		log.info("Adding description with details {}", desc);
		MaDescription maDesc = new MaDescription();
		if (desc.getId() == null) {
			maDesc.setDesc(desc.getDesc());
			maDesc.setCreatedOn(DateTimeUtil.getTodaysDate());
			return descriptionRepo.save(maDesc).getId();
		}
		Optional<MaDescription> descOptional = descriptionRepo.findById(desc.getId());
		if (descOptional.isPresent()) {
			maDesc = descOptional.get();
			maDesc.setDesc(desc.getDesc());
			maDesc.setCreatedOn(DateTimeUtil.getTodaysDate());
			maDesc.setIsActive(AppConstant.TRUE);
			return descriptionRepo.save(maDesc).getId();
		} else {
			throw new CustomMessageException("No details found with given details");
		}

	}

	@Override
	public Integer deleteDescription(Integer descId) throws CustomMessageException {

		log.debug("Inside deleteDescription with descId - {}  ", descId);

		Optional<MaDescription> descOptional = descriptionRepo.findById(descId);
		if (descOptional.isPresent()) {
			MaDescription maDesc = descOptional.get();
			maDesc.setIsActive(AppConstant.FALSE);
			maDesc.setUpdatedOn(DateTimeUtil.getTodaysDate());
			return descriptionRepo.save(maDesc).getId();
		} else {
			throw new CustomMessageException("No Description found");
		}

	}

	@Override
	public List<CommunicationDetails> getLinkComunication() throws CustomMessageException {

		log.debug("Inside getLinkComunication or history ");
		Query linkDetailsComunication = entityManager.createNativeQuery(SqlQueries.LINK_DETAILS_HISORY)
				.unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(LinkCommunicationHistory.class));

		linkDetailsComunication.setParameter("linkstatus", AppConstant.COMPLETED_LINK);
		List<LinkCommunicationHistory> LinkCommunicationHistorylist = (List<LinkCommunicationHistory>) linkDetailsComunication
				.getResultList();

		Query folderComunication = entityManager.createNativeQuery(SqlQueries.FOLDER_DETAILS_HISORY)
				.unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(LinkCommunicationHistory.class));
		folderComunication.setParameter("linkstatus", AppConstant.COMPLETED_LINK);

		List<LinkCommunicationHistory> FolderCommunicationHistorylist = (List<LinkCommunicationHistory>) folderComunication
				.getResultList();

		return new HistoryMapper(LinkCommunicationHistorylist, FolderCommunicationHistorylist,
				userService.getDiscriptionMap()).getCompleteHistory();
	}

	@Override
	public Integer addUser(UserModel user) throws CustomMessageException {

		log.debug("Inside addUser with details UserModel  ", user);
		if (user.getEmail().isEmpty() || user.getFirstname().isEmpty() || user.getPwd().isEmpty()
				|| user.getRoleId() < 0) {
			log.error("Validation error {}", user);
			throw new CustomMessageException("Some required fileds are empty");
		}
		MaUser usr = new MaUser();
		usr.setEmail(user.getEmail().toLowerCase());
		usr.setPwd(new BCryptPasswordEncoder().encode(user.getPwd()));
		usr.setStatus(user.getStatus());
		usr.setFirstName(user.getFirstname());
		usr.setLastName(user.getLastName());
		usr.setRole(user.getRoleId());

		userLoginDetailsService.createNewUserPassword(user.getEmail(), user.getPwd(), "");
		return userRepo.save(usr).getUser_id();
	}

	@Override
	public List<UserModel> getAllUser() {
		log.debug("Inside getAllUser : getting all user details ");
		List<UserModel> users = new ArrayList<UserModel>();
		List<MaUser> allUser = userRepo.findAll();
		for (MaUser maUser : allUser) {
			UserModel usr = new UserModel();

			usr.setEmail(maUser.getEmail());
			usr.setFirstname(maUser.getFirstName());
			usr.setLastName(maUser.getLastName());
			usr.setRoleId(maUser.getRole());
			usr.setStatus(maUser.getStatus());
			usr.setUserId(maUser.getUser_id());
			users.add(usr);
		}

		return users;
	}

	@Override
	public Integer updateUser(UserModel user) throws CustomMessageException {
		log.debug("Inside update with details UserModel  ", user);
		Optional<MaUser> userOptional = userRepo.findById(user.getUserId());
		if (userOptional.isPresent()) {
			MaUser maUser = userOptional.get();
			maUser.setRole(user.getRoleId());
			maUser.setStatus(user.getStatus());
			return userRepo.save(maUser).getUser_id();

		} else {
			log.error("No user found with given details");
			throw new CustomMessageException("No user found with given details");
		}
	}

	@Override
	public List<RoleIdDesc> getAllRoles() {
		log.debug("Inside getAllRoles ");
		List<RoleIdDesc> RoleIdDescs = new ArrayList<RoleIdDesc>();
		List<MaRoles> roles = maRolesRepo.findAll();
		for (MaRoles maRoles : roles) {
			RoleIdDesc rid = new RoleIdDesc();
			rid.setId(maRoles.getRoleId());
			rid.setDesc(maRoles.getRoleDesc());
			RoleIdDescs.add(rid);
		}

		return RoleIdDescs;
	}

	@Override
	public List<InspectorAuditor> getUserByStatus(String status) {
		log.debug("Inside getUserByStatus with status - {}  ", status);
		Integer statusCode = 0;
		if (status.equalsIgnoreCase("a"))
			statusCode = 1;
		List<InspectorAuditor> auditList = new ArrayList<InspectorAuditor>();
		List<MaUser> userList = userRepo.findAllByRoleAndStatus(AppConstant.INSPECTOR_OR_AUDITOR_ROLE, statusCode);
		for (MaUser maUser : userList) {
			InspectorAuditor usr = new InspectorAuditor();
			usr.setId(maUser.getUser_id());
			usr.setName(maUser.getEmail());
			auditList.add(usr);
		}
		return auditList;
	}

	@Override
	public Boolean resetTodefaultPassword(String email) throws CustomMessageException {
		MaUser user = userRepo.findByEmail(email);
		if (user == null)
			throw new CustomMessageException("User Not Found");
		else {
			user.setPwd(new BCryptPasswordEncoder().encode(AppConstant.DEFAULT_PASSWORD));
			user.setStatus(AppConstant.ACTIVE);
			user.setLastLogOut(null);
			user.setLastLogIn(null);
			userRepo.save(user);
			userLoginDetailsService.createNewUserPassword(user.getEmail(), AppConstant.DEFAULT_PASSWORD,
					"Default Password");
			emailRepo.SendEmail(email, AppConstant.PASSWORD_RESET_SUB, AppConstant.PASSWORD_RESET_BODY);
			return true;
		}
	}

	@Override
	public Boolean unlockUser(UnlockUser unlockUser) throws CustomMessageException {
		MaUser user = userRepo.findByEmail(unlockUser.getEmail());
		if (user == null || (user.getUser_id() == unlockUser.getUserId())) {
			throw new CustomMessageException("User name and User Id is not matching");
		} else {

			LoginDetailsHistory loginHistory = loginHistoryRepo.findByEmailAndStatus(unlockUser.getEmail(),
					AppConstant.ACTIVE);
			if (loginHistory != null) {
				loginHistory.setLoginAttempt(AppConstant.LOGIN_INITIAL_COUNT);
				loginHistoryRepo.saveAndFlush(loginHistory);
			}

			user.setStatus(AppConstant.ACTIVE);
			log.debug("saving and flusing the data ");
			userRepo.saveAndFlush(user);
			log.debug("Sending notification to email {} ", unlockUser.getEmail());
			emailRepo.SendEmail(unlockUser.getEmail(), AppConstant.ACCOUNT_UNBLOCK_SUBJECT,
					AppConstant.ACCOUNT_UNBLOCK_BODY);
			return true;
		}
	}
}
