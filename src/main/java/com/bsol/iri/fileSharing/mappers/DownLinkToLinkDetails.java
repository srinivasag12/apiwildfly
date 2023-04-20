package com.bsol.iri.fileSharing.mappers;

/**
 * 
 * @author rupesh
 *		This is a mapper class used to map object of Downloadlink Details to other Object classes
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bsol.iri.fileSharing.entity.DownloadLink;
import com.bsol.iri.fileSharing.entity.FolderDetails;
import com.bsol.iri.fileSharing.models.AllLinkDetailsResponse;

public class DownLinkToLinkDetails {

	Map<Integer, String> folderVessel = new HashMap<Integer, String>();
	Map<Integer, String> folderDesc = new HashMap<Integer, String>();
	Map<Integer, Integer> vslImo = new HashMap<Integer, Integer>();
	Map<Integer, String> vslOffcialNo = new HashMap<Integer, String>();

	public DownLinkToLinkDetails(List<FolderDetails> fDlist, Map<Integer, String> descMap) {

		for (FolderDetails folderDetails : fDlist) {
			folderVessel.put(folderDetails.getFolderId(), folderDetails.getVesselName());
			vslImo.put(folderDetails.getFolderId(), folderDetails.getImo());
			vslOffcialNo.put(folderDetails.getFolderId(), folderDetails.getVslOfficialNo());
			folderDesc.put(folderDetails.getFolderId(), descMap.get(folderDetails.getVesselDesc()));
		}
	}

	public List<AllLinkDetailsResponse> getLinkDetailsResponseList(List<DownloadLink> DlinkList) {
		List<AllLinkDetailsResponse> respList = new ArrayList<AllLinkDetailsResponse>();
		for (DownloadLink downLink : DlinkList) {
			respList.add(getLinkDetailsResponse(downLink));
		}
		return respList;
	}

	public AllLinkDetailsResponse getLinkDetailsResponse(DownloadLink dLink) {
		AllLinkDetailsResponse resp = new AllLinkDetailsResponse();
		resp.setEmail(dLink.getEmail());
		resp.setExpiryDate(dLink.getExpired_on());
		resp.setExtendedDays(dLink.getExtDays());
		resp.setGenDateTime(dLink.getCreatedOn());
		resp.setLink(dLink.getURL());
		resp.setLinkStatus(dLink.getLinkStatus());
		resp.setVesselName(folderVessel.get(dLink.getFolderId()));
		resp.setLinkDesc(folderDesc.get(dLink.getFolderId()));
		resp.setImo(vslImo.get(dLink.getFolderId()));
		resp.setVslOfficialNo(vslOffcialNo.get(dLink.getFolderId()));
		resp.setLoggedInStatus(dLink.getLoggedInStatus());
		return resp;
	}

}
