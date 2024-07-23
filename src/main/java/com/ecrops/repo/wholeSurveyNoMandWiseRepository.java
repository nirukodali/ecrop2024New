package com.ecrops.repo;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.wholeSurveyNoMandWise;

public interface wholeSurveyNoMandWiseRepository extends JpaRepository<wholeSurveyNoMandWise, String>{
	@Query(value="select lgdvname,cr_wsno from (select * from masters.wholesurveynolist \r\n"
			+ "where wbdcode=:wbdcode and wbmcode=:wbmcodenew) a,(select * from wbvillage_mst \r\n"
			+ "where wbdcode=:wbdcode and wbmcode=:wbmcodenew) b where a.wbvcode=b.wbvcode ",nativeQuery=true)
	List<wholemandview> getListt(@Param("wbdcode") Integer wbdcode,@Param("wbmcodenew") Integer wbmcodenew);

	interface wholemandview{
		String getLgdvname();
		Integer getCr_wsno();
	}

}
