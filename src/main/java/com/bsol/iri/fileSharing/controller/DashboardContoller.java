package com.bsol.iri.fileSharing.controller;

/**
 * @author rupesh
 * This is a controller class , having all the mapping used in Dashboard
 */
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @author rupesh
 * This class is used to fetch data that will be shown on DashBoard
 */
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.models.DashboardArchiveFilesToDelete;
import com.bsol.iri.fileSharing.models.GroupingDataForChart;
import com.bsol.iri.fileSharing.models.RecentReassigned;
import com.bsol.iri.fileSharing.models.UnviewdFolders;
import com.bsol.iri.fileSharing.service.DashboardService;

@RestController
@RequestMapping("api/dashboard")
public class DashboardContoller {

	private static final Logger log = LoggerFactory.getLogger(DashboardContoller.class);

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("user/notviewdFolder/{userId}/{days}/{page}/{pageSize}/{toggle}")
	public ResponseEntity<List<UnviewdFolders>> foldersNotViewd(@PathVariable("userId") Integer userId,
			@PathVariable("days") Integer days, @PathVariable("page") Integer page,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("toggle") Integer toggle)
			throws CustomMessageException, ParseException {
		log.info(
				"inside api/dashboard/user/notviewdFolder/userId/days/page/pageSize/toggle  with data {}, {}, {}, {}, {}",
				userId, days, page, pageSize, toggle);
		return new ResponseEntity<List<UnviewdFolders>>(
				dashboardService.getUnviewdFiles(userId, days, page, pageSize, toggle), HttpStatus.OK);
	}

	@GetMapping("manager/notviewdFolder/{days}/{page}/{pageSize}/{toggle}")
	public ResponseEntity<List<UnviewdFolders>> allfoldersNotViewd(@PathVariable("days") Integer days,
			@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize,
			@PathVariable("toggle") Integer toggle) throws CustomMessageException, ParseException {
		log.info("inside api/dashboard/manager/notviewdFolder/days/page/pageSize/toggle with data {}, {}, {}, {} ",
				days, page, pageSize, toggle);
		return new ResponseEntity<List<UnviewdFolders>>(
				dashboardService.getAllUnviewdFiles(days, page, pageSize, toggle), HttpStatus.OK);
	}

	@GetMapping("user/archieveFileToDelete/{userId}/{days}/{page}/{pageSize}/{toggle}")
	public ResponseEntity<List<DashboardArchiveFilesToDelete>> archieveFileToDelete(
			@PathVariable("userId") Integer userId, @PathVariable("days") Integer days,
			@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize,
			@PathVariable("toggle") Integer toggle) throws CustomMessageException, ParseException {
		log.info(
				"inside api/dashboard/user/archieveFileToDelete/userId/days/page/pageSize/toggle  with data {}, {}, {}, {}, {}",
				userId, days, page, pageSize, toggle);

		return new ResponseEntity<List<DashboardArchiveFilesToDelete>>(
				dashboardService.userArchiveFileWillBeDeleted(userId, days, page, pageSize, toggle), HttpStatus.OK);
	}

	@GetMapping("manager/recentReassigned/{page}/{pageSize}")
	public ResponseEntity<List<RecentReassigned>> getRecentReassigned(@PathVariable("page") Integer page,
			@PathVariable("pageSize") Integer pageSize) {

		log.info("inside api/dashboard/manager/recentReassigned/page/pageSize with data  {}, {} ", page, pageSize);
		return new ResponseEntity<List<RecentReassigned>>(dashboardService.getAllRecentReassignedLinks(page, pageSize),
				HttpStatus.OK);
	}

	@GetMapping("user/vslByInspectionType/{userId}/{toggle}")
	public ResponseEntity<List<GroupingDataForChart>> getVesselsBasedOnInspectionTypeUser(
			@PathVariable("userId") Integer userId, @PathVariable("toggle") Integer toggle) {
		log.info("inside api/dashboard/user/vslByInspectionType/userId/toggle  with data {}, {}", userId, toggle);
		return new ResponseEntity<List<GroupingDataForChart>>(
				dashboardService.getVesselsBasedOnInspectioTypeForUser(userId, toggle), HttpStatus.OK);
	}

	@GetMapping("manager/vslByInspectionType/{toggle}")
	public ResponseEntity<List<GroupingDataForChart>> getVesselsBasedOnInspectionTypeManager(
			@PathVariable("toggle") Integer toggle) {
		log.info("inside api/dashboard/manager/vslByInspectionType/toggle with data  {} ", toggle);

		return new ResponseEntity<List<GroupingDataForChart>>(
				dashboardService.getVesselsBasedOnInspectioTypeFormanger(toggle), HttpStatus.OK);
	}

	@GetMapping("manager/filecountbyvsl/{toggle}")
	public ResponseEntity<Object> getfileCountByVessel(@PathVariable("toggle") Integer toggle) {
		log.info("inside api/dashboard/manager/filecountbyvsl/toggle with data  {} ", toggle);

		return new ResponseEntity<Object>(dashboardService.getFileContByVslNameManager(toggle), HttpStatus.OK);
	}

	@GetMapping("user/filecountbyvsl/{userId}/{toggle}")
	public ResponseEntity<Object> getfileCountByVesselByUserId(@PathVariable("userId") Integer userId,
			@PathVariable("toggle") Integer toggle) {
		log.info("inside api/dashboard/user/filecountbyvsl/userId/toggle with data  {} ,{}", userId, toggle);

		return new ResponseEntity<Object>(dashboardService.getFileContByVslNameUser(userId, toggle), HttpStatus.OK);
	}
}
