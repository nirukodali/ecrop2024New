package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.AadhaarNotUpdated;

public interface AadhaarNotUpdatedRepository extends JpaRepository<AadhaarNotUpdated, String>{
	@Query(value="select lgdmname,vname from user_registration u, wbvillage_mst wb,vill_sec_det vi \r\n"
			+ "where district=:dcode and district!='999' and aadhaar_id='' and status='A' and type_user='25'  \r\n"
			+ "and u.blockortehsil=cast(wb.mcode as varchar) and u.village=cast(vi.vcode as varchar) \r\n"
			+ "group by blockortehsil, village,lgdmname,vname",nativeQuery=true)
	List<aadhaarnotupdateview> getList(@Param("dcode") String dcode);
	
	interface aadhaarnotupdateview{
		String getLgdmname();
		String getVname();
	}

}
