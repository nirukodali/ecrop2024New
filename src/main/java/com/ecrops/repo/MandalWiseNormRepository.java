package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.MandalWiseNorm;


public interface MandalWiseNormRepository extends JpaRepository<MandalWiseNorm, String>{
	@Query(value="select dname,mname,sum(normalarea) as normalarea from village_crop_normalareas a, district_2011_cs  b,mandal_2011_cs  c \r\n"
			+ "where a.dcode=b.dcode  and b.dcode!=999  and a.dcode=c.dcode and a.mcode=c.mcode and cropyear=:cropyear \r\n"
			+ "and season=:season and a.dcode=:dcode group by dname,mname order by dname,mname ",nativeQuery=true)
	List<MandalWiseNorm> getListt(@Param("cropyear") Integer cropyear,@Param("season") String season,@Param("dcode") Integer dcode);
	
}
