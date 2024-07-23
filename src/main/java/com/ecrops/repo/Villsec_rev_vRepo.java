package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.Villsec_rev_v;

@Repository
public interface Villsec_rev_vRepo extends JpaRepository<Villsec_rev_v, Integer> {

    @Query(value="select distinct vcode, wbvname from ecrop2024.villsec_rev_v where vcode=:vcode", nativeQuery=true)
    List<Villsec_rev_v> getVillagenames(@Param("vcode") int vscode);

}