package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.AadhaarCNmand;


public interface AadhaarCNmandRepository extends JpaRepository<AadhaarCNmand, String>{
	@Query(value="select lgdmname,vname from user_registration u, wbvillage_mst wb,vill_sec_det vi where district=:dcode and district!='999' \r\n"
			+ "and length(cast (aadhaar_id as text))=12 and status='A' and type_user='25' and u.blockortehsil=cast(wb.mcode as varchar) \r\n"
			+ "and u.village=cast(vi.vcode as varchar) group by blockortehsil, village,lgdmname,vname",nativeQuery=true)
	List<aadharmandview> getlist(@Param("dcode") String dcode);
	
	interface aadharmandview{
		String getLgdmname();
		String getVname();
	}

}
