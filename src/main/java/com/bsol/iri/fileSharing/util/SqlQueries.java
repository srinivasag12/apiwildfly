package com.bsol.iri.fileSharing.util;

/**
 * 
 * @author rupesh
 * 
 *         All queried used in this application will be written here
 */

public class SqlQueries {

	public static final String sharedFiles = "select uf.f_id, uf.isViewed, uf.file_name, uf.isfolder, uf.file_type, uf.file_size , ld.email, ld.vessel_name, ld.Link_id as lid ,ld.VSL_IMO_NO, ld.VSL_OFFICIAL_NO, md.description, uf.created_by as uploadedBy , uf.created_on as uploadedAt from uploaded_files uf left join link_details ld  on uf.link_id = ld.link_id and ld.user_id = uf.user_id left join ma_description md on md.id = ld.link_desc where ld.link_status = :linkStatus  and ld.logged_in_status = :loggedInstatus  and uf.user_id = :userId and uf.isdeleted = 0 and uf.isArchive = 0";

	public static final String thirdPartyEmailByLinkId = "select DISTINCT   ld.link_id as linkId  ,ld.email as email from link_details ld join uploaded_files uf on ld.user_id = uf.user_id and ld.link_id =  uf.link_id where uf.created_on > :logoutDate";

	public static final String FILE_SHARED_TO_LOGGED_IN_USER = "select uf.f_id, mu.email, fs.created_on , uf.file_name, uf.file_size, uf.file_type from file_shared fs join uploaded_files uf on fs.file_id = uf.f_id and fs.shared_to = uf.user_id join ma_user mu on fs.shared_to = mu.user_id where mu.user_id = :userid";

	public static final String SHARED_THIRD_PARTY_FOLDERS = "select uf.f_id, uf.file_name, uf.file_size , uf.FILE_TYPE, fdl.download_link_id, dl.email, dl.created_on, dl.expired_on, dl.folder_id, fd.vessel_name, fd.vsl_imo_no, fd.vsl_official_no, md.DESCRIPTION, fdl.isViewed, fdl.viewedOn  from uploaded_files uf join file_download_link fdl on uf.f_id  =  fdl.file_id  join download_link dl on dl.link_id = fdl.download_link_id  join folder_details fd on fd.folder_id = dl.folder_id  join MA_DESCRIPTION md on md.id = fd.vessel_desc  where fdl.download_link_id in :linkId and uf.isarchive = 0 and dl.LINK_STATUS <> 2";

	public static final String VSL_DETAILS_BY_LINKID = "select fd.VESSEL_NAME, fd.VSL_IMO_NO  , fd.VSL_OFFICIAL_NO , md.description, dl.EXPIRED_ON from download_link dl join folder_details  fd on dl.folder_id = fd.folder_id join ma_description md on fd.vessel_desc = md.id where dl.link_id = :linkId";

	public static final String LINK_DETAILS_HISORY = "select vessel_name , link_desc AS vslDesc,VSL_IMO_NO as imo ,VSL_OFFICIAL_NO as officialNo, count(link_desc) AS ctimes from link_details where LINK_STATUS = :linkstatus GROUP by vessel_name, link_desc,VSL_IMO_NO,VSL_OFFICIAL_NO";

	public static final String FOLDER_DETAILS_HISORY = "select vessel_name, vessel_desc as vslDesc,VSL_IMO_NO as imo, VSL_OFFICIAL_NO as  officialNo,  count(dl.folder_id)as ctimes from folder_details fd join download_link  dl on fd.folder_id = dl.folder_id where dl.link_status = :linkstatus GROUP by dl.folder_id,vessel_name, vessel_desc, VSL_IMO_NO , VSL_OFFICIAL_NO";

	public static final String LINK_DETAILS_HISORY_BY_USER = "select vessel_name , link_desc AS vslDesc,VSL_IMO_NO as imo ,VSL_OFFICIAL_NO as officialNo, count(link_desc) AS ctimes from link_details where LINK_STATUS = :linkstatus and  USER_ID = :userId GROUP by vessel_name, link_desc,VSL_IMO_NO,VSL_OFFICIAL_NO";

	public static final String FOLDER_DETAILS_HISORY_BY_USER = "select vessel_name, vessel_desc as vslDesc,VSL_IMO_NO as imo, VSL_OFFICIAL_NO as  officialNo,  count(dl.folder_id)as ctimes from folder_details fd join download_link  dl on fd.folder_id = dl.folder_id  where USER_ID = :userId and dl.link_status in (1,3,4) GROUP by dl.folder_id,vessel_name, vessel_desc, VSL_IMO_NO , VSL_OFFICIAL_NO";

