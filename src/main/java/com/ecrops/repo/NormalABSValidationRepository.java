package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.NormalABSValidation;

public interface NormalABSValidationRepository extends JpaRepository<NormalABSValidation, String>{
	@Query(value = "select dname,rbkname,totpushed,updcnt from  farmerdb.mobileupd_det_v",nativeQuery=true)
	List<normalabsview> getList();

	interface normalabsview{
		String getDname();
		String getRbkname();
		Long getTotpushed();
		Long getUpdcnt();
	}
}
