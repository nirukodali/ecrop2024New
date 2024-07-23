package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.EfishdetailEntity;





public interface EfishdetailRepo extends JpaRepository<EfishdetailEntity, Integer>{
	
	@Query(value = "select recid,dist_name,mand_name,village_name,kh_no,cr_sno,occupant_name,mapped_extent,total_extent,occupant_father_name,"
		+ "pattadar_name,pattadar_father_name from ecrop2023.cr_details_efish_2023 where  cr_vcode=:vcode and kh_no=:khano and (COALESCE(CAST(total_extent AS double precision), 0) -COALESCE(CAST(mapped_extent AS double precision), 0))>0", nativeQuery = true)
//			+ "pattadar_name,pattadar_father_name from ecrop2023.cr_details_efish_2023 where dist_code='19' and mand_code='47' and cr_vcode='1910004' and kh_no='122'", nativeQuery = true)
	public List<EfishdetailEntity> getefishdetails(@Param("vcode") String vcode , @Param("khano") String khano);
	

}
