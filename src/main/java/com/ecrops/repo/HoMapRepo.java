package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.HoMandal;
import com.ecrops.projection.HoDisplay;
import com.ecrops.projection.HoMapping;
import com.ecrops.projection.UnMappingProj;


public interface HoMapRepo extends JpaRepository<HoMandal, Integer> {

	@Query(value = "select mcode,mname from mandal_2011_cs where dcode=:dcode order by mname", nativeQuery = true)	
	public List<HoMapping> getMandal(@Param("dcode") Integer dcode);
	
	@Query(value="select * from mandal_2011_cs where dcode=:dcode and mcode not in (select mcode from homandalsmap) order by mname",nativeQuery=true)
	public List<HoMapping> getunmapMandal(@Param("dcode") Integer dcode);
	
	@Query(value="select b.dname,c.mname,d.mname as hqname,a.dcode,a.mcode,a.hqcode from homandalsmap a,district_2011_cs b,mandal_2011_cs c ,"
			+ "mandal_2011_cs d "
			+ " where a.dcode=b.dcode and a.dcode=c.dcode and a.mcode=c.mcode and a.dcode=d.dcode and a.hqcode=d.mcode and a.dcode=:dcode"
			+ " order by dname,hqname,mname", nativeQuery = true)
	public List<HoDisplay> getMappedDetails(@Param("dcode") Integer dcode);
	
	@Query(value="select blockortehsil from user_registration where type_user='22' and blockortehsil=:hmcode ", nativeQuery = true)
	public List<HoMapping> usercheck(@Param("hmcode") String hmcode);
	
	@Query(value="select a.dcode,a.mcode,a.hqcode,dname,c.mname as hqname,b.mname from homandalsmap a,dist_mand_names_v b,mandal_2011_cs c "
			+ "	where a.dcode=b.dcode and a.mcode=b.mcode and a.dcode=c.dcode and a.hqcode=c.mcode and a.dcode=:dcode order by hqname,mname ", nativeQuery = true)
	public List<UnMappingProj> getheadquarter(@Param("dcode") Integer dcode);
	
	
}
