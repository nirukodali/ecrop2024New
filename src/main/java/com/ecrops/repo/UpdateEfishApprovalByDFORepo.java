package com.ecrops.repo;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class UpdateEfishApprovalByDFORepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public int updateEFishDetailsByDFO(String dcode, String RecIdOrg, String OccupantNameOrg,
			String OccupantFNameOrg, String KhathaNoOrg, String SurveyNoOrg, String OccupantExtentOrg,
			String MappedExtentOrg, String AllowableExtentOrg, String ReqExtentOrg, String RemarksOrg,
			String apprStatus, String DfoSugExtentOrg, HttpSession session) {

		int executeUpdate = 0;
//		String activeYear = "2024";

		Integer activeYear = (Integer) session.getAttribute("ACTIVEYEAR");

		String partitionName = "cr_details_efish_";

		partitionName = "ecrop" + activeYear + "." + partitionName + activeYear;

//        if(activeYear.equals("2024")){
//        	partitionName="ecrop"+activeYear+"."+partitionName+activeYear; 
//        }

		String updateQuery = "update " + partitionName
				+ " set dfo_ts=now(), dfo_appr_sts=?, dfo_remarks=?, dfo_sug_ext=?" 
				+ "where dist_code=? and recid=? and cr_sno=? and kh_no=?";   // and mand_code=? 
		Query updateSQL = entityManager.createNativeQuery(updateQuery);

		if (apprStatus.equals("A")) {
			updateSQL.setParameter(1, apprStatus);
		} else if (apprStatus.equals("R")) {
			updateSQL.setParameter(1, apprStatus);
		}

		updateSQL.setParameter(2, RemarksOrg);

		if (DfoSugExtentOrg == null || DfoSugExtentOrg.isEmpty()) {
			updateSQL.setParameter(3, new BigDecimal(0));
		} else {
			updateSQL.setParameter(3, new BigDecimal(DfoSugExtentOrg));
		}

		updateSQL.setParameter(4, dcode);
//		updateSQL.setParameter(5, wbmcode);
		updateSQL.setParameter(5, Integer.parseInt(RecIdOrg));
		updateSQL.setParameter(6, SurveyNoOrg.trim());
		updateSQL.setParameter(7, KhathaNoOrg.trim());

		executeUpdate = updateSQL.executeUpdate();

		return executeUpdate;

	}

}
