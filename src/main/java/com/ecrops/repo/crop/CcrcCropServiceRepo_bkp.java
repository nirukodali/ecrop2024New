package com.ecrops.repo.crop;

import com.ecrops.dto.webland.CCRCCropData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
@Transactional

public class CcrcCropServiceRepo_bkp {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public int checkRecordIsAvailableInDatabase(int villageCode, String season, int cropYear) {
		try {

			String downtab = "ecrop" + cropYear + "." + "verify_datadownload";
			String QRY_GET_RECORDS_CNT = "select ccrc_cnt as count from " + downtab
					+ " where  cr_vcode=? and cr_year=? and cr_season=?";

			Query query = entityManager.createNativeQuery(QRY_GET_RECORDS_CNT);
			query.setParameter(1, villageCode);
			query.setParameter(2, cropYear);
			query.setParameter(3, season);

			Number result = (Number) query.getSingleResult();
			int recordCount = (result != null) ? result.intValue() : 0;

			System.out.println("Record count found: " + recordCount);

			return recordCount;
		} catch (NoResultException e) {
			System.out.println("No records found for the specified criteria.");
			return 0;
		}
	}

	@Transactional
	public int checkInsertAndViewCccrcCropDetails(int activeYear, String userId, String ipAddress, int wDCode,
			int villageCode, int wMCode, String season, int cropYear, int mandalCode, int districtCode)
			throws Exception {
	int	 bookIns = 0;
		int alreadyRecordsCount= checkRecordIsAvailableInDatabase(villageCode,  season,  cropYear);
		if(alreadyRecordsCount<=0) {		
		int cdowndet = 0, diffIns = 0, totccrc = 0;
		int existingCnt = 0;
		boolean newVcode = false;

		String ccrcdetTab = "ccrc_details";
		String ccrcTempTab = "ccrc_details_temp";
		// String cccrdownloadTab = "ccrcdownloaddetails";
		String downtab = "ecrop" + cropYear + "." + "verify_datadownload";

		String pattmast = "pattadarmast_wb_partition_"; // pattadarmast_wb_partition_k232023

		String dcode = Integer.toString(wDCode);
		String cropyear = Integer.toString(cropYear);
		String activeyear = Integer.toString(activeYear);

		if (dcode.length() == 1) {
			dcode = "0" + dcode;

		}
		if (Integer.parseInt(activeyear.trim()) == Integer.parseInt(cropyear.trim())) {
			ccrcTempTab = "ecrop" + activeYear + "." + ccrcTempTab;
			ccrcdetTab = "ecrop" + activeYear + "." + ccrcdetTab;
			// cccrdownloadTab = "ecrop" + activeYear + "." + cccrdownloadTab;
			pattmast = "ecrop" + activeYear + "." + pattmast + season + dcode + cropYear;
		}

		int ccrcIns = insertCropDetailsFromWebland(dcode, villageCode, wMCode, activeYear, season, cropYear,districtCode,mandalCode);
		System.out.println("ccrcdetTab---------->"+ccrcdetTab);
		System.out.println("ccrcIns------->" + ccrcIns);

		if (ccrcIns > 0) {
			int wsnoccrcTemp = 0;

			String wholesrnoTempupd = "SELECT ecrop2024.update_wsno_regno_sjoint(:pattmast, :villageCode)";

			try {
				Query query = entityManager.createNativeQuery(wholesrnoTempupd);
				query.setParameter("pattmast", pattmast);
				query.setParameter("villageCode", villageCode);

				List<Object> result = query.getResultList(); //System.out.println("query::"+query);

				if (result != null && !result.isEmpty()) {
					Object singleResult = result.get(0);

					if (singleResult instanceof Number) {
						diffIns = ((Number) singleResult).intValue();

						System.out.println("diffIns-------->" + diffIns);

					} else {
						System.err.println("Unexpected result type: " + singleResult.getClass());
					}
				}
			} catch (Exception e) {
				// Log the error instead of printing the stack trace
				System.err.println("Error updating wsno: " + e.getMessage());
			}
		}

		if (diffIns >= 0) {

			String QRY_INS_RECORDS_INT = "", partkey = "";
			String wbdist = String.valueOf(wDCode);
			if (wbdist.length() == 1) {
				wbdist = "0" + wbdist;
			}
			String crbooking = "cr_booking_nwb";
			if (activeyear.equals(cropyear)) {
				crbooking = "ecrop" + activeYear + "." + crbooking;

			}
			System.out.println("crbooking---------->"+crbooking);
			if (diffIns > 0) {

				QRY_INS_RECORDS_INT = "INSERT INTO " + ccrcdetTab
						+ "( ccrcid, docid, wbdcode, dname, wbmcode, mname, vcode, vname, tenantfarmername, rid, khatano, surveyno, extent, "
						+ "enrolldate, enrollenddate, gender, dt_crt, caste, cr_year, cr_season, cultivable_land, uncultivable_land, tot_extent, pname, pfname, anubhavadar_extent, part_key, dcode, mcode, dtcreated, tenantfarmer_fathername, tenant_aadhaar, land_nature, "
						+ "anubhavadar_name, anubhavadar_fathername, cr_wsno,regno,sjointoccupant) "
						+ "SELECT ccrcid, docid, wbdcode, dname, wbmcode, mname, vcode, vname, tenantfarmername, rid, khatano, surveyno, extent, enrolldate, enrollenddate, gender, dt_crt, caste, cr_year, cr_season, cultivable_land, uncultivable_land, tot_extent, pname, pfname, anubhavadar_extent, part_key, dcode, mcode, dtcreated, tenantfarmer_fathername, tenant_aadhaar, land_nature, "
						+ "anubhavadar_name, anubhavadar_fathername, cr_wsno,regno,sjointoccupant FROM ecrop2024.ccrc_details_temp WHERE vcode=? AND (ccrcid,vcode,khatano,surveyno,extent) "
						+ "NOT IN (SELECT ccrcid,vcode,khatano,surveyno,extent FROM ecrop2024.ccrc_details WHERE vcode=?)";

				Query query = entityManager.createNativeQuery(QRY_INS_RECORDS_INT);
				query.setParameter(1, villageCode);
				query.setParameter(2, villageCode);

				int tmptoccrIns = query.executeUpdate();

				System.out.println("tmptoccrIns------->" + tmptoccrIns);

				String QRY_INS_RECORDS_INTCRB = "INSERT INTO " + crbooking
						+ "( ccrcid, cr_dist_code, cr_mand_code, cr_vcode, occupname, kh_no, cr_sno, occupant_extent,tot_extent, "
						+ "  soc_category,data_src, cr_year,cr_season,owner_tenant,cr_tr_i_ext,cr_tr_d_ext,part_key,oc_name,oc_fname,dcode,"
						+ " mcode,occupfname,cr_farmeruid,anubhavadar_name,cultivator_type,cr_wsno)  "
						+ " select ccrcid,wbdcode, wbmcode,  vcode, tenantfarmername, khatano, surveyno, extent,tot_extent,  caste,'C',cr_year,cr_season,'T',"
						+ " 0,0,part_key,pname,pfname,dcode,mcode,tenantfarmer_fathername,tenant_aadhaar,anubhavadar_name,'C',cr_wsno "
						+ "  from " + ccrcdetTab
						+ " where cr_wsno is not null and enrollenddate::::date>=now()::::date and vcode=? and cr_year=? and cr_season=?  and (CAST(ccrcid as text)||CAST(khatano as text)||CAST(surveyno as text)||CAST(vcode as text)) not in "
						+ " ( select CAST(ccrcid as text)||CAST(kh_no as text)||CAST(cr_sno as text)||CAST(cr_vcode as text ) from "
						+ crbooking + " where  data_src='C' and cr_vcode=? and cr_year=? and cr_season=? )";

				Query queryCrb = entityManager.createNativeQuery(QRY_INS_RECORDS_INTCRB);
				queryCrb.setParameter(1, villageCode);
				queryCrb.setParameter(2, cropYear);
				queryCrb.setParameter(3, season);
				queryCrb.setParameter(4, villageCode);
				queryCrb.setParameter(5, cropYear);
				queryCrb.setParameter(6, season);

				bookIns = queryCrb.executeUpdate();

				System.out.println("bookIns------->" + bookIns);

				String qry4 = "DELETE FROM " + ccrcTempTab + " WHERE vcode=? AND cr_year=? AND cr_season=? ";

				Query deleteQuery = entityManager.createNativeQuery(qry4);
				deleteQuery.setParameter(1, villageCode);
				deleteQuery.setParameter(2, cropYear);
				deleteQuery.setParameter(3, season);

				int delTemp = deleteQuery.executeUpdate();

				System.out.println("delTemp------->" + delTemp);
				
								String updateQuery = "UPDATE " + downtab + " SET ccrc_cnt = ?, ccrc = 'Y', dt_ccrc = now() "
						+ " WHERE cr_dist_code = ? AND cr_mand_code = ? AND cr_vcode = ? AND cr_year = ? AND cr_season = ?";

				Query insertCdown = entityManager.createNativeQuery(updateQuery);
				insertCdown.setParameter(1, bookIns);
				insertCdown.setParameter(2, wDCode);
				insertCdown.setParameter(3, wMCode);
				insertCdown.setParameter(4, villageCode);
				insertCdown.setParameter(5, cropYear);
				insertCdown.setParameter(6, season);
				cdowndet = insertCdown.executeUpdate();

				System.out.println(" inseted bookins count----->" + bookIns);

				System.out.println("cdowndet----->" + cdowndet);
				
				System.out.println("bookIns:" + bookIns);
				totccrc = existingCnt + bookIns;
				System.out.println("totccrc::" + totccrc + "--extcnt:" + existingCnt + "--diffIns:" + diffIns);
			//}

					}
			}

		}
		return bookIns;

	}
//			String qry6 = "SELECT ccrc_cnt as prevCnt FROM " + downtab
//					+ " WHERE cr_vcode=? AND cr_year=? AND cr_season=? ";
//
//			Query countQuery = entityManager.createNativeQuery(qry6);
//			countQuery.setParameter(1, villageCode);
//			countQuery.setParameter(2, cropYear);
//			countQuery.setParameter(3, season);
//
//			System.out.println("COUNTQUERY------->" + countQuery);
//
//			try {
//				Integer result = (Integer) countQuery.getSingleResult();
//				System.out.println("result-----" + result);
//				existingCnt = result;
//			} catch (NoResultException e) {
//				newVcode = true;
//			}
//
//			if (newVcode) {
//
////				INSERT INTO ecrop2024.verify_datadownload(
////						cr_dist_code, cr_mand_code, cr_vcode, dcode, mcode, cr_year, cr_season, wbland, wb_cnt, dt_wbland, ccrc, ccrc_cnt, dt_ccrc, efish, efish_cnt, dt_efish, rofr, rofr_cnt, dt_rofr, peri_data, peri_cnt, dt_peri_data, dt_crt, crt_user, clientip, lgddcode, lgdmcode, lgdvcode)
////						VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
//
//				String QRY_INS_USER_DET = "INSERT INTO " + downtab
//						+ "( cr_vcode, userId, ccrc_cnt, dt_ccrc, ipaddress, ccrc, cr_year, cr_season)\n"
//						+ "VALUES (?, ?, ?, NOW(), ?, ?, ?, ?)";
//
//				Query insertCdown = entityManager.createNativeQuery(QRY_INS_USER_DET);
//				insertCdown.setParameter(1, villageCode);
//				insertCdown.setParameter(2, userId);
//				insertCdown.setParameter(3, ccrcIns);
//				insertCdown.setParameter(4, ipAddress);
//				insertCdown.setParameter(5, "Y");
//				insertCdown.setParameter(6, cropYear);
//				insertCdown.setParameter(7, season);
//
//				cdowndet = insertCdown.executeUpdate();
//
//				System.out.println("cdowndet---------->" + cdowndet);
//			} else {
//				diffIns = bookIns;
//				totccrc = existingCnt + diffIns;
//
//				System.out.println("totccrc------->" + totccrc);

//}			

			
//		System.out.println("bookIns:" + bookIns + ", ccrcIns----->" + ccrcIns + ", diffIns------->" + diffIns
//				+ ", cdowndet---->" + cdowndet);
//
//		System.out.println("bookIns + \"@\" + totccrc + \"\";:-------->" + bookIns + "@" + totccrc + "");

	

