package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ecrops.entity.SupercheckVill;

public interface SupercheckVillRepo extends JpaRepository<SupercheckVill, String>{
//	@Query(value="select wbvname,hod_allotted,hod_approved,hod_rejected,dao_allotted,dao_approved,dao_rejected,ada_allotted,ada_approved,ada_rejected,\r\n"
//			+ "mao_allotted,mao_approved,mao_rejected,dho_allotted,dho_approved,dho_rejected,ho_allotted,ho_approved,ho_rejected,\r\n"
//			+ "rdo_allotted, rdo_approved,rdo_rejected,tah_allotted,tah_approved,tah_rejected,\r\n"
//			+ "dc_allotted,dc_approved,dc_rejected,jc_allotted,jc_approved,\r\n"
//			+ "jc_rejected, cast(cr_dist_code as varchar),cast(cr_mand_code as varchar),mcode,dname,mname  \r\n"
//			+ "from supercheck_status_mandwise where cast(cr_dist_code as varchar)=:wbdcode and cast(cr_mand_code as varchar)=:wbmcode order by wbvname"
//			,nativeQuery=true)
//	public List<SupercheckVill> getSupVill(@Param("wbdcode") String wbdcode,
//			                               @Param("wbmcode") String wbmcode);

}
