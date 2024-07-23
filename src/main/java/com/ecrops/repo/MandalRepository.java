package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.HoMandal;

@Repository
public interface MandalRepository extends JpaRepository<HoMandal,Integer> {

	public List<HoMandal> findByDcode(int dcode);
	
	@Query(value="select * from mandal_2011_cs where dcode=:dcode or wbdcodenew=:dcode order by mname",nativeQuery=true)
    List<HoMandal> getMandals(@Param("dcode") Integer dcode);

}