	@Transactional
	public int insertCropDetailsFromWebland(String dcode, int villageCode, int wMCode, int activeYear, String season,
			int cropYear, int sesdcode,int sesmcode) {

		String ccrcTempTab = "ccrc_details_temp";
//		String dcode = Integer.toString(wDCode);
		String cropyear = Integer.toString(cropYear);

		String activeyear = Integer.toString(activeYear);

		if (dcode.length() == 1) {
			dcode = "0" + dcode;

		}
		if (Integer.parseInt(activeyear.trim()) == Integer.parseInt(cropyear.trim())) {
			ccrcTempTab = "ecrop" + activeYear + "." + ccrcTempTab;
		}

		String QRY_INS_CROP_DET = "INSERT INTO " + ccrcTempTab + " (ccrcid, docid, wbdcode, dname, wbmcode, "
				+ "mname, vcode, vname, tenantfarmername, khatano, " + "surveyno, extent, enrolldate, caste, cr_year,"
				+ " cr_season, tot_extent, anubhavadar_extent, part_key,dcode,"
				+ "  mcode,tenantfarmer_fathername, tenant_aadhaar, enrollenddate, land_nature,"
				+ "gender, pname, pfname, anubhavadar_name, anubhavadar_fathername) " + "VALUES " + "(?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, " + "?, ?, to_date(cast(? as TEXT), 'YYYY-MM-DD'), ?, ?, " + "?, ?, ?, ?, ?,"
				+ " ?, ?, ?, to_date(cast(? as TEXT), 'YYYY-MM-DD'), ?," + " ?, ?,?,?,?)";

		List<CCRCCropData> cropDataList = getCcrcCropData(String.valueOf(dcode), String.valueOf(wMCode),
				String.valueOf(villageCode));

		int ccrcIns = 0;

		for (CCRCCropData data : cropDataList) {
			try {
				String gender = mapGender(data.getTenantGender());

				int casteCode = getCasteCode(data.getCaste());
				String anubhavadarName = mapString(data.getWebland_Occupant_Name());
				String anubhavadarFname = mapString(data.getWebland_Occupant_Father_Name());

				double weblandOccupantExt = Double.parseDouble(data.getWebland_occupant_extent());
				double weblandTotalExt = Double.parseDouble(data.getWebland_total_extent());

				if (weblandOccupantExt > weblandTotalExt || weblandOccupantExt == 0 || weblandTotalExt == 0) {
					continue;
				}

				ccrcIns += entityManager.createNativeQuery(QRY_INS_CROP_DET)
						.setParameter(1, Integer.parseInt(data.getCcrc_id())).setParameter(2, data.getDoc_Id())
						.setParameter(3, Integer.parseInt(data.getDistrict())).setParameter(4, data.getDName())
						.setParameter(5, Integer.parseInt(data.getMandal()))

						.setParameter(6, data.getMName()).setParameter(7, Integer.parseInt(data.getVillage()))
						.setParameter(8, data.getVName()).setParameter(9, data.getNameOf_TheTenantFarmer())
						.setParameter(10, Integer.parseInt(data.getKhata_no()))

						.setParameter(11, data.getSurvey_No())
						.setParameter(12, Double.parseDouble(data.getCard_Extent()))
						.setParameter(13, data.getEnroll_Date()).setParameter(14, casteCode).setParameter(15, cropYear)

						.setParameter(16, season).setParameter(17, weblandTotalExt).setParameter(18, weblandOccupantExt)
						.setParameter(19, season + dcode + cropYear).setParameter(20, sesdcode)

						.setParameter(21, sesmcode).setParameter(22, data.getTenantFather())
						.setParameter(23, data.getTenant_Aadhar()).setParameter(24, data.getEnroll_End_Date())
						.setParameter(25, data.getLand_nature())

						.setParameter(26, gender).setParameter(27, data.getNameOf_TheLandOwner())
						.setParameter(28, data.getLandOwnerFather()).setParameter(29, anubhavadarName)
						.setParameter(30, anubhavadarFname).executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error inserting data: " + e.getMessage());
			}
		}

		return ccrcIns;
	}

