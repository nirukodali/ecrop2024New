package com.ecrops.repo;

import java.math.BigDecimal;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class UpdateEfishApprovalByVAARepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public int updateEFishDetailsByVAA(String[] RecIdLst, String[] OccExtentLst, String[] MapExtentLst,
			String[] AllowableExtLst, String[] RequiredExtLst, String village, String khathaNum, String surveyNum,
			HttpSession session) {

		int executeUpdate = 0;

		Integer activeYear = (Integer) session.getAttribute("ACTIVEYEAR");
		// String cr_Season = (String) session.getAttribute("seasonActive");

		// String activeYear = "2024";
		String partitionName = "cr_details_efish_";

		partitionName = "ecrop" + activeYear + "." + partitionName + activeYear;

//        if(activeYear.equals("2024")){
//        	partitionName="ecrop"+activeYear+"."+partitionName+activeYear;
//        }

		String updateQuery = "update " + partitionName
				+ " set req_ext=? where cr_vcode=? and cr_sno=? and kh_no=? and recid=?";

		Query updateSQL = entityManager.createNativeQuery(updateQuery);

		for (int j = 0; j < RecIdLst.length; j++) {

			updateSQL.setParameter(1, new BigDecimal((RequiredExtLst[j])));
			updateSQL.setParameter(2, village);
			updateSQL.setParameter(3, surveyNum.trim());
			updateSQL.setParameter(4, khathaNum.trim());
			updateSQL.setParameter(5, Integer.parseInt(RecIdLst[j]));
			executeUpdate = updateSQL.executeUpdate();
		}

		return executeUpdate;

	}

}
