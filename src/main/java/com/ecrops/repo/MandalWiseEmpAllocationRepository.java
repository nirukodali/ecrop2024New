package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.MandalWiseEmpAllocation;

public interface MandalWiseEmpAllocationRepository extends JpaRepository<MandalWiseEmpAllocation, String>{
	@Query(value="select b.vsdname,b.vsmname, count(*) filter (where desg=1) as VAA,\r\n"
			+ "count(*) filter (where desg=2) as VHA,count(*) filter (where desg=3) as VSA,\r\n"
			+ "count(*) filter (where desg=4) as AEO, count(*) filter (where desg=5) as MPEO, \r\n"
			+ "count(*) filter (where desg=6) as VFA, count(*) filter (where desg=7) as ATM,\r\n"
			+ "count(*) filter (where desg=8) as BTM, count(*) filter (where desg=9) as NOPOST \r\n"
			+ "from rbk_status a, vill_sec_det b where a.dcode=b.dcode and a.mcode=b.mcode and a.vscode=b.vcode\r\n"
			+ "group by a.mcode,b.vsdname,b.vsmname order by b.vsdname",nativeQuery=true)
	List<manAlloView> getlist();
	
	interface manAlloView{
		String getVsdname();
		String getVsmname();
		Long getVAA();
		Long getVHA();
		Long getVSA();
		Long getAEO();
		Long getMPEO();
		Long getVFA();
		Long getATM();
		Long getBTM();
		Long getNOPOST();
	}

}