	private String mapGender(String gender) {
		if (gender != null && !gender.isEmpty()) {
			if (gender.equalsIgnoreCase("Female")) {
				return "F";
			} else if (gender.equalsIgnoreCase("Male")) {
				return "M";
			} else if (gender.equalsIgnoreCase("Transgender")) {
				return "T";
			}
		}
		return "";
	}

	private int getCasteCode(String caste) {
		try {
			String queryCaste = "SELECT castecode FROM caste_mst WHERE caste = :caste";

			if (isSpecialCaste(caste)) {
				if (caste.trim().equalsIgnoreCase("OC")) {
					caste = "General";
					System.out.println("caste" + caste);
				} else if (caste.trim().equalsIgnoreCase("Minority")) {
					caste = "Minorities";
				}
			}

			Query query = entityManager.createNativeQuery(queryCaste);
			query.setParameter("caste", caste);

			List<Object> resultList = query.getResultList();

			if (resultList.isEmpty()) {
				System.err.println("No result found for caste: " + caste);
				return 0;
			}

			return Integer.parseInt(resultList.get(0).toString());
		} catch (Exception e) {
			System.err.println("Error while fetching caste code: " + e.getMessage());
			return 0;
		}
	}

	private boolean isSpecialCaste(String caste) {
		return caste.trim().equalsIgnoreCase("BC") || caste.trim().equalsIgnoreCase("SC")
				|| caste.trim().equalsIgnoreCase("ST") || caste.trim().equalsIgnoreCase("OC")
				|| caste.trim().equalsIgnoreCase("Minority") || caste.trim().equalsIgnoreCase("NA");
	}

