package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.ExcessBookingKhataProjections;
import com.ecrops.entity.ExcessBookingKhataWise;
import com.ecrops.entity.ExcessBookingSurveyProjections;

public interface ExcessBookingkhataRepository extends JpaRepository<ExcessBookingKhataWise, Integer>{
	@Query(value = "select wbedname,wbemname,wbevname,vname,count(*) filter (where excess_booking='Y') as yet_to_attempt, count(*) filter (where excess_booking='A') \r\n"
			+ "as approved_by_mao, \r\n"
			+ "count(*) filter (where excess_booking='E') as deleted from ecrop2023.cr_details a inner join wbvillage_mst b on cr_vcode=wbvcode\r\n"
			+ "inner join vill_sec_det c \r\n"
			+ " on c.vcode=CAST(SUBSTRING(a.updatedBy, 5) AS INT) where excess_booking_khno is not null group by wbedname,wbemname,wbevname,vname",nativeQuery = true)
	List<ExcessBookingKhataProjections> getList();

}
