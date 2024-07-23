package com.ecrops.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.Aadhaar;

@Repository
public interface AadhaarRepository extends JpaRepository<Aadhaar, String> {

	@Query(value = "SELECT district, count(*) FILTER(WHERE LENGTH(CAST(aadhaar_id AS text))=12) AS updatedcount,count(*) \r\n"
			+ "			 FILTER(WHERE aadhaar_id='' ) AS notupdatedcount from user_registration where status='A'  and type_user='25' \r\n"
			+ "			 and district!='999' group by district  order by district ", nativeQuery = true)
	public List<Aadhaar> findAll();
	

}