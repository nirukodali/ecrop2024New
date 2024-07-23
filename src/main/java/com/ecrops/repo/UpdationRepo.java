package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.Updation;

import groovyjarjarpicocli.CommandLine.Parameters;

@Repository
public interface UpdationRepo extends JpaRepository<Updation, String> { 		//ecrop2024.					and cropyear=:cropYear and season=:season 
	@Query(value = "select  mn.vcode,m.wbvname,normalarea,targetarea from ecrop2024.village_crop_normalareas mn	 left join wbvillage_mst m on \r\n"
			+ " mn.dcode=m.dcode and mn.mcode=m.mcode and mn.vcode=m.wbvcode where  mn.dcode=:dcode and mn.mcode=:mode  and cropcode=:cropNum and season=:season \r\n"
			+ "  order by m.wbvname", nativeQuery = true)
	public List<updateView> getDetails(@Param("dcode") int dcode,@Param("mode") int mode,@Param("cropNum") int cropNum,@Param("season") String season);
	
//	@Param("cropYear") int cropYear,@Param("season") String season,
	
	@Query(value = "select  mn.vcode,m.wbvname,normalarea,targetarea from ecrop2024.village_crop_normalareas mn	 left join wbvillage_mst m on \r\n"
			+ " mn.dcode=m.dcode and mn.mcode=m.mcode and mn.vcode=m.wbvcode where  mn.dcode=:dcode and mn.mcode=:selectedMand  and cropcode=:cropNum\r\n"
			+ " and cropyear=:cropYear and season=:season and cropnature='H'  order by m.wbvname", nativeQuery = true)
	public List<updateView> getDetailsHo(@Param("cropYear") int cropYear,@Param("dcode") int dcode,@Param("selectedMand") Integer selectedMand,@Param("season") String season,@Param("cropNum") int cropNum);
	
	
	
	@Modifying
	@Query(value = "UPDATE ecrop2024.village_crop_normalareas SET normalarea = :normalarea,targetarea= 0.0, crt_user=:user WHERE dcode = :dcode AND mcode = :mcode AND vcode = :vcode and cropcode=:cropNum and season=:season", nativeQuery = true)
	void updateNormalArea(@Param("normalarea") BigDecimal normalarea, @Param("dcode") Integer dcode, @Param("mcode") Integer mcode, @Param("vcode") Integer vcode, @Param("cropNum") int cropNum, @Param("user") String user, @Param("season") String season );
	
//	,@Param("targetarea") BigDecimal targetarea

	@Modifying
	@Query(value = "UPDATE ecrop2024.village_crop_normalareas SET normalarea = :normalarea,targetarea=0.0, crt_user=:user WHERE dcode = :dcode AND  vcode = :vcode and cropcode=:cropNum", nativeQuery = true)
	void updateNormalAreaHo(@Param("normalarea") BigDecimal normalarea, @Param("dcode") Integer dcode, @Param("vcode") Integer vcode, @Param("cropNum") int cropNum, @Param("user") String user);

	
	interface updateView{
		Integer getvcode();
		BigDecimal getnormalarea();
		BigDecimal gettargetarea();
		String getWbvname();
	}

}
