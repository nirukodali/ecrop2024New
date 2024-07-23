package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.ABSValidationNo;


public interface ABSValidationNoRepository extends JpaRepository<ABSValidationNo, String>{
	@Query(value = "select dname,rbkname,totpushed,updcnt from farmerdb.mobileupd_det_v",nativeQuery=true)
	List<absview> getlist();
	
	interface absview{
		String getDname();
		String getRbkname();
		Long getTotpushed();
		Long getUpdcnt();
	}

}