	public static final String DASHBOARD_UNVIEWD_FOLDERS_UPLOAD = "select * from (select ld.email as email, ld.vessel_name as  vesselname, md.description  as description, ld.VSL_OFFICIAL_NO as vslOfficialNo, ld.VSL_IMO_NO as IMO , ld.COMPLETED_ON as CompletedOn,   row_number()  over (order by ld.COMPLETED_ON desc) rn from link_details ld join ma_description md on md.id = ld.link_desc where ld.user_id= :user_id   and ld.ISVIEWED= :ISVIEWED  and ld.COMPLETED_ON > CAST(TO_DATE(:datetostart,'DD-MM-YY')AS TIMESTAMP ) order by ld.COMPLETED_ON desc) where rn between :pageStart and :pageEnd";

	public static final String DASHBOARD_All_UPLOAD_UNVIEWD_FOLDERS = "select * from (select ld.email as email, ld.vessel_name as  vesselname, md.description  as description, ld.VSL_OFFICIAL_NO as vslOfficialNo, ld.VSL_IMO_NO as IMO ,ld.COMPLETED_ON as CompletedOn, mu.email as owner, row_number()  over (order by ld.COMPLETED_ON desc) rn from link_details ld join ma_description md on md.id = ld.link_desc join ma_user mu on mu.user_id = ld.user_Id where ld.ISVIEWED= :ISVIEWED  and ld.COMPLETED_ON > CAST(TO_DATE(:datetostart,'DD-MM-YY')AS TIMESTAMP ) order by ld.COMPLETED_ON desc) where rn between :pageStart and :pageEnd";

	public static final String ARCHIVE_UPLOADED_FILE_GOING_TO_DELETE = "select * from (select FILE_NAME as FILENAME , F_ID AS ID ,ARCHIVED_DATE AS ARCHIEVEDON, (ARCHIVED_DATE+60)AS WILLBEDELETEDON, CREATED_BY as SHAREDBY, row_number()  over (order by archived_date desc) rn from uploaded_files where user_id = :user_id and  isarchive = :isarchive and isdeleted = :isdeleted and ((archived_date+60) between CAST(TO_DATE(:dateFrom,'DD-MM-YY')AS TIMESTAMP) and CAST(TO_DATE(:dateUpto,'DD-MM-YY')AS TIMESTAMP) ) and link_id  is not null ) where rn between :pageStart and :pageEnd";

	public static final String RECENT_RE_ASSIGNED = "select * from ( select  mu.email as reassignedTo, ld.email as submittedBy, ld.vessel_name as  vesselname, md.description  as description, ld.VSL_OFFICIAL_NO as vslOfficialNo, ld.VSL_IMO_NO as VSLIMO, ld.REASSIGNED_ON as REASSIGNEDON  ,ld.COMPLETED_ON as CompletedOn ,row_number()  over (order by ld.REASSIGNED_ON desc) rn  from link_details ld join  ma_user mu on mu.user_id = ld.user_id join ma_description md on md.id = ld.link_desc where ld.reassigned_on IS NOT NULL order by ld.REASSIGNED_ON desc) where rn between :pageStart and :pageEnd";

	public static final String UNIQUE_UPLOAD_VSL_COUNT = "select count(*) from (select distinct VESSEL_NAME, VSL_OFFICIAL_NO,VSL_IMO_NO  from link_details where link_desc = :descId  and LINK_STATUS in (1,3,4) and CANCEL_REQUEST = 0  and ISARCHIVE = 0 and CREATED_AT between  TO_TIMESTAMP(SYSDATE-30, 'DD-MON-RR HH.MI.SSXFF AM') and  TO_TIMESTAMP(SYSDATE+1, 'DD-MON-RR HH.MI.SSXFF AM'))";

	public static final String UNIQUE_DOWNLOAD_VSL_COUNT = "select count(*) from (select distinct VESSEL_NAME, VSL_OFFICIAL_NO,VSL_IMO_NO  from FOLDER_DETAILS where VESSEL_desc = :descId and (ISARCHIVE IS  NULL or ISARCHIVE = 0) and CREATED_ON between  TO_TIMESTAMP(SYSDATE-30, 'DD-MON-RR HH.MI.SSXFF AM') and  TO_TIMESTAMP(SYSDATE+1, 'DD-MON-RR HH.MI.SSXFF AM'))";

