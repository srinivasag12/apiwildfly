package com.bsol.iri.fileSharing.service;


/**
 * 
 * @author rupesh
 *	
 */

import java.util.List;
import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.models.CommunicationDetails;
import com.bsol.iri.fileSharing.models.Description;
import com.bsol.iri.fileSharing.models.InspectorAuditor;
import com.bsol.iri.fileSharing.models.ManagerLinkFiles;
import com.bsol.iri.fileSharing.models.NotViewedLink;
import com.bsol.iri.fileSharing.models.ReAssignTask;
import com.bsol.iri.fileSharing.models.RoleIdDesc;
import com.bsol.iri.fileSharing.models.UnlockUser;
import com.bsol.iri.fileSharing.models.UserModel;

public interface ManagerService {

	Boolean reasignTask(ReAssignTask reAssignTask) throws CustomMessageException;

	List<InspectorAuditor> getAllUserName();

	List<NotViewedLink> getUnViewedLinks(Integer page, Integer pageSize) throws CustomMessageException;

	List<ManagerLinkFiles>  getAllFilesToRevies(Integer linkId) throws DataNotFoundException, CustomMessageException;

	Integer addUpdateDescription(Description desc) throws CustomMessageException;

	Integer deleteDescription(Integer descId) throws CustomMessageException;

	List<CommunicationDetails> getLinkComunication() throws CustomMessageException;

	Integer addUser(UserModel user) throws CustomMessageException;

	List<UserModel> getAllUser();

	Integer updateUser(UserModel user) throws CustomMessageException;

	List<RoleIdDesc> getAllRoles();

	List<InspectorAuditor> getUserByStatus(String status);

	Boolean resetTodefaultPassword(String Email) throws CustomMessageException;

	Boolean unlockUser(UnlockUser user) throws CustomMessageException;

}
