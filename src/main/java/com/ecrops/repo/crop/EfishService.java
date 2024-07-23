package com.ecrops.repo.crop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.dto.crop.request.EfishLandData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Service
@Transactional
public class EfishService {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Transactional
	public int checkRecordIsAvailableInDatabase(int villageCode, String season, int cropYear) {
//		if (villageCode <= 0) {
//			throw new IllegalArgumentException("Village code must be a positive integer.");
//		}
		try {

			if (season == null || season.isEmpty()) {
				throw new IllegalArgumentException("Season must not be empty.");
			}

			String downtab = "ecrop" + cropYear + "." + "verify_datadownload";
			String QRY_GET_RECORDS_CNT = "SELECT efish_cnt AS count FROM " + downtab
					+ " WHERE cr_vcode = ? AND cr_year = ? AND cr_season = ?";

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

	
	public int insertEfishDetails(String ipaddress, int cropyear, String season, String vcode, int wbdcode, int wbmcode,int sesdcode, int sesmcode) {
		
	    String insertQuery = " INSERT INTO ecrop2024.cr_details_efish_2024 "
	            + "(cr_sno, total_extent, uncultivated_land, cultivatable_land, land_nature,"
	            + " tax, land_classification, water_source, ayakat_extent, kh_no,"
	            + " pattadar_name, pattadar_father_name, base_survey_no, occupant_name, occupant_father_name,"
	            + " occupant_extent, enjoyment_nature, dist_code, dist_name, mand_code,"
	            + " mand_name, cr_vcode, village_name, aqua_zone_y_n, webland_id, mapped_extent,allowable_ext) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    String downtab = "ecrop" + cropyear + "." + "verify_datadownload";

	    String QRY_INS_USER_DET = "INSERT INTO " + downtab
	            + "(cr_dist_code, cr_mand_code, cr_vcode,dcode,mcode, cr_year, cr_season, "
	            + "   efish_cnt, efish,  dt_efish, clientip) " + "VALUES (?, ?, ?, ?, ?, ?,  ?, ?, 'Y',now(), ? ) ";

	    String token = getToken(); // Get token from eFish API

	    List<EfishLandData> cropDataList = null;
	    int efishcount = 0;

	    try {
	        cropDataList = getEfishServiceData(token, vcode);
	        // Fetch crop data from eFish API
	        System.out.println("cropDataList--->" + cropDataList);
System.out.println("efish ins:"+insertQuery);
	        if (cropDataList != null && !cropDataList.isEmpty()) {
	            for (EfishLandData data : cropDataList) {
	                // Execute insert query for each crop data
	            	 double occupantExt=Double.parseDouble(data.getOccupantExtent());
	            	 double mappedExt=  data.getMappedExtent();
	            	 double allowableExt=(occupantExt-mappedExt);
	            	 
	                efishcount += entityManager.createNativeQuery(insertQuery)
	                        .setParameter(1, data.getSurveyNo())
	                        .setParameter(2, Double.parseDouble(data.getTotalExtent()))
	                        .setParameter(3, Double.parseDouble(data.getUncultivatedLand()))
	                        .setParameter(4, Double.parseDouble(data.getCultivatableLand()))
	                        .setParameter(5, data.getLandNature())
	                        .setParameter(6, data.getTax())
	                        .setParameter(7, data.getLandClassification())
	                        .setParameter(8, data.getWaterSource())
	                        .setParameter(9, Double.parseDouble(data.getAyakatExtent()))
	                        .setParameter(10, data.getKhataNo())
	                        .setParameter(11, data.getPattadarName())
	                        .setParameter(12, data.getPattadarFatherName())
	                        .setParameter(13, data.getBaseSurveyNo())
	                        .setParameter(14, data.getOccupantName())
	                        .setParameter(15, data.getOccupantFatherName())
	                        .setParameter(16, Double.parseDouble(data.getOccupantExtent()))
	                        .setParameter(17, data.getEnjoymentNature())
	                        .setParameter(18, data.getDistCode())
	                        .setParameter(19, data.getDistName())
	                        .setParameter(20, data.getMandCode())
	                        .setParameter(21, data.getMandName())
	                        .setParameter(22, data.getVillageCode())
	                        .setParameter(23, data.getVillageName())
	                        .setParameter(24, data.getAquaZoneStatus())
	                        .setParameter(25, data.getWebLandId())
	                        .setParameter(26, data.getMappedExtent())
	                        .setParameter(27, allowableExt)
	                        .executeUpdate(); 
	                
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error inserting data: " + e.getMessage());
	        throw new RuntimeException("Error inserting data", e);
	    }

	    try {
	        if (efishcount > 0) {
	            entityManager.createNativeQuery(QRY_INS_USER_DET)
	                    .setParameter(1, wbdcode)
	                    .setParameter(2, wbmcode)
	                    .setParameter(3, Integer.parseInt(vcode))
	                    .setParameter(4, sesdcode)
	                    .setParameter(5, sesmcode)
	                    .setParameter(6, cropyear)
	                    .setParameter(7, season)
	                    .setParameter(8, efishcount)
	                    .setParameter(9, ipaddress)
	                    .executeUpdate();
	        } else {
	            // If cropDataList is null or empty, insert efishcount as 0
	            entityManager.createNativeQuery(QRY_INS_USER_DET)
	                    .setParameter(1, wbdcode)
	                    .setParameter(2, wbmcode)
	                    .setParameter(3, Integer.parseInt(vcode))
	                    .setParameter(4, sesdcode)
	                    .setParameter(5, sesmcode)
	                    .setParameter(6, cropyear)
	                    .setParameter(7, season)
	                    .setParameter(8, 0)
	                    .setParameter(9, ipaddress)
	                    .executeUpdate();
	        }
	    } catch (Exception e) {
	        System.out.println("Error inserting user details: " + e.getMessage());
	        throw new RuntimeException("Error inserting user details", e);
	    }

	    return efishcount;
	}
	
	// Method to fetch crop data from eFish API

	@Transactional
	public List<EfishLandData> getEfishServiceData(String accessToken, String vcode) {
		try {
			String efishData = fetchEfishServiceData(accessToken,vcode);
			ObjectMapper mapper = new ObjectMapper();

			JsonNode rootNode = mapper.readTree(efishData);

			JsonNode resultNode = rootNode.get("result");

			List<EfishLandData> efishLandDataList = mapper.readValue(resultNode.toString(),
					new TypeReference<List<EfishLandData>>() {
					});
	        System.out.println("efishDataList:---->"+efishLandDataList);

			return efishLandDataList;
		} catch (IOException e) {
			System.err.println("Error reading eFish service data: " + e.getMessage());
			throw new RuntimeException("Error reading eFish service data", e);
		}
	}

	public static String getToken() {
        String apiUrl = "https://ematsyakar.com/efish/api/webland/get-token";
        StringBuilder response = new StringBuilder();
        String accessToken = null;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonInputString = "{\"user_name\":\"webland_user\", \"password\":\"CoF#2024\"}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            Gson gson = new Gson();
            Map<String, Object> responseMap = gson.fromJson(response.toString(), Map.class);
            Object accessTokenObj = responseMap.get("user");
            if (accessTokenObj instanceof Map) {
                Map<String, Object> user = (Map<String, Object>) accessTokenObj;
                accessToken = (String) user.get("access_token");
            }
            connection.disconnect();
        } catch (IOException e) {
            System.out.println("Error getting token: " + e.getMessage());
            throw new RuntimeException("Error getting token", e);
        }
        System.out.println("efish accessToken---->"+accessToken);
        return accessToken;
    }

    public static String fetchEfishServiceData(String accessToken, String vcode) throws IOException {
        String apiUrl = "https://ematsyakar.com/efish/api/webland/village_survey_data";
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Set content type to application/json
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            // Construct the request body
            String requestBody = "{\"PUSERNAME\":\"EFISH\",\"VILLAGE_CODE\":\"" + vcode + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            System.out.println("Error fetching eFish service data: " + e.getMessage());
            throw e;
        }
        System.out.println("EfishData:---->"+response.toString());
        return response.toString();
        
    }
}
