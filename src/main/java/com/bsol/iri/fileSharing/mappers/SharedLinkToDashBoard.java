package com.bsol.iri.fileSharing.mappers;
/**
 * 
 * @author rupesh
 * This class is used to map  LinkDetails entity to Dashboard class object or list of objects
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsol.iri.fileSharing.entity.LinkDetails;
import com.bsol.iri.fileSharing.models.DashBoardLinkDetails;

public class SharedLinkToDashBoard {
	private final Logger log = LoggerFactory.getLogger(SharedLinkToDashBoard.class);

	Map<Integer, String> discriptionMap = null;

	public SharedLinkToDashBoard(Map<Integer, String> discriptionMap) {
		this.discriptionMap = discriptionMap;
	}

	private DashBoardLinkDetails getDashBoardLinkDetails(LinkDetails ld) {
		log.info("Inside getDashBoardLinkDetails");
		DashBoardLinkDetails dash = new DashBoardLinkDetails();
		dash.setDesc(discriptionMap.get(ld.getLinkDesc()));
		dash.setEmail(ld.getEmail());
		dash.setLinkStatus(ld.getLinkStatus());
		dash.setLoggedInStatus(ld.getLoggedInStatus());
		dash.setSharedDate(ld.getCreatedAt());
		dash.setVesselname(ld.getVesselName());

		return dash;
	}

	public List<DashBoardLinkDetails> getDashBoardLinkDetailsList(List<LinkDetails> lds) {
		log.info("Inside getDashBoardLinkDetailsList");
		List<DashBoardLinkDetails> linkDetails = new ArrayList<DashBoardLinkDetails>();
		for (LinkDetails ld : lds) {
			linkDetails.add(getDashBoardLinkDetails(ld));
		}
		return linkDetails;
	}
}
