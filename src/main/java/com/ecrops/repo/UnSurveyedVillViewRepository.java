package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.UnSurveyedVillView;

public interface UnSurveyedVillViewRepository extends JpaRepository<UnSurveyedVillView, String>{
	@Query(value = "select cr_dist_code,w.wbdname,cr_mand_code,w.wbmname,cr_vcode,w.wbvname,count(1) \r\n"
			+ "filter (where cat_code='1') as Unset_est,count(2)\r\n"
			+ "filter (where cat_code='2') as Unset_inam,count(3) \r\n"
			+ "filter (where cat_code='3') as Unsurveyed_est,count(4) \r\n"
			+ "filter (where cat_code='4') as Unsurveyed_Inam from unsurveyed_unsettled_det a, wbvillage_mst w \r\n"
			+ "where a.cr_vcode=w.wbvcode  group by  cr_dist_code,w.wbdname,cr_mand_code,w.wbmname,cr_vcode,w.wbvname \r\n"
			+ "order by cr_dist_code,w.wbdname,cr_mand_code,w.wbmname,cr_vcode" , nativeQuery = true)
	List<UnSurveyedVillViewProjections> getList();

}
