package com.bsol.iri.fileSharing.controller;

/**
 * 
 * @author rupesh
 *
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.models.CommunicationDetails;
import com.bsol.iri.fileSharing.models.Description;
import com.bsol.iri.fileSharing.models.Email;
import com.bsol.iri.fileSharing.models.InspectorAuditor;
import com.bsol.iri.fileSharing.models.ManagerLinkFiles;
import com.bsol.iri.fileSharing.models.NotViewedLink;
import com.bsol.iri.fileSharing.models.ReAssignTask;
import com.bsol.iri.fileSharing.models.RoleIdDesc;
import com.bsol.iri.fileSharing.models.SharedFileResponse;
import com.bsol.iri.fileSharing.models.UnlockUser;
import com.bsol.iri.fileSharing.models.UpdatePwd;
import com.bsol.iri.fileSharing.models.UserModel;
import com.bsol.iri.fileSharing.service.ManagerService;
import com.bsol.iri.fileSharing.service.UserService;

@RestController
@RequestMapping("api/manager")
public class ManagerController {

	private static final Logger log = LoggerFactory.getLogger(ManagerController.class);

	@Autowired
	private ManagerService managerService;

	@Autowired
	private UserService userService;

	@PutMapping("reassign")
	public ResponseEntity<Boolean> reAssignTaskToOtherInspector(@RequestBody ReAssignTask reAssignTask)
			throws CustomMessageException {
		log.info("Inside api/manager/reassign ");
		log.debug("ReAssignTask params are {} ", reAssignTask);
		return new ResponseEntity<Boolean>(managerService.reasignTask(reAssignTask), HttpStatus.OK);
	}

	/**
	 * Get all inspector and auditor
	 */

	@GetMapping("users")
	public ResponseEntity<List<InspectorAuditor>> getAllInspectorAndAuditor() throws CustomMessageException {
		log.info("Inside api/manager/users");
		return new ResponseEntity<List<InspectorAuditor>>(managerService.getAllUserName(), HttpStatus.OK);
	}

	/**
	 * Get all peding docs for review
	 */

	@GetMapping("links/pending/{pageNo}/{pageSize}")
	public ResponseEntity<List<NotViewedLink>> getAllPendingLinks(@PathVariable("pageNo") Integer pageNo,
			@PathVariable("pageSize") Integer pageSize) throws CustomMessageException {
		log.info("Inside api/manager/links/pending/{pageNo}/{pageSize} ");
		log.debug("params are pagenumber  {} page size ", pageNo, pageSize);
		return new ResponseEntity<List<NotViewedLink>>(managerService.getUnViewedLinks(pageNo, pageSize),
				HttpStatus.OK);
	}

	/**
	 * Get files of that link
	 * 
	 * @throws DataNotFoundException
	 */

	@GetMapping("link/files/{linkId}")
	public ResponseEntity<List<ManagerLinkFiles>> getAllPendingFiles(@PathVariable("linkId") Integer linkId)
			throws CustomMessageException, DataNotFoundException {
		log.info("Inside api/manager/link/files/{linkId} ");
		log.debug("params are linkId {} ", linkId);
		return new ResponseEntity<List<ManagerLinkFiles>>(managerService.getAllFilesToRevies(linkId), HttpStatus.OK);
	}

	/**
	 * add / update description
	 */

	@PostMapping("description")
	public ResponseEntity<Integer> getAddUpdateDescription(@RequestBody Description desc)
			throws CustomMessageException, DataNotFoundException {
		log.info("Inside api/manager/description ");
		log.debug("Params are {} ", desc);
		return new ResponseEntity<Integer>(managerService.addUpdateDescription(desc), HttpStatus.OK);
	}

	/**
	 * remove description
	 */
	@DeleteMapping("description/{descId}")
	public ResponseEntity<Integer> deleteDescription(@PathVariable("descId") Integer descId)
			throws CustomMessageException, DataNotFoundException {
		log.info("Inside api/manager/description/{descId}");
		log.debug("Params are {} ", descId);
		return new ResponseEntity<Integer>(managerService.deleteDescription(descId), HttpStatus.OK);
	}

	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws CustomMessageException
	 * @throws DataNotFoundException
	 */

	@GetMapping("user/links/{userId}")
	public ResponseEntity<List<SharedFileResponse>> getUserPendingLinks(@PathVariable("userId") Integer userId)
			throws CustomMessageException, DataNotFoundException {
		log.info("Inside api/manager/user/links/{userId} ");
		log.debug("Params are {} ", userId);
		return new ResponseEntity<List<SharedFileResponse>>(userService.getSharedFiles(userId), HttpStatus.OK);
	}

	@GetMapping("link/history")
	public ResponseEntity<List<CommunicationDetails>> getCommunicationHistory()
			throws CustomMessageException, DataNotFoundException {
		log.info("Inside api/manager/link/history ");
		return new ResponseEntity<List<CommunicationDetails>>(managerService.getLinkComunication(), HttpStatus.OK);
	}

	@PostMapping("add/user")
	public ResponseEntity<Integer> createUser(@RequestBody UserModel user) throws CustomMessageException {
		log.info("Inside api/manager/add/user ");
		log.debug("Params are {} ", user);
		return new ResponseEntity<Integer>(managerService.addUser(user), HttpStatus.OK);
	}

	// get all user
	@GetMapping("all/users")
	public ResponseEntity<List<UserModel>> getAllUser() {
		log.info("Inside api/manager/all/users ");
		return new ResponseEntity<List<UserModel>>(managerService.getAllUser(), HttpStatus.OK);
	}

	@PutMapping("update/status")
	public ResponseEntity<Integer> updateUser(@RequestBody UserModel user) throws CustomMessageException {
		log.info("Inside api/manager/update/status ");
		log.debug("Params are {} ", user);
		return new ResponseEntity<Integer>(managerService.updateUser(user), HttpStatus.OK);
	}

	@GetMapping("all/roles")
	public ResponseEntity<List<RoleIdDesc>> getAllRoles() {
		log.info("Inside api/manager/all/roles");
		return new ResponseEntity<List<RoleIdDesc>>(managerService.getAllRoles(), HttpStatus.OK);
	}

	@PutMapping("change/password")
	public ResponseEntity<Boolean> updatePassword(@RequestBody UpdatePwd updatePwd) throws CustomMessageException {
		log.info("Inside api/manager/change/password ");
		log.debug("Params are {} ", updatePwd);
		return new ResponseEntity<Boolean>(userService.updatePwd(updatePwd), HttpStatus.OK);
	}

	@GetMapping("{status}/users")
	public ResponseEntity<List<InspectorAuditor>> getAllInspectorAndAuditor(@PathVariable("status") String status)
			throws CustomMessageException {
		log.info("Inside api/manager/ {status}/users");
		log.debug("Params are {} ", status);
		return new ResponseEntity<List<InspectorAuditor>>(managerService.getUserByStatus(status), HttpStatus.OK);
	}
	
	@PutMapping("resetDefault/password")
	public ResponseEntity<Boolean> resetDefaultPassword(@RequestBody Email email) throws CustomMessageException {
		log.info("inside api/manager/resetDefault/password");
		log.debug("isExpired password  = {}", email.getEmail());
		return new ResponseEntity<Boolean>(managerService.resetTodefaultPassword(email.getEmail()), HttpStatus.OK);
	}
	
	
	@PutMapping("unlock/user")
	public ResponseEntity<Boolean> unlockUser(@RequestBody UnlockUser user) throws CustomMessageException {
		log.info("inside api/manager/unlock/user");
		log.debug("Unlocking user with Id  = {}", user.getEmail());
		return new ResponseEntity<Boolean>(managerService.unlockUser(user), HttpStatus.OK);
	}
}
