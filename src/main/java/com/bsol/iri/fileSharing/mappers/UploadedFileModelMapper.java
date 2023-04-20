package com.bsol.iri.fileSharing.mappers;
/**
 * 
 * @author rupesh
 * This class is used to map UploadedFile entiry to Dashboard class object
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bsol.iri.fileSharing.entity.UploadedFiles;
import com.bsol.iri.fileSharing.models.DashBoardData;

public class UploadedFileModelMapper {

	Map<Integer, String> linkMap = null;

	public UploadedFileModelMapper(Map<Integer, String> linkMap) {
		this.linkMap = linkMap;
	}

	public List<DashBoardData> getDashBoardData(List<UploadedFiles> fils) {
		List<DashBoardData> dataList = new ArrayList<DashBoardData>();
		for (UploadedFiles file : fils) {
			dataList.add(getdashBoardData(file));
		}

		return dataList;
	}

	public DashBoardData getdashBoardData(UploadedFiles file) {
		DashBoardData dash = new DashBoardData();

		dash.setCreatedTime(file.getCreatedOn());
		dash.setEmail(linkMap.get(file.getLinkId()));
		dash.setFileSize(file.getFileSize());
		dash.setType(file.getFileType());

		return dash;
	}
}
