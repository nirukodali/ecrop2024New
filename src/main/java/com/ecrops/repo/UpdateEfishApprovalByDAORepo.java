package com.ecrops.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class UpdateEfishApprovalByDAORepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public int updateEFishDetailsByDAO(String dcode, String RecIdOrg, String OccupantNameOrg,
			String OccupantFNameOrg, String KhathaNoOrg, String SurveyNoOrg, String OccupantExtentOrg,
			String MappedExtentOrg, String AllowableExtentOrg, String ReqExtentOrg, String RemarksOrg,
			String apprStatus, HttpSession session) {

		System.out.println("******* Entered in updateEFishDetailsByDAO *********");

		int executeUpdate = 0;
//		String activeYear = "2024";

		Integer activeYear = (Integer) session.getAttribute("ACTIVEYEAR");

		String partitionName = "cr_details_efish_";

		partitionName = "ecrop" + activeYear + "." + partitionName + activeYear;

//        if(activeYear.equals("2024")){
//        	partitionName="ecrop"+activeYear+"."+partitionName+activeYear;
//        }

		String updateQuery = "update " + partitionName + " set dao_ts=now(), dao_appr_sts=?, dao_remarks=?"
				+ "where dist_code=? and recid=? and cr_sno=? and kh_no=?";   // and mand_code=?
		Query updateSQL = entityManager.createNativeQuery(updateQuery);

		if (apprStatus.equals("A")) {
			updateSQL.setParameter(1, apprStatus);
		} else if (apprStatus.equals("R")) {
			updateSQL.setParameter(1, apprStatus);
		}

		updateSQL.setParameter(2, RemarksOrg);
		updateSQL.setParameter(3, dcode);
		//updateSQL.setParameter(4, wbmcode);
		updateSQL.setParameter(4, Integer.parseInt(RecIdOrg));
		updateSQL.setParameter(5, SurveyNoOrg.trim());
		updateSQL.setParameter(6, KhathaNoOrg.trim());

		executeUpdate = updateSQL.executeUpdate();

		return executeUpdate;

	}

}
