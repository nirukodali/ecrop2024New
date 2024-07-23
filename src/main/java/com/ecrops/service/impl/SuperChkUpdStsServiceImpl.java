package com.ecrops.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ecrops.entity.AuthrbkvaavroekycModel;
import com.ecrops.entity.SuperChkUpdStatusModel;
import com.ecrops.service.SuperChkUpdStatusService;

@Service
public class SuperChkUpdStsServiceImpl implements SuperChkUpdStatusService{
	@PersistenceContext private EntityManager entityManager;

	@Override
	public List<SuperChkUpdStatusModel> getSuperChkUpdStatusdet(String partition, String supercheckupd,
			Integer wbdcode1, Integer wbmcode1, Integer vcode1) {
		// TODO Auto-generated method stub
		
		String sql="SELECT supercheck_userid,vaa_sup_rem,mro_remarks,mao_remarks, oremarks ,occup_name,occup_fname, "
				+ "  x.bookingid, kh_no, cr_sno, cr_sow_date , cr_no,wbdname,wbmname,wbvname,cropname,varietyname,reason,"
				+ " case when oremarks='A' then 'Entry Found Correct' when  "
				+ " oremarks='R' then 'Entry Found Incorrect'  end as remarks from "
				+ "  ((select substring(supercheck_userid,1,3) as supercheck_userid, superchk_remarks as oremarks,occupname as occup_name,occupfname ,cr_dist_code,cr_mand_code,cr_vcode,bookingid, "
				+ "   occupfname as occup_fname,a.kh_no,a.cr_sno,a.cr_sow_date ,a.cr_no,wbdname,wbmname,wbvname,cropname,varietyname,reason, "
				+ "  case when superchk_remarks='A' then 'Entry Found Correct' when "
				+ "  superchk_remarks='R' then 'Entry Found Incorrect'  end as remarks  from " + partition + "  a,wbvillage_mst b ,cropnames c ,  cr_variety_master v,authority_verify_reasons ar "
				+ " where a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and a.cr_vcode=b.wbvcode and a.cr_crop=c.cropid and a.variety=v.varietycode "
				+ " and  a.suprejreason =CAST(ar.code AS text) and a.cr_dist_code=? and a.cr_mand_code=? and cr_vcode=? ) x  "
				+ " inner join(select vaa_sup_rem,mro_remarks,mao_remarks,cr_dist_code,cR_mand_code,cr_vcode ,bookingid from " + supercheckupd + " where cr_dist_code=? and cr_mand_code=? and cr_vcode=?) y  "
				+ " on x.cr_dist_code=y.cr_dist_code  "
				+ "and x.cr_mand_code=y.cr_mand_code and x.cr_vcode=y.cr_vcode and x.bookingid=y.bookingid ) order by wbmname,wbvname";
		
		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1, wbdcode1);
		insertQuery.setParameter(2, wbmcode1);
		insertQuery.setParameter(3, vcode1);
		insertQuery.setParameter(4, wbdcode1);
		insertQuery.setParameter(5, wbmcode1);
		insertQuery.setParameter(6, vcode1);


		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<SuperChkUpdStatusModel> list = new ArrayList<SuperChkUpdStatusModel>();
		for(Object[] row: detailsEntities1) {
			SuperChkUpdStatusModel superChkUpdStatus = new SuperChkUpdStatusModel();
			superChkUpdStatus.setWbdname((String) row[12].toString());
			superChkUpdStatus.setWbmname((String)row[13].toString());
			superChkUpdStatus.setWbvname((String)row[14].toString());
			superChkUpdStatus.setBookingid((String)row[7].toString());
            superChkUpdStatus.setOccup_name((String)row[5].toString());
			superChkUpdStatus.setOccup_fname((String)row[6].toString());
			superChkUpdStatus.setCropname(row[15].toString());
			superChkUpdStatus.setVarietyname(row[16].toString());
			superChkUpdStatus.setCr_sow_date (row[10].toString());
			superChkUpdStatus.setKh_no(row[8].toString());
			superChkUpdStatus.setCr_sno(row[9].toString());
			superChkUpdStatus.setSupercheck_userid(row[0].toString());
			superChkUpdStatus.setOremarks(row[4].toString());
			superChkUpdStatus.setReason(row[17].toString());
			superChkUpdStatus.setVaa_sup_rem(row[1].toString());
            superChkUpdStatus.setMao_remarks(row[3].toString());
			superChkUpdStatus.setMro_remarks(row[2].toString());


			list.add(superChkUpdStatus);
		}		
		return list;
	}
}
