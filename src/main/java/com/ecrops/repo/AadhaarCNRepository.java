package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.AadhaarCN;


public interface AadhaarCNRepository extends JpaRepository<AadhaarCN, String>{
	@Query(value="SELECT dname,count(*) FILTER(WHERE LENGTH(CAST(aadhaar_id AS text))=12) AS updatedcount,\r\n"
			+ "count(*)  FILTER(WHERE aadhaar_id='' ) AS notupdatedcount,district FROM user_registration a \r\n"
			+ "JOIN district_2011_cs b ON a.district = CAST(b.dcode AS varchar) WHERE status = 'A' \r\n"
			+ "AND type_user = '25' AND district != '999' GROUP BY district, dname ORDER BY district",nativeQuery=true)
	List<aadharview> getlist();
	
	interface aadharview{
		String getDname();
		Long getUpdatedcount();
		Long getNotupdatedcount();
		String getDistrict();
	}

}
