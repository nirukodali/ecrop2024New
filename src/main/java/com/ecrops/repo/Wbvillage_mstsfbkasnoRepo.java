package com.ecrops.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecrops.entity.Wbvillage_mstFsfbkasno;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface Wbvillage_mstsfbkasnoRepo extends JpaRepository<Wbvillage_mstFsfbkasno, Integer> {

    @Query(value = "select distinct wbmcode from wbvillage_mst where mcode = :mcode and wbdcode != 88", nativeQuery = true)
    int findDistinctWbmcodeByMcode(@Param("mcode") int mcode);
}
