package com.ecrops.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.AllocationSurveyNoModel;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.repo.GetCropYearRepo;
import com.ecrops.service.GetCropYearService;

@Service
public class GetCropYearServiceImpl implements GetCropYearService{
	
	@Autowired private GetCropYearRepo cropYearRepo;
	@PersistenceContext private EntityManager entityManager;

	@Override
	public List<ActiveSeasonProjection> getActiveSeason() {
		// TODO Auto-generated method stub
		return cropYearRepo.getActiveSeason();
	}

	@Override
	public List<AllocationSurveyNoModel> getAllSurveryNos(String t1, Integer mcode, String userid, Integer cropyear1, String season) {
		// TODO Auto-generated method stub
		String sql = " ";
		System.out.println(cropyear1+"----------------------"+season);
		System.out.println(mcode+"----------------------"+t1);
		if (cropyear1 >= 2023  ) {
			sql = "select lgdvname,cast(kh_no as character varying) as kh_no,cr_sno,coalesce (cast(tot_extent as character varying) , '0') as tot_extent,coalesce(geo_reffered,' ') as geo_reffered,\r\n"
					+ " coalesce (data_src,' ') as data_src from "
					+ " " + t1 + " a, wbvillage_mst b where cr_vcode=wbvcode and "
					+ " a.mcode=? and srno_userid=? order by lgdvname,cr_sno,kh_no" ;
		} else if (cropyear1 == 2022 ) {
			sql = "select lgdvname,kh_no,cr_sno,coalesce (cast(tot_extent as character varying) , '0') as tot_extent,coalesce(geo_reffered,' ') as geo_reffered,\r\n"
					+ " coalesce (data_src,' ') as data_src from " + t1 + " a, wbvillage_mst b "
					+ " where cr_vcode=wbvcode and a.mcode=? order by lgdvname,cr_sno,kh_no";
		}
		System.out.println("qry--------->  "+sql);
		Query insry = (Query) entityManager.createNativeQuery(sql);
		if (cropyear1 >= 2023  ) {
			System.out.println(userid+"-----------------"+mcode);
			insry.setParameter(1, mcode);
			insry.setParameter(2, userid);

		}
		if (cropyear1 == 2022 ) {
			System.out.println("-----------------"+mcode);
			insry.setParameter(1, mcode);
		}
		List<Object[]> detailsEntities1 = insry.getResultList();
		System.out.println("detailsEntities=>" + detailsEntities1.size());
		List<AllocationSurveyNoModel> list = new ArrayList<AllocationSurveyNoModel>();

		for (Object[] row : detailsEntities1) {
			AllocationSurveyNoModel allocationSurveyNoModel = new AllocationSurveyNoModel();
			allocationSurveyNoModel.setLgdvname((String) row[0].toString());
			allocationSurveyNoModel.setKh_no((String) row[1].toString());
			allocationSurveyNoModel.setCr_sno((String) row[2].toString());
			allocationSurveyNoModel.setTot_extent((String) row[3].toString());
			
			Character Georeffered = (Character)row[4];	
//			if(Georeffered == null) {
//				allocationSurveyNoModel.setGeo_reffered("");
//			}else {
				allocationSurveyNoModel.setGeo_reffered((String) row[4].toString());;
//			}		
			allocationSurveyNoModel.setData_src((String) row[5].toString());
			list.add(allocationSurveyNoModel);
		}
		System.out.println("list------=>" + list.size());
		return list;
	}


	@Override
	public List<ActiveSeasonProjection> getAllSeason() {
		// TODO Auto-generated method stub
		return cropYearRepo.getAllSeason();
	}
	
	
}
