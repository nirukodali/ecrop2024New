package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.UnSurveyedView;

public interface UnSurveyedViewRepository extends JpaRepository<UnSurveyedView, Integer>{
	@Query(value = "select cr_dist_code,w.wbdname,count(distinct cr_mand_code) as no_mandals,count(distinct cr_vcode) as no_villages, \r\n"
			+ "count(1) filter (where cat_code='1') as Unset_est, sum(occupant_extent) filter  (where cat_code='1') as Unset_est_ext,\r\n"
			+ "count(2) filter (where cat_code='2') as Unset_inam,sum(occupant_extent) filter  (where cat_code='2') as Unset_inam_ext,\r\n"
			+ "count(3) filter (where cat_code='3') as Unsurveyed_est, sum(occupant_extent) filter  (where cat_code='3') as Unsurveyed_est_ext,\r\n"
			+ "count(4) filter (where cat_code='4') as Unsurveyed_Inam,sum(occupant_extent) filter  (where cat_code='4') as Unsurveyed_Inam_ext\r\n"
			+ "from unsurveyed_unsettled_det a, wbvillage_mst w \r\n"
			+ "where a.cr_vcode=w.wbvcode group by  cr_dist_code,w.wbdname order by cr_dist_code\r\n" , nativeQuery = true)
	List<UnSurveyedViewProjections> getList();

}
