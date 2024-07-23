package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.LockUnlockReasonsEntity;

@Repository
public interface LockUnlockReasons extends JpaRepository<LockUnlockReasonsEntity,Integer>{
	
	@Query(value="select distinct code, reason from lock_unlock_reasons where active='A' order by reason",nativeQuery=true)
	List<LockUnlockReasonsEntity> getReason();
}
