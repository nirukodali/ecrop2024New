package com.ecrops.repo;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.RedownloadCn;

public interface RedownloadCnRepository extends JpaRepository<RedownloadCn, String>{
	@Query(value="SELECT w.wbdname,w.wbmname,w.wbvname,x.downloadcount from   \r\n"
			+ "(select wbvcode,count(*) as downloadcount from ecrop2023.dataredownload where  \r\n"
			+ " cropyear=:cropyear and season=:season  group by wbvcode )x ,wbvillage_mst w\r\n"
			+ " where  w.wbvcode=x.wbvcode and dcode=:dcode group by \r\n"
			+ " wbdname,wbmname,wbvname,w.dcode,w.mcode,x.wbvcode,downloadcount order by dcode,mcode",nativeQuery = true)
	List<RedownloadCn> getListt(@Param("cropyear") Integer cropyear,@Param("season") String season,@Param("dcode") Integer dcode);
	
	@Query(value="SELECT w.wbdname,w.wbmname,w.wbvname,x.downloadcount from   \r\n"
			+ "(select wbvcode,count(*) as downloadcount from ecrop2024.dataredownload where  \r\n"
			+ " cropyear=:cropyear and season=:season  group by wbvcode )x ,wbvillage_mst w\r\n"
			+ " where  w.wbvcode=x.wbvcode and dcode=:dcode group by \r\n"
			+ " wbdname,wbmname,wbvname,w.dcode,w.mcode,x.wbvcode,downloadcount order by dcode,mcode",nativeQuery = true)
	List<RedownloadCn> getListt4(@Param("cropyear") Integer cropyear,@Param("season") String season,@Param("dcode") Integer dcode);
	
	@Query(value="SELECT w.wbdname,w.wbmname,w.wbvname,x.downloadcount from   \r\n"
			+ "(select wbvcode,count(*) as downloadcount from dataredownload where  \r\n"
			+ " cropyear=:cropyear and season=:season  group by wbvcode )x ,wbvillage_mst w\r\n"
			+ " where  w.wbvcode=x.wbvcode and dcode=:dcode group by \r\n"
			+ " wbdname,wbmname,wbvname,w.dcode,w.mcode,x.wbvcode,downloadcount order by dcode,mcode",nativeQuery = true)
	List<RedownloadCn> getListtP(@Param("cropyear") Integer cropyear,@Param("season") String season,@Param("dcode") Integer dcode);
}
