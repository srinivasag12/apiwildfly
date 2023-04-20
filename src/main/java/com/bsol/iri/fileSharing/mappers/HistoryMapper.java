package com.bsol.iri.fileSharing.mappers;

/**
 * 
 * @author rupesh
 *
 *This class is used to map HistotyDetails from Link and Folder details
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bsol.iri.fileSharing.models.CommunicationDetails;
import com.bsol.iri.fileSharing.models.LinkCommunicationHistory;

public class HistoryMapper {

	List<LinkCommunicationHistory> FolderCommunicationHistorylist = null;
	List<LinkCommunicationHistory> LinkCommunicationHistorylist = null;
	Map<Integer, String> descMap = null;
	List<CommunicationDetails> communicationDetailsList = new ArrayList<CommunicationDetails>();

	public HistoryMapper(List<LinkCommunicationHistory> LinkCommunicationHistorylist,
			List<LinkCommunicationHistory> FolderCommunicationHistorylist, Map<Integer, String> descMap) {
		this.FolderCommunicationHistorylist = FolderCommunicationHistorylist;
		this.LinkCommunicationHistorylist = LinkCommunicationHistorylist;
		this.descMap = descMap;
	}

	public List<CommunicationDetails> getCompleteHistory() {
		if (FolderCommunicationHistorylist.size() >= LinkCommunicationHistorylist.size()) {
			for (int i = 0; i < FolderCommunicationHistorylist.size(); i++) {
				CommunicationDetails communicationDetails = new CommunicationDetails();
				communicationDetails.setVessel((String) FolderCommunicationHistorylist.get(i).getVESSEL_NAME());
				communicationDetails.setImo((Integer) FolderCommunicationHistorylist.get(i).getIMO());
				communicationDetails.setOfficalNo((String) FolderCommunicationHistorylist.get(i).getOFFICIALNO());
				communicationDetails.setDesc(descMap.get((Integer) FolderCommunicationHistorylist.get(i).getVSLDESC()));
				communicationDetails.setDownloadCount((FolderCommunicationHistorylist.get(i).getCTIMES()));
				for (int j = 0; j < LinkCommunicationHistorylist.size(); j++) {
					if (LinkCommunicationHistorylist.get(j).getVESSEL_NAME()
							.equals(FolderCommunicationHistorylist.get(i).getVESSEL_NAME())
							&& LinkCommunicationHistorylist.get(j).getVSLDESC()
									.equals(FolderCommunicationHistorylist.get(i).getVSLDESC())) {
						communicationDetails.setUploadCount((LinkCommunicationHistorylist.get(j).getCTIMES()));
						LinkCommunicationHistorylist.remove(j);
						break;
					}
				}

				communicationDetailsList.add(communicationDetails);
			}

			for (int i = 0; i < LinkCommunicationHistorylist.size(); i++) {
				CommunicationDetails communicationDetails = new CommunicationDetails();
				communicationDetails.setVessel((String) LinkCommunicationHistorylist.get(i).getVESSEL_NAME());
				communicationDetails.setDesc(descMap.get(LinkCommunicationHistorylist.get(i).getVSLDESC()));
				communicationDetails.setImo((Integer) LinkCommunicationHistorylist.get(i).getIMO());
				communicationDetails.setOfficalNo((String) LinkCommunicationHistorylist.get(i).getOFFICIALNO());
				communicationDetails.setUploadCount((LinkCommunicationHistorylist.get(i).getCTIMES()));
				communicationDetailsList.add(communicationDetails);
			}
		} else {
			for (int i = 0; i < LinkCommunicationHistorylist.size(); i++) {
				CommunicationDetails communicationDetails = new CommunicationDetails();
				communicationDetails.setVessel((String) LinkCommunicationHistorylist.get(i).getVESSEL_NAME());
				communicationDetails.setDesc(descMap.get((Integer) LinkCommunicationHistorylist.get(i).getVSLDESC()));
				communicationDetails.setImo((Integer) LinkCommunicationHistorylist.get(i).getIMO());
				communicationDetails.setOfficalNo((String) LinkCommunicationHistorylist.get(i).getOFFICIALNO());
				communicationDetails.setUploadCount((LinkCommunicationHistorylist.get(i).getCTIMES()));
				for (int j = 0; j < FolderCommunicationHistorylist.size(); j++) {
					if (FolderCommunicationHistorylist.get(j).getVESSEL_NAME()
							.equals(LinkCommunicationHistorylist.get(i).getVESSEL_NAME())
							&& FolderCommunicationHistorylist.get(j).getVSLDESC()
									.equals(LinkCommunicationHistorylist.get(i).getVSLDESC())) {
						communicationDetails.setDownloadCount((FolderCommunicationHistorylist.get(j).getCTIMES()));
						FolderCommunicationHistorylist.remove(j);
					}
				}
				communicationDetailsList.add(communicationDetails);
			}
			for (int i = 0; i < FolderCommunicationHistorylist.size(); i++) {
				CommunicationDetails communicationDetails = new CommunicationDetails();
				communicationDetails.setVessel((String) FolderCommunicationHistorylist.get(i).getVESSEL_NAME());
				communicationDetails.setDesc(descMap.get((Integer) FolderCommunicationHistorylist.get(i).getVSLDESC()));
				communicationDetails.setImo((Integer) FolderCommunicationHistorylist.get(i).getIMO());
				communicationDetails.setOfficalNo((String) FolderCommunicationHistorylist.get(i).getOFFICIALNO());
				communicationDetails.setDownloadCount((FolderCommunicationHistorylist.get(i).getCTIMES()));
				communicationDetailsList.add(communicationDetails);
			}
		}

		return communicationDetailsList;
	}
}
