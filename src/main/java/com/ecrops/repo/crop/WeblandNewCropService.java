package com.ecrops.repo.crop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.dto.crop.response.WeblandLandData;
import com.ecrops.dto.webland.LandRecord;
import com.ecrops.dto.webland.WeblandCropData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class WeblandNewCropService {
	
	

	@PersistenceContext
	private EntityManager entityManager;

	
	

	public boolean checkRecordIsAvailableInDatabase(int vCode, String season, int cropYear) {

		String downtab = "ecrop" + cropYear + "." + "verify_datadownload";

		String QRY_GET_RECORDS_CNT = "select wb_cnt as cnt from " + downtab + " where  cr_vcode= ? "
				+ "and cr_year= ? and cr_season= ?";

		Query query = entityManager.createNativeQuery(QRY_GET_RECORDS_CNT);
		query.setParameter(1, vCode);
		query.setParameter(2, cropYear);
		query.setParameter(3, season);

		List<Object> results = query.getResultList();
		if (null != results && !results.isEmpty() && null != results.get(0)) {
			return ((int) results.get(0)) > 0;
		}
		return false;
	}

	@Transactional
	public int checkInsertAndWeblandCropDetails(String userId, String ipAddress, String wbdcode, String wbmcode,
			String wbvcode, String season, int cropYear, int sesdcode, int sesmcode) {
		
		if (wbdcode.length() == 1) {
			wbdcode = "0" + wbdcode;
		}

		String tableName = "pattadarmast_wb_partition_" + season + wbdcode + cropYear;

		
		String tbname = "ecrop" + cropYear + "." + tableName;
		
		String QRY_INS_CROP_DET = "INSERT INTO " + tbname + "("
				+ "cr_dist_code, cr_mand_code, cr_vcode, cr_farmeruid, farmername, "
				+ "fathername, mobileno, kh_no, cr_sno, tot_extent, "
				+ "dt_crt, occupname, occupfname, occup_extent, regno, sjointoccupant, "
				+ "cultivable_land, uncultivable_land, cropyear, season, status, dcode, "
				+ "mcode, cr_wsno, part_key, lpmno, land_nature_code, broadcategory_land_nature, broadcategory_land_class_code, "
				+ "broadcategory_land_class, subcategory_land_class_code, subcategory_land_class, land_use_nature, src_of_irrgation, "
				+ "acquisition_rights, acquisition_rights_code, notification_no, notification_sec, notification_date, "
				+ "prohibited, entry_date, dispute_desc, is_joint,datasource,issignedornot,code,resurvey )" + "VALUES (?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, " + "NOW(), ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ? , ?, " + "?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?,?,?,? )";
		

		String downtab = "ecrop" + cropYear + "." + "verify_datadownload";
		String updateQuery = "UPDATE " + downtab + " SET wb_cnt = ?, wbland = 'Y', dt_wbland = now() "
				+ "WHERE cr_dist_code = ? AND cr_mand_code = ? AND cr_vcode = ? AND cr_year = ? AND cr_season = ?";

		int totalCount = 0;
		List<WeblandLandData> weblandCropData = getWeblandCropData(wbvcode);
		
		System.out.println(" Webland api Response JavaObject  :---->"+weblandCropData);

		if (weblandCropData != null && !weblandCropData.isEmpty()) {
		    for (WeblandLandData data : weblandCropData) {
		        try {
		            String status = "";
		            if (data.getExtent() == 0.0 || data.getTotalExtent() == 0.0 || data.getExtent() > data.getTotalExtent() || data.getOccupantFatherName().isEmpty() || data.getFatherName().isEmpty() || data.getTotalExtent() < 0) {
		                status = "N";
		            } else {
		                status = "Y";
		            }

		            String isDigitallySigned = "";
		            if (data.getLpmNo()!=null) {
		                isDigitallySigned = data.getIsSignedOrNot().equalsIgnoreCase("Yes") ? "Y" : "N";
		            } else {
		                isDigitallySigned = data.getIsSignedOrNot();
		            }

		            double mobileNo = 0;
		            if (data.getMobileNo() != null && data.getMobileNo().length() == 10) {
		                mobileNo = Double.parseDouble(data.getMobileNo());
		            }

		            int regno = data.getRegNo() == null || data.getRegNo().isEmpty() ? 0 : Integer.parseInt(data.getRegNo());

		            String singleJointOccupantStr = data.getSingleJointOccupant();
		            int singleJointOccupant = 0;

		            if (singleJointOccupantStr != null) {
		                singleJointOccupantStr = singleJointOccupantStr.trim();
		                if (!singleJointOccupantStr.isEmpty()) {
		                    try {
		                        singleJointOccupant = Integer.parseInt(singleJointOccupantStr);
		                    } catch (NumberFormatException e) {
		                        e.printStackTrace();
		                    }
		                }
		            }

		            
		            String resurvey="";
		            if(data.getLpmNo()!=null) {
		            	resurvey="YES";
		            }else {
		            	resurvey="No";
		            }
		            
		             String code="";
		            if (weblandCropData != null && !weblandCropData.isEmpty()) {
		            	code="100";
		            }
		            
		          
		            String baseSurveyNo = data.getBaseSurveyNo();
		            String lpmNo = data.getLpmNo();
		            int basesurveyNo = 0;

		            if (baseSurveyNo != null && !baseSurveyNo.isEmpty() && lpmNo == null) {
		                try {
		                    double doubleValue = Double.parseDouble(baseSurveyNo);

		                    basesurveyNo = (int) doubleValue;
		                } catch (NumberFormatException e) {
		                    e.printStackTrace();
		                }
		            }
		            
		            int insCount = entityManager.createNativeQuery(QRY_INS_CROP_DET)
		                    .setParameter(1, data.getDistrictCode())
		                    .setParameter(2, data.getMandalCode())
		                    .setParameter(3, data.getVillageCode())
		                    .setParameter(4, checkNull(data.getUid()))
		                    .setParameter(5, checkNull(data.getName() != null ? data.getName() : ""))
		                    .setParameter(6, checkNull(data.getFatherName() != null ? data.getFatherName() : ""))
		                    .setParameter(7, mobileNo)
		                    .setParameter(8, checkNull(Double.parseDouble(data.getKhatano())))
		                    .setParameter(9, data.getLpmNo() != null ? data.getLpmNo()  : data.getSurveyNo())
		                    .setParameter(10, data.getTotalExtent())
		                    .setParameter(11, checkNull(data.getOccupantName()))
		                    .setParameter(12, checkNull(data.getOccupantFatherName()))
		                    .setParameter(13, data.getExtent())
		                    .setParameter(14, regno)
		                    .setParameter(15, singleJointOccupant)
		                    .setParameter(16, data.getCultivatedLand() != null ? Double.parseDouble(data.getCultivatedLand()) : 0.0)
		                    .setParameter(17, data.getUncultivatedLand() != null ? Double.parseDouble(data.getUncultivatedLand()) : 0.0)
		                    .setParameter(18, cropYear)
		                    .setParameter(19, season)
		                    .setParameter(20, status)
		                    .setParameter(21, sesdcode)
		                    .setParameter(22, sesmcode)
		                    .setParameter(23, data.getLpmNo() != null ? Integer.parseInt(data.getLpmNo()) : basesurveyNo  )
		                    .setParameter(24, season + wbdcode + cropYear)
		                    .setParameter(25, checkNull(data.getLpmNo()!= null ? data.getLpmNo() : "0"))
		                    .setParameter(26, data.getLandNatureCode())
		                    .setParameter(27, checkNull(data.getBroadCategoryLandNature()))
		                    .setParameter(28, data.getBroadCategoryLandClassificationCode())
		                    .setParameter(29, checkNull(data.getBroadCategoryLandClassification()))
		                    .setParameter(30, data.getSubCategoryLandClassificationCode())
		                    .setParameter(31, checkNull(data.getSubCategoryLandClassification()))
		                    .setParameter(32, checkNull(data.getLandUseNature()))
		                    .setParameter(33, checkNull(data.getSourceOfIrrigation()))
		                    .setParameter(34, checkNull(data.getAcquisitionRights()))
		                    .setParameter(35, data.getAcquisitionRightsCode() != 0 ? data.getAcquisitionRightsCode() : 0)
		                    .setParameter(36, checkNull(data.getNotificationNumber()))
		                    .setParameter(37, checkNull(data.getNotificationSection()))
		                    .setParameter(38, checkNull(data.getNotificationDate()))
		                    .setParameter(39, checkNull(data.getProhibited()))
		                    .setParameter(40, checkNull(data.getEntryDate()))
		                    .setParameter(41, checkNull(data.getDisputeDescription()))
		                    .setParameter(42, checkNull(data.getIsJoint()))
		                    .setParameter(43, checkNull(data.getDataSource()))
		                    .setParameter(44, isDigitallySigned)
		                    .setParameter(45, code)
		                    .setParameter(46,resurvey)
		                    .executeUpdate();
		            System.out.println(" WebLand totalRecordCount----->"+totalCount);
		            totalCount += insCount;
		        } catch (Exception e) {
		            e.printStackTrace();
		            System.err.println("Error inserting data: " + data);
		        }
		    }

		    try {
		        if (totalCount > 0) {
		            Query insertCdown = entityManager.createNativeQuery(updateQuery);
		            insertCdown.setParameter(1, totalCount);
		            insertCdown.setParameter(2, Integer.parseInt(wbdcode));
		            insertCdown.setParameter(3, Integer.parseInt(wbmcode));
		            insertCdown.setParameter(4, Integer.parseInt(wbvcode));
		            insertCdown.setParameter(5, cropYear);
		            insertCdown.setParameter(6, season);
		            insertCdown.executeUpdate();
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.err.println("Error updating download table.");
		    }
		} else {
		    Query insertCdown = entityManager.createNativeQuery(updateQuery);
		    insertCdown.setParameter(1, 0);
		    insertCdown.setParameter(2, Integer.parseInt(wbdcode));
		    insertCdown.setParameter(3, Integer.parseInt(wbmcode));
		    insertCdown.setParameter(4, Integer.parseInt(wbvcode));
		    insertCdown.setParameter(5, cropYear);
		    insertCdown.setParameter(6, season);
		    insertCdown.executeUpdate();
		}

		return totalCount;
	}
	
	
	private Object checkNull(Object value) {
		return value != null ? value : "";
	}

	
	public List<WeblandLandData> getWeblandCropData(String wbvcode) {
		String weblandDetails = getWeblandData(wbvcode);
		ObjectMapper mapper = new ObjectMapper();
		List<WeblandLandData> cropList = new ArrayList<>();
		try {

			JsonNode rootNode = mapper.readTree(weblandDetails);
			JsonNode dataNode = rootNode.path("Data");
			
			if (dataNode.isArray()) {
				cropList = mapper.readValue(dataNode.toString(), new TypeReference<List<WeblandLandData>>() {
				});
			} else {

				System.err.println("Expected an array but got: " + dataNode.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cropList;
	}

	
	public static String getWeblandData(String wbvcode) {
		try {
			String token = getKeyValue();
			
			
			//URL url = new URL("http://103.129.75.175/Finalror/AOAV1/Agriculture");
			URL url = new URL("https://weblandapi.ap.gov.in/prod/AOAV1/Agriculture");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Authorization", "Bearer " + token);
			

			String inputData = "{\n" + "    \"Ptype\":\"RC\",\n" + "    \"VillageCode\":\"" + wbvcode + "\",\n"
					+ "    \"UserName\":\"ecrop\",\n" + "    \"Upassword\":\"EcROP@2024\"\n" + "}";
			
//			String inputData = "{\n" + "    \"Ptype\":\"RC\",\n" + "    \"VillageCode\":\"" + wbvcode + "\",\n"
//					+ "    \"UserName\":\"NICTEST\",\n" + "    \"Upassword\":\"NICtEST@123\"\n" + "}";
//			

			try (OutputStream os = connection.getOutputStream()) {
				byte[] inputBytes = inputData.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				os.write(inputBytes, 0, inputBytes.length);
			}

			int responseCode = connection.getResponseCode();
			
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + responseCode);
			}

			StringBuilder response = new StringBuilder();
			
			
			
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), java.nio.charset.StandardCharsets.UTF_8))) {
				String line;
				while ((line = br.readLine()) != null) {
					response.append(line);
					
				}
			
			}
         
			System.out.println(" Webland api Response  :---->"+response.toString());

			return response.toString();
		
		} catch (Exception e) {
			throw new RuntimeException("Error fetching Webland data", e);
		}
		
	}

	public static String getKeyValue() {
		try {
			//URL url = new URL("http://103.129.75.175/Finalror/api/Token");
			URL url = new URL("https://weblandapi.ap.gov.in/prod/api/token");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			
			String input = "{\n" + "    \"un\":\"ecrop\",\n" + "    \"up\":\"EcROP@2024\"\n" + "}";
			//String input = "{\n" + "    \"un\":\"NICTEST\",\n" + "    \"up\":\"NICtEST@123\"\n" + "}";

			try (OutputStream os = connection.getOutputStream()) {
				byte[] inputBytes = input.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				os.write(inputBytes, 0, inputBytes.length);
			}

			int responseCode = connection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + responseCode);
			}

			StringBuilder response = new StringBuilder();
			try (BufferedReader in = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), java.nio.charset.StandardCharsets.UTF_8))) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			}

			System.out.println("Token API Response: " + response.toString());

			JSONObject json = new JSONObject(response.toString());

			if (json.has("Data")) {
				
				System.out.println("api token key :---->"+json.getString("Data"));
				
				return json.getString("Data");
			} else {
				throw new RuntimeException("Token not found in response: " + response.toString());
			}
		} catch (Exception e) {
			throw new RuntimeException("Error fetching API key", e);
		}
	}

}
