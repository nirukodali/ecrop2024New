package com.ecrops.repo.crop;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.dto.crop.response.AddAdditionalVroVillageData;

@Service
@Transactional
public class AddAdditionalVroService {

	@PersistenceContext
	private EntityManager entityManager;

	public List<AddAdditionalVroVillageData> getData(int dcode, int mcode) {
		try {
			String qry1 = "select distinct(wbvcode), wbvname from wbvillage_mst where dcode = ? and mcode = ? order by wbvname";
			Query query = entityManager.createNativeQuery(qry1);
			query.setParameter(1, dcode);
			query.setParameter(2, mcode);

			List<Object[]> result = query.getResultList();
			List<AddAdditionalVroVillageData> villageDataList = new ArrayList<>();

			for (Object[] obj : result) {
				int wbvcode = (int) obj[0];
				String wbvname = (String) obj[1];
				AddAdditionalVroVillageData villageData = new AddAdditionalVroVillageData();
				villageData.setWbvcode(wbvcode);
				villageData.setWbvname(wbvname);
				villageDataList.add(villageData);
			}

			return villageDataList;
		} catch (Exception e) {
			throw new RuntimeException("An error occurred while fetching village data.", e);
		}
	}

	public int getWmdcode(String vcode) {
		try {
			String qry1 = "SELECT  wbmcode FROM wbvillage_mst WHERE wbvcode= ?";
			Query query = entityManager.createNativeQuery(qry1);
			query.setParameter(1, Integer.parseInt(vcode));
			return (int) query.getSingleResult();
		} catch (Exception e) {
			throw new RuntimeException("An error occurred while fetching wbmcode.", e);
		}
	}

	public String addAdditionalAuthorityOfficer(String userId, String districtCode, String mandalCode,
			String officerName, String mobilePhone, String emailId, String aadhaarId, String previousWbvCode,
			String wbdCode, String wbmCode, String clientIPAddress) {
		String message = "";
		try {
			String checkAadhaarQuery = "SELECT 1 FROM (SELECT aadhaarno AS aadhaar FROM cr_addl_authofficers UNION ALL SELECT aadhaar_id FROM user_registration) a WHERE aadhaar = ?";
			Query cAadhaarQuery = entityManager.createNativeQuery(checkAadhaarQuery);
			cAadhaarQuery.setParameter(1, aadhaarId);
			int existingAadhaarCount;
			try {
				existingAadhaarCount = (int) cAadhaarQuery.getSingleResult();
			} catch (NoResultException e) {
				existingAadhaarCount = 0;
			}
			if (existingAadhaarCount > 0) {
				message = "Aadhaar number already exists. Record Not Inserted ";
			} else {
				String insertOfficerQuery = "INSERT INTO public.cr_addl_authofficers (cr_dist_code, cr_mand_code, cr_vcode, dcode, mcode, officername, mobileno, emailid, aadhaarno, clientip) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				Query OfficerQuery = entityManager.createNativeQuery(insertOfficerQuery);
				OfficerQuery.setParameter(1, Integer.parseInt(wbdCode));
				OfficerQuery.setParameter(2, Integer.parseInt(wbmCode));
				OfficerQuery.setParameter(3, Integer.parseInt(previousWbvCode));
				OfficerQuery.setParameter(4, Integer.parseInt(districtCode));
				OfficerQuery.setParameter(5, Integer.parseInt(mandalCode));
				OfficerQuery.setParameter(6, officerName);
				OfficerQuery.setParameter(7, Long.parseLong(mobilePhone));
				OfficerQuery.setParameter(8, emailId);
				OfficerQuery.setParameter(9, aadhaarId);
				OfficerQuery.setParameter(10, clientIPAddress);
				int insertCount = OfficerQuery.executeUpdate();

				if (insertCount > 0) {
					message = "VRO added successfully.";
				} else {
					message = "Failed to add VRO.";
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			message = "An error occurred while processing the request.";
		}
		return message;
	}
}
