package com.bsol.iri.fileSharing.mappers;

/**
 * 
 * @author rupesh
 *This class is used to map LinkDetails class object to different types of Object
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsol.iri.fileSharing.entity.LinkDetails;
import com.bsol.iri.fileSharing.models.AllLinkDetailsResponse;
import com.bsol.iri.fileSharing.models.LinkDetailsModel;

public class LinkDetailsMapper {
	private final Logger log = LoggerFactory.getLogger(LinkDetailsMapper.class);

	private Map<Integer, String> descMap = null;

	 

	public LinkDetailsMapper(Map<Integer, String> descMap) {
		this.descMap = descMap;
	}


	public LinkDetailsModel linkDetailsToLinkDetailsModel(LinkDetails linkDetails) {

		log.info("Inside  linkDetailsToLinkDetailsModel");

		LinkDetailsModel model = new LinkDetailsModel();
		model.setCancelRequest(linkDetails.getCancelRequest());
		model.setEmail(linkDetails.getEmail());
		model.setExpiryDate(linkDetails.getExpiryDate());
		model.setIsExtended(linkDetails.getIsExtended());
		model.setLinkDesc(descMap.get(linkDetails.getLinkDesc()));
		model.setLinkStatus(linkDetails.getLinkStatus());
		model.setLinkType(linkDetails.getLinkType());
		model.setLoggedInStatus(linkDetails.getLoggedInStatus());
		model.setVesselName(linkDetails.getVesselName());
		model.setUserId(linkDetails.getUserId());
		model.setLinkId(linkDetails.getLinkId());
		model.setIsViewed(linkDetails.getIsViewed());

		return model;
	}

	public AllLinkDetailsResponse getAllLinkResponseFromLinkDetails(LinkDetails linkDetails) {

		log.trace("Inside  getAllLinkResponseFromLinkDetails");

		AllLinkDetailsResponse resp = new AllLinkDetailsResponse();

		resp.setEmail(linkDetails.getEmail());
		resp.setExpiryDate(linkDetails.getExpiryDate());
		resp.setGenDateTime(linkDetails.getCreatedAt());
		resp.setLink(linkDetails.getLink());
		resp.setLinkStatus(linkDetails.getLinkStatus());
		resp.setLoggedInStatus(linkDetails.getLoggedInStatus());
		resp.setLinkDesc(descMap.get(linkDetails.getLinkDesc()));
		resp.setVesselName(linkDetails.getVesselName());
		resp.setExtendedDays(linkDetails.getExtended_days());
		resp.setImo(linkDetails.getImo());
		resp.setVslOfficialNo(linkDetails.getOfficialNo());
		resp.setIsViewd(linkDetails.getIsViewed());
		return resp;
	}

	public List<AllLinkDetailsResponse> getAllLinkResponseFromLinkDetails(List<LinkDetails> linkDetailList) {
		log.trace("Inside  getAllLinkResponseFromLinkDetails");
		List<AllLinkDetailsResponse> list = new ArrayList<AllLinkDetailsResponse>();
		for (LinkDetails linkDetails : linkDetailList) {
			list.add(getAllLinkResponseFromLinkDetails(linkDetails));
		}
		return list;
	}

}
