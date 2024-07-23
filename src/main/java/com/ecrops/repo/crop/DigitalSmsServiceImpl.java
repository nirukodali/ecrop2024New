package com.ecrops.repo.crop;

import java.math.BigInteger; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecrops.dto.crop.response.CommonMethods;
import com.ecrops.dto.crop.response.DistrictCodeAndDistrictName;
import com.ecrops.dto.crop.response.McodeWbemname;
import com.ecrops.repo.DigitalSmsRepository;

@Service

public class DigitalSmsServiceImpl {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DigitalSmsRepository digitalSmsRepository;
    
    @Autowired
    private CommonMethods  common;

    @Transactional
    public List<McodeWbemname> getMcodeWbemname(int distcode, String season, int year) {
        final Logger logger = LoggerFactory.getLogger(DigitalSmsServiceImpl.class);
        try {
            // Server-side validations
            if (distcode <= 0 || year <= 0 || season == null) {
                throw new IllegalArgumentException("Invalid distcode, year, or season input parameters");
            }
            
            String distcodeStr = String.valueOf(distcode);
            if (distcodeStr.length() < 2) {
                distcodeStr = '0' + distcodeStr;
            }

            String tab = "ecrop" + year + ".cr_details_" + season + distcodeStr + year;

            String qry1 = "SELECT a.mcode, wbemname FROM " + tab + " a, " +
                          "(SELECT DISTINCT mcode, wbemname FROM wbvillage_mst WHERE wbdcode = ?) b " +
                          "WHERE a.mcode = b.mcode AND a.mismatch IS NULL AND a.social_status IS NULL AND a.sup_status IS NULL AND a.ekyc = 'Y' AND a.smsmobileno IS NOT NULL " +
                          "AND cr_crop <> 888 AND LENGTH(CAST(smsmobileno AS TEXT)) = 10 AND CAST(smsmobileno AS TEXT) ~ '^[6-9][0-9]+$' AND sms_sent IS NULL AND " +
                          "(a.supercheck_userid IS NULL OR a.superchk_remarks = 'A' OR (a.superchk_remarks = 'R' AND a.supid IS NOT NULL)) " +
                          "GROUP BY a.dcode, a.mcode, wbemname ORDER BY wbemname";

            // Execute query
            Query query = entityManager.createNativeQuery(qry1);
            query.setParameter(1, distcode);

            // Fetch results
            List<Object[]> resultList = query.getResultList();
            logger.info("Query returned {} results", resultList.size());

            List<McodeWbemname> getData = new ArrayList<>();
            for (Object[] obj : resultList) {
                int mcode = ((Number) obj[0]).intValue();  // Safe type casting
                String wbemname = (String) obj[1];
                McodeWbemname setData = new McodeWbemname();
                setData.setMcode(mcode);
                setData.setWbemname(wbemname);
                getData.add(setData);
            }

            logger.info("Processed {} McodeWbemname objects", getData.size());
            return getData;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input parameters: distcode={}, season={}, year={}", distcode, season, year, e);
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error("Error executing query for distcode={}, season={}, year={}", distcode, season, year, e);
            return Collections.emptyList();
        }
    }
    
    @Transactional
    public List<DistrictCodeAndDistrictName> getDistrictCodeAndDistrictName() {
        List<Object[]> resultList = digitalSmsRepository.getWbvcodeWbvname();
        List<DistrictCodeAndDistrictName> getData = new ArrayList<>();
        for (Object[] obj : resultList) {
            int wbdcode = (int) obj[0];
            String dname = (String) obj[1];
            DistrictCodeAndDistrictName setData = new DistrictCodeAndDistrictName();
            setData.setWbdcode(wbdcode);
            setData.setDname(dname);
            getData.add(setData);
        }
        return getData;
    }

    @Transactional
    public int getSmsTotalCount(int activeYear, String userid, String clientip, String wbdcode, String wbmcode, String season, int cropyear) {
        // Server-side validations
        if (activeYear <= 0 || userid == null || clientip == null || wbdcode == null || wbmcode == null || season == null) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        String[] wbmcodes = wbmcode.split(",");
        if (wbmcodes == null || wbmcodes.length == 0) {
            throw new IllegalArgumentException("No mcodes provided");
        }

        String table = "ecrop" + activeYear + ".cr_details_" + season + (Integer.parseInt(wbdcode) <= 9 ? "0" + wbdcode : wbdcode) + cropyear;
        int villcount = 0, sms_total = 0;

        // Calculate the total number of villages
        for (String mcode : wbmcodes) {
            String qry2 = "SELECT COUNT(DISTINCT wbvcode) AS cnt FROM wbvillage_mst WHERE mcode = " + mcode;
            Query pst2 = entityManager.createNativeQuery(qry2);
            BigInteger rs2 = (BigInteger) pst2.getSingleResult(); // Use BigInteger instead of int
            int rs2Int = rs2.intValueExact(); // Convert BigInteger to int
            villcount += rs2Int;
        }

        if (villcount == 0) {
            return 0; // Or handle according to your business logic
        }
        final int[] smscount = new int[villcount];
        try {
            int v = 0;
            Callable<Void>[] callableArray = new Callable[villcount];
            for (String mcode : wbmcodes) {
                String qry1 = "SELECT DISTINCT wbvcode FROM wbvillage_mst WHERE mcode = " + mcode + " ORDER BY wbvcode";
                Query pst1 = entityManager.createNativeQuery(qry1);
                List<Integer> rs1 = pst1.getResultList();

                for (int vcode : rs1) {
                    String qry = "SELECT cr_vcode, COUNT(*) AS pending_sms FROM " + table +
                            " WHERE sms_sent IS NULL AND cr_mix_unmix_ext > 0 AND smsmobileno IS NOT NULL " +
                            " AND mismatch IS NULL AND social_status IS NULL AND sup_status IS NULL AND ekyc = 'Y' " +
                            " AND cr_vcode = " + vcode + " GROUP BY cr_vcode";

                    Query pst = entityManager.createNativeQuery(qry);
                    List<Object[]> rs = pst.getResultList();

                    for (Object[] row : rs) {
                        int pending_sms = ((Number) row[1]).intValue();
                        if (pending_sms > 0) {
                            int village = ((Number) row[0]).intValue();
                            final String tab2 = "ecrop" + activeYear + ".farmer_sms_details";
                            final String ip = clientip;
                            final String user_id = userid;
                            final String tab = table;
                            final int k = v;
                            callableArray[k] = () -> {
                    
                                smscount[k] = common.DigitalAck_sendSMS(village, tab, tab2, ip, user_id);
                                return null;
                            };
                            v++;
                        }
                    }
                }
            }

            if (v <= 0) {
                throw new IllegalArgumentException("No valid tasks to execute");
            }

            // Execute callables in parallel
            List<Callable<Void>> taskList = new ArrayList<>(Arrays.asList(callableArray).subList(0, v));
            ExecutorService executor = Executors.newFixedThreadPool(v);
            executor.invokeAll(taskList);

            // Calculate total SMS count
            for (int i = 0; i < v; i++) {
                sms_total += smscount[i];
            }

            return sms_total;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