	public static final String UNIQUE_VSL_COUNT_BY_USER_UPLOAD = "select count(*) from (select distinct VESSEL_NAME, VSL_OFFICIAL_NO,VSL_IMO_NO  from link_details where link_desc = :descId and user_id = :user_id  and LINK_STATUS in (1,3,4) and CANCEL_REQUEST = 0  and ISARCHIVE = 0 and CREATED_AT between  TO_TIMESTAMP(SYSDATE-30, 'DD-MON-RR HH.MI.SSXFF AM') and  TO_TIMESTAMP(SYSDATE+1, 'DD-MON-RR HH.MI.SSXFF AM'))";

	public static final String UPLOADED_FILES_COUNT_BY_VESSEL_NAME = "select count(f_id)  from uploaded_files where  ISARCHIVE = 0  and ISDELETED = 0  and link_id in (select link_id from  link_details where ISARCHIVE = 0   and LINK_STATUS = 4 and vessel_name = :vslName)";
	
	public static final String DOWNLOADED_FILES_COUNT_BY_VESSEL_NAME = "select count(f_id)  from uploaded_files where ISARCHIVE = 0  and ISDELETED = 0 and FOLDER_ID in (select FOLDER_ID from  folder_details where vessel_name = :vslName)";
	
	public static final String UNIQUE_VSL_COUNT_BY_USER_DOWNLOAD="select count(*) from (select distinct VESSEL_NAME, VSL_OFFICIAL_NO,VSL_IMO_NO  from folder_details where vessel_desc = :descId and user_id = :user_id and (ISARCHIVE IS  NULL or ISARCHIVE =0) and CREATED_ON between  TO_TIMESTAMP(SYSDATE-30, 'DD-MON-RR HH.MI.SSXFF AM') and  TO_TIMESTAMP(SYSDATE+1, 'DD-MON-RR HH.MI.SSXFF AM'))";
	
	public static final String DASHBOARD_UNVIEWD_FOLDERS_DOWNLOAD ="select * from (select fd.vessel_name as  vesselname, fd.vsl_imo_no as IMO, fd.vsl_official_no as vslOfficialNo ,md.description  as description , dl.created_on  as CompletedOn,dl.email as email, row_number()  over (order by fd.created_on desc) rn from download_link dl join folder_details fd on dl.folder_id = fd.folder_id join ma_description md on md.id = fd.vessel_desc where dl.created_by = :user_id and dl.logged_in_status = :logged_in_status and  fd.created_on > CAST(TO_DATE(:datetostart,'DD-MM-YY')AS TIMESTAMP ) order by  fd.created_on desc) where rn between :pageStart  and :pageEnd";
	
	public static final String DASHBOARD_All_DOWNLOAD_UNVIEWD_FOLDERS ="select * from (select fd.vessel_name as  vesselname, fd.vsl_imo_no as IMO, fd.vsl_official_no as vslOfficialNo ,md.description  as description , dl.created_on  as CompletedOn, dl.email as email, row_number()  over (order by fd.created_on desc) rn from download_link dl join folder_details fd on dl.folder_id = fd.folder_id join ma_description md on md.id = fd.vessel_desc where dl.logged_in_status = :logged_in_status and  fd.created_on > CAST(TO_DATE(:datetostart,'DD-MM-YY')AS TIMESTAMP ) order by  fd.created_on desc) where rn between :pageStart  and :pageEnd";
	
	public static final String ARCHIVE_DOWNLOADED_FILE_GOING_TO_DELETE = "select * from (select FILE_NAME as FILENAME , F_ID AS ID ,ARCHIVED_DATE AS ARCHIEVEDON, (ARCHIVED_DATE+60)AS WILLBEDELETEDON, CREATED_BY as SHAREDBY, row_number()  over (order by archived_date desc) rn from uploaded_files where user_id = :user_id and  isarchive = :isarchive and isdeleted = :isdeleted and ((archived_date+60) between CAST(TO_DATE(:dateFrom,'DD-MM-YY')AS TIMESTAMP) and CAST(TO_DATE(:dateUpto,'DD-MM-YY')AS TIMESTAMP) ) and folder_id  is not null ) where rn between :pageStart and :pageEnd";
	
	
}