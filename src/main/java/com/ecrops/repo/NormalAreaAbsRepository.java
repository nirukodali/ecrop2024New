package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.NormalAreaAbs;

public interface NormalAreaAbsRepository extends JpaRepository<NormalAreaAbs, String>{
	@Query(value="select dname,sum(normalarea)  as normalarea from village_crop_normalareas a ,district_2011_cs b where a.dcode=b.dcode and b.dcode!=999 \r\n"
			+ "and a.cropyear=:cropyear and a.season=:season  group by dname \r\n",nativeQuery=true)
	List<NormalAreaAbs> getListt(@Param("cropyear") Integer cropyear,	@Param("season") String season);
	
	
	interface NormalAreaView{
		String getDname();
		Integer getNormalarea();
		
	}

}
