package com.ecrops.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ecrops.entity.VroRejdetModel;
import com.ecrops.service.GetvroRejIntfDetService;

@Service

public class GetVroRejIntfDetServiceImpl implements GetvroRejIntfDetService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<VroRejdetModel> getVroRejdets(String t1, Integer vill, String season, Integer cropyear1) {
		// TODO Auto-generated method stub



		String sql = "select  wbvname,cr_dist_code,cr_mand_code,cr_vcode,bookingid,cr_no,cropname,varietyname,"
				+ "cr_sow_dt,cr_sno,kh_no,rej_reason,reason from " + t1 + " a,vro_rej_reasons b,"
				+ "cropnames c,cr_variety_master d ,wbvillage_mst m where  a.rej_reason=b.code "
				+ "and a.cr_crop=c.cropid and a.cr_crop=d.cropcode and a.variety=d.varietycode and "
				+ "a.cr_vcode=m.wbvcode and cr_vcode in(select vcode from vs_rev_villages where vscode=? )"
				+ " and a.part_key like 'R%' and  a.season= ? and a.cropyear=? ";

		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1, vill);
		insertQuery.setParameter(2, season);
		insertQuery.setParameter(3, cropyear1);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> vrorejdetEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>" + vrorejdetEntities1.size());

		List<VroRejdetModel> list = new ArrayList<VroRejdetModel>();

		for (Object[] row : vrorejdetEntities1) {
			VroRejdetModel vrorejdetModel = new VroRejdetModel();
			vrorejdetModel.setWbvname((String) row[0].toString());
			vrorejdetModel.setBookingid((String) row[4].toString());
			vrorejdetModel.setCr_no((String) row[5].toString());
			vrorejdetModel.setCropname((String) row[6].toString());
			vrorejdetModel.setVarietyname((String) row[7].toString());
			vrorejdetModel.setCr_sow_dt((String) row[8].toString());
			vrorejdetModel.setCr_sno((String) row[9].toString());
			vrorejdetModel.setKh_no((String) row[10].toString());
			vrorejdetModel.setReason((String) row[12].toString());
			list.add(vrorejdetModel);
		}
		return list;

	}

}
