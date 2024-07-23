package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.Obj_unobjEntity;



public interface ObjkhatanoRepository extends JpaRepository<Obj_unobjEntity,Integer>  {
	
	@Query(value="select category,code,crb_remarks from obj_unobj where crb_remarks not in ('No') ",nativeQuery=true)
    List<Obj_unobjEntity> getkhatano();
    
    
    @Query(value="select category,code,crb_remarks from obj_unobj where crb_remarks  in ('No') and code=:code",nativeQuery=true)
    List<Obj_unobjEntity> getcategory(@Param("code") Integer code);

}
