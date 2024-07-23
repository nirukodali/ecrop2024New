package com.ecrops.repo.crop;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.dto.webland.LandRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class ROFRServiceRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RofrLandRecordRepository recordRepository;

	public int checkRecordIsAvailableInDatabase(int lgdvcode, String season, int cropyear) {
		try {
			String downtab = "ecrop" + cropyear + ".verify_datadownload";
			String QRY_GET_RECORDS_CNT = "select rofr_cnt as cnt from " + downtab + " where  lgdvcode= ?  "
					+ "and cr_year= ? and cr_season= ? ";
			Query query = entityManager.createNativeQuery(QRY_GET_RECORDS_CNT);
			query.setParameter(1, lgdvcode);
			query.setParameter(2, cropyear);
			query.setParameter(3, season);
			Number result = (Number) query.getSingleResult();
			int recordCount = (result != null) ? result.intValue() : 0;
			return recordCount;
		} catch (NoResultException e) {
			return 0;
		}
	}

	@Transactional
	public int insertROFRLandRecords(String lgddcode, String lgdmcode, String lgdvcode, int wbvcode, int wbdcode,
	        int wbmcode, String key, String season, String sesdcode, String sesmcode, int cropyear, String userid,
	        String ipaddress) {
	    String tab = "rofr_2024_new";
	    String tab2 = "cr_booking_nwb";

	    tab2 = "ecrop" + cropyear + "." + tab2;
	    tab = "ecrop" + cropyear + "." + tab;

	    String QRY_INS_CROP_DET = "INSERT INTO " + tab + " (lgddcode, lgdmcode, rev_vill_code,"
	            + " season, owner_tenant, aadhaar, farmer_name, father_name,"
	            + " mobile, rofr_pattano_khno, survey_no, extent, cultivator_name, "
	            + " cultivator_fname, cultivator_extent, cultivable_land, uncultivable_land, dcode, "
	            + " mcode, wbmcode, wbdcode, wbvcode, beneficiaryid,  plotid ) " + " VALUES (?, ?, ?, ?, ?,"
	            + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ? )";
	    
	    String QRY_INS_CR_BOOKING_NWB = "INSERT INTO " + tab2 + " (part_key, cr_dist_code, cr_mand_code, cr_vcode, cr_year,"
	    		+ " cr_season, cr_farmeruid, owner_tenant, oc_name, oc_fname,"
	    		+ " kh_no, cr_sno, tot_extent, cr_tr_d_ext,  ipaddress,data_src ,"
	    		+ "occupant_extent,  dcode, mcode, occupname,"
	    		+ " occupfname, anubhavadar_name, anubhavadar_extent, dt_crt,cr_wsno)" + " VALUES (?, ?, ?, ?, ?,"
	    				+ " ?, ?, ?, ?, ?, "
	    				+ "?, ?, ?, ?, ?,'R',"
	    				+ " ?, ?, ?, ?,"
	    				+ " ?, ?, ?, NOW(),0)"; 

	    String downtab = "ecrop" + cropyear + "." + "verify_datadownload";

	    String updateQuery = "UPDATE " + downtab
	            + " SET rofr_cnt = ?, rofr = 'Y', dt_rofr = now() ,lgddcode= ?, lgdmcode= ?, lgdvcode= ? "
	            + " WHERE cr_dist_code = ? AND cr_mand_code = ? AND cr_vcode = ? AND cr_year = ? AND cr_season = ?";

	    List<LandRecord> cropDataList = getLandRecordData(lgddcode, lgdmcode, lgdvcode, key);
	    
	    if (cropDataList != null && !cropDataList.isEmpty()) {
	        for (LandRecord data : cropDataList) {
	            try {
	            	 Query pst = entityManager.createNativeQuery(QRY_INS_CR_BOOKING_NWB);
		                pst .setParameter(1, season+wbdcode+cropyear);
		                pst .setParameter(2, data.getDistCode());
		                pst.setParameter(3, data.getMandCode());
		                pst.setParameter(4, data.getVillageCode());
		                pst .setParameter(5, cropyear);
		                pst.setParameter(6, season);
		                pst .setParameter(7, data.getPattadarUid());
		                if(data.getOwnerTenant().equals("owner")) {
			                pst.setParameter(8, 'O');
		
		                }
		                else if(data.getOwnerTenant().equals("cultivator")) {
			                pst.setParameter(8, 'C');

		                }
		                pst.setParameter(9, data.getPname());
		                pst.setParameter(10, data.getPfname());
		                String khNoWithHyphen =data.getPkhatano();
		                String khNoWithoutHyphen = khNoWithHyphen.replace("-", "");
		                int numericKhNo = Integer.parseInt(khNoWithoutHyphen);
		                pst.setParameter(11, numericKhNo);
		                pst.setParameter(12, "ROFR-"+String.valueOf(data.getPlotId())+"-"+data.getPsurveyno());
		                pst .setParameter(13, Double.parseDouble(data.getTotalExtent()));
		                pst.setParameter(14, Double.parseDouble(data.getPextent()));
		                pst.setParameter(15, ipaddress);
		                pst.setParameter(16, Double.parseDouble(data.getCultivatableLand()));
		                pst.setParameter(17, Integer.parseInt(sesdcode));
		                pst.setParameter(18, Integer.parseInt(sesmcode));
		                pst.setParameter(19, data.getPname());
		                pst.setParameter(20, data.getPfname());
		                pst.setParameter(21, data.getPname());
		                pst.setParameter(22, Double.parseDouble(data.getCultivatableLand()));
		                pst.executeUpdate();
		                }
	         catch(Exception e) {
	        	 System.out.println(e);
	        	 }
	            }
	        }

	   	    
	    int totalCount = 0;
	    if (cropDataList != null && !cropDataList.isEmpty()) {
	        for (LandRecord data : cropDataList) {
	            try {
	                int count = entityManager.createNativeQuery(QRY_INS_CROP_DET)
	                        .setParameter(1, Integer.parseInt(data.getLgddCode()))
	                        .setParameter(2, Integer.parseInt(data.getLgdmCode()))
	                        .setParameter(3, Integer.parseInt(data.getLgdVcode()))
	                        .setParameter(4, season)
	                        .setParameter(5, data.getOwnerTenant())
	                        .setParameter(6, data.getPattadarUid())
	                        .setParameter(7, data.getPname())
	                        .setParameter(8, data.getPfname())
	                        .setParameter(9, data.getPmobileno())
	                        .setParameter(10, data.getPkhatano())
	                        .setParameter(11, data.getPsurveyno())
	                        .setParameter(12, Double.parseDouble(data.getPextent()))
	                        .setParameter(13, data.getOccuptName())
	                        .setParameter(14, data.getOccuptFname())
	                        .setParameter(15, Double.parseDouble(data.getPextent()))
	                        .setParameter(16, Double.parseDouble(data.getCultivatableLand()))
	                        .setParameter(17, Double.parseDouble(data.getUncultivatedLand()))
	                        .setParameter(18, Integer.parseInt(sesdcode))
	                        .setParameter(19, Integer.parseInt(sesmcode))
	                        .setParameter(20, data.getDistCode())
	                        .setParameter(21, data.getMandCode())
	                        .setParameter(22, data.getVillageCode())
	                        .setParameter(23, data.getBeneficiaryId())
	                        .setParameter(24, data.getPlotId())
	                        .executeUpdate();             
	                totalCount += count;
	            } catch (NumberFormatException e) {
	                System.err.println("Error parsing integer: " + e.getMessage());
	            } catch (Exception e) {
	                System.err.println("Error inserting data: " + e.getMessage());
	            }
	        }
	        try {
	            Query insertCdown = entityManager.createNativeQuery(updateQuery);
	            insertCdown.setParameter(1, totalCount);
	            insertCdown.setParameter(2, Integer.parseInt(lgddcode));
	            insertCdown.setParameter(3, Integer.parseInt(lgdmcode));
	            insertCdown.setParameter(4, Integer.parseInt(lgdvcode));
	            insertCdown.setParameter(5, wbdcode);
	            insertCdown.setParameter(6, wbmcode);
	            insertCdown.setParameter(7, wbvcode);
	            insertCdown.setParameter(8, cropyear);
	            insertCdown.setParameter(9, season);
	            int cdowndet = insertCdown.executeUpdate();
	            System.out.println(" update Row count----->" + cdowndet);
	            
	        } catch (Exception e) {
	            System.err.println("Error inserting user details: " + e.getMessage());
	        }
	    } else {
	        try {
	            Query insertCdown = entityManager.createNativeQuery(updateQuery);
	            insertCdown.setParameter(1, 0);
	            insertCdown.setParameter(2, Integer.parseInt(lgddcode));
	            insertCdown.setParameter(3, Integer.parseInt(lgdmcode));
	            insertCdown.setParameter(4, Integer.parseInt(lgdvcode));
	            insertCdown.setParameter(5, wbdcode);
	            insertCdown.setParameter(6, wbmcode);
	            insertCdown.setParameter(7, wbvcode);
	            insertCdown.setParameter(8, cropyear);
	            insertCdown.setParameter(9, season);
	            int cdowndet = insertCdown.executeUpdate();
	            System.out.println(" update Row count----->" + cdowndet);
	        } catch (Exception e) {
	            System.err.println("Error inserting user details: " + e.getMessage());
	        }
	    }
	    
	    	         
	    return totalCount;
	}

	public int[] getLgddcodeLgmcode(HttpSession session) {
		String sesmcode = (String) session.getAttribute("mcode");
		String sesdcode = (String) session.getAttribute("dcode");

		List<Object[]> lgddcodeLgmcode = recordRepository.getLgddcodeLgdmcode(Integer.parseInt(sesdcode),
				Integer.parseInt(sesmcode));
		if (!lgddcodeLgmcode.isEmpty()) {
			Object[] ob = lgddcodeLgmcode.get(0);
			int lgddcode = (int) ob[0];
			int lgdmcode = (int) ob[1];
			return new int[] { lgddcode, lgdmcode };
		} else {
			return new int[] {};
		}
	}

	@Transactional
	public List<LandRecord> getLandRecordData(String lgddcode, String lgdmcode, String lgdvcode, String key) {
		try {
			String rofrData = getRofrData(lgddcode, lgdmcode, lgdvcode, key);

			if (rofrData == null || rofrData.isEmpty()) {
				return new ArrayList<>();
			}

			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(rofrData);
			JsonNode dataNode = rootNode.get("Data");

			if (dataNode != null && dataNode.isArray()) {
				return mapper.readValue(dataNode.toString(), new TypeReference<List<LandRecord>>() {
				});
			} else {
				throw new RuntimeException("Data field is missing or not an array in the JSON response");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error fetching LandRecord data", e);
		}
	}

	public static String getRofrData(String dcode, String mcode, String vcode, String rofrkey) {
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

			URL url = new URL("https://giribhumi.ap.gov.in/api/ITDA/Agriculture");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");

			String inputData = "{\n" + "   \"key\":\"" + rofrkey + "\",\n" + "    \"Districtcode\":\"" + dcode + "\",\n"
					+ "    \"Mandalcode\":\"" + mcode + "\",\n" + "    \"RVCode\":\"" + vcode + "\"\n" + "}";

			try (OutputStream os = connection.getOutputStream()) {
				byte[] inputBytes = inputData.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				os.write(inputBytes, 0, inputBytes.length);
			}

			int responseCode = connection.getResponseCode();

			StringBuilder response = new StringBuilder();
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), java.nio.charset.StandardCharsets.UTF_8))) {
				String line;
				while ((line = br.readLine()) != null) {
					response.append(line);
				}
			}

			return response.toString();
		} catch (Exception e) {
			throw new RuntimeException("Error fetching ROFR data", e);
		}
	}

	public static String getKeyValue() {
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

			URL url = new URL("https://giribhumi.ap.gov.in/api/ITDA/GetApikey");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");

			String input = "{}";
			try (OutputStream os = connection.getOutputStream()) {
				byte[] inputBytes = input.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				os.write(inputBytes, 0, inputBytes.length);
			}

			int responseCode = connection.getResponseCode();
			StringBuilder response = new StringBuilder();
			try (BufferedReader in = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), java.nio.charset.StandardCharsets.UTF_8))) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			}

			JSONObject json = new JSONObject(response.toString());
			return json.getString("key");
		} catch (Exception e) {
			throw new RuntimeException("Error fetching API key", e);
		}
	}
}