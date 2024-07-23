package com.ecrops.repo;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class SaveSeedVarietyRepo {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public int insertSeedDetails(Integer varietycode, String encodedVariety, String crcode, String user) {
		
		int executeInsert = 0;
		String insertQuery = "INSERT INTO public.cr_variety_master(varietycode, varietyname, cropcode, yield_ll, yield_ul, entry_by, entry_date,status)	VALUES (?, ?, ?, ?, ?, ?, now(),?)";
		
		Query pst1 = entityManager.createNativeQuery(insertQuery);

        pst1.setParameter(1, varietycode + 1);
        pst1.setParameter(2, encodedVariety);
        pst1.setParameter(3, Integer.parseInt(crcode));
        pst1.setParameter(4, 0);
        pst1.setParameter(5, 0);
        pst1.setParameter(6, user);
        pst1.setParameter(7, "A");
        
        executeInsert = pst1.executeUpdate();

		return executeInsert;
		
	}
	
}
