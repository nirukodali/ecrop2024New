package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ecrops.entity.DeleteReasonsEntity;

public interface GetReasonsForDelete extends JpaRepository<DeleteReasonsEntity, Integer> {
	
	 @Query(value="select code, reason from soc_audit_del_reasons where active='A'", nativeQuery = true)
	 
	 List<DeleteReasonsEntity> getReasonsForDelete();

	
}
