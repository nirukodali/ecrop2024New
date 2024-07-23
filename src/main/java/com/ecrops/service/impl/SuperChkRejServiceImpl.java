package com.ecrops.service.impl;
import java.math.BigDecimal; 
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.SuperChk_rejReportModel;
import com.ecrops.service.superChk_rejService;

@Service
public class SuperChkRejServiceImpl implements superChk_rejService {

	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public List<SuperChk_rejReportModel> getsuperchkrejdet(String superchkrejmv, Integer wbdcode1,
			Integer wbmcode1, Integer rbkcode) {
		// TODO Auto-generated method stub

		
		String sql = "select distinct bookedext,wsrcdesc,ekycfarmername,wbdname,wbmname,wbvname,\r\n"
				+ "bookingid,supercheck_userid,occupname,occupfname,oc_name,oc_fname,remarks,r.reason,\r\n"
				+ "cr_vcode,cr_sno,cr_crop,kh_no,cr_no, cropname,varietyname,cr_sow_date\r\n"
				+ "from " + superchkrejmv + " a , authority_verify_reasons r where cast(a.reason as int)=r.code and active='A' and cr_dist_code=? and \r\n"
				+ "cr_mand_code=? and cr_vcode in (select distinct vcode from vs_rev_villages\r\n"
				+ "where rbkcode=?) order by wbvname,cr_sno,kh_no";
				
		
	Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1, wbdcode1);
		insertQuery.setParameter(2, wbmcode1);
		insertQuery.setParameter(3, rbkcode);
		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> superchkrejEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+superchkrejEntities1.size());
		
		List<SuperChk_rejReportModel> list = new ArrayList<SuperChk_rejReportModel>();
		
		for(Object[] row: superchkrejEntities1) {
			SuperChk_rejReportModel superChkrejmodel = new SuperChk_rejReportModel();
			superChkrejmodel.setWbdname((String)row[3].toString());
			superChkrejmodel.setWbmname((String)row[4].toString());
	        superChkrejmodel.setWbvname((String)row[5].toString());
			superChkrejmodel.setBookingid((String)row[6].toString());
			superChkrejmodel.setOccupname((String)row[8].toString());
			superChkrejmodel.setOccupfname((String)row[9].toString());
			superChkrejmodel.setCropname((String)row[19].toString());
			superChkrejmodel.setVarietyname((String)row[20].toString());
			superChkrejmodel.setBookedext((String)row[0].toString());
			superChkrejmodel.setWsrcdesc((String)row[1].toString());
			superChkrejmodel.setCr_sow_date((String)row[21].toString());
			superChkrejmodel.setKh_no((String)row[17].toString());
			superChkrejmodel.setCr_sno((String)row[15].toString());
			superChkrejmodel.setSupercheck_userid((String)row[7].toString());
			
//			String rem = (String)row[12];
			superChkrejmodel.setRemarks((String)row[12].toString());
		System.out.println((String)row[13].toString());
//			String reason = MasterFunctions.MasterFunction(row[13].toString(), "reasoncode");	
		superChkrejmodel.setReason((String)row[13].toString());
			
//			System.out.println("reason=>"+reason);
//			superChkrejmodel.setReason(reason);	
			list.add(superChkrejmodel);
		}

		return list;
	
	}
	
	
			
			
			
	
}
