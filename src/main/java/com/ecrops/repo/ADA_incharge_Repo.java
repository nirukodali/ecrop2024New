package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.RbkInchEntity;
import com.ecrops.projection.InchargeRbkProjection;

public interface ADA_incharge_Repo extends JpaRepository<RbkInchEntity, Integer> {

	@Query(value = "select  b.rbkcode,'XXXXXXXX'||right(aadhaar_id,4) as uid,vname,rbkuserid,emp_name as empname,a.emp_code as empcode,inchargests,mobile,"
			+ "email,designation from ecrop2023.cr_emp_profile a,ecrop2023.emp_rbk_map_incharges b,vill_sec_det c where a.emp_code=b.empcode "
			+ "and b.dcode=c.dcode and b.mcode=c.mcode and b.rbkcode=c.vcode and b.ada_appr is null and"
			+ " b.dcode=:district and b.mcode=:mandal order by vname", nativeQuery = true)
	public List<InchargeRbkProjection> getadaDMcode(@Param("district") Integer district,
			@Param("mandal") Integer mandal);

}