	private String mapString(String value) {
		return (value == null) ? "NA" : value.toString();
	}

	@Transactional
	public List<CCRCCropData> getCcrcCropData(String dCode, String mCode, String villageCode) {
		try {
			String ccrcDetails = fetchCcrcDetailsFromURL(dCode, mCode, villageCode);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(ccrcDetails, new TypeReference<List<CCRCCropData>>() {
			});
		} catch (RuntimeException e) {
			handleConnectionTimeoutException(e);
			System.out.println("Connection timeout: Unable to connect to the server");
			e.printStackTrace();
			return new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public String fetchCcrcDetailsFromURL(String dCode, String mCode, String villageCode) {
		String status="";
		String url = "http://ccrc.ap.gov.in/ccrcrest/CCRCServiceVillageActive";
		HttpURLConnection connection = null;
		BufferedReader reader = null;

		try {
			URL apiUrl = new URL(url);
			connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setConnectTimeout(120000);

			// Set request headers
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// Set request parameters
			String parameters = "Username=Administrator&Password=Administrator@789&DistrictCode=" + dCode
					+ "&MandalCode=" + mCode + "&VillageCode=" + villageCode;

			// Write parameters to the request
			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
				wr.write(parameters.getBytes(StandardCharsets.UTF_8));
			}

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;

				while ((line = reader.readLine()) != null) {
					response.append(line);
				}

				return response.toString();
			} else {
				System.out.println("Error: " + responseCode);
				 status="URL Response error" ;
				throw new RuntimeException("URL Response error. " + responseCode);
				
			}
		} catch (java.net.ConnectException e) {
			 status="Connection timed out: Unable to connect to the server" ;

			throw new RuntimeException("Connection timed out: Unable to connect to the server", e);
		} catch (Exception e) {
			 status="Error reading URL. Please Download Again" ;

			throw new RuntimeException("Error reading URL. Please Download Again", e);
			
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
				 status="Error closing URL reader" ;

				throw new RuntimeException("Error closing URL reader", e);
			}

			if (connection != null) {
				connection.disconnect();
			}
		}
		
	}

	private void handleConnectionTimeoutException(RuntimeException e) {
		
		System.out.println("Connection timeout: Unable to connect to the server");
		e.printStackTrace();
	}

	public static String decodeUnicodeEscapeSequences(String input) {
		return StringEscapeUtils.unescapeJava(input);
	}

}