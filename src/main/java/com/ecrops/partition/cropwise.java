package com.ecrops.partition;

import java.math.BigDecimal; 
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.Cropwise;

@Component
@Repository

@Transactional
public class cropwise {
	@PersistenceContext
	private EntityManager entityManager;
	@Transactional
	public List<Cropwise> getcropwise(String  mcode, String cropyear ,String dcode, String cropcode) {
System.out.println(cropcode+"ssssssssssssssrrrrrrrrrrrrrccccccccccccccccccccc");
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		 String tab= "mandal_crop_normalareas_new";
				
		String qry="select normalarea,targetarea,mname from " + tab + " a ,mandal_2011_cs m where "
		        + " a.dcode=m.dcode and a.mcode=m.mcode and  a.mcode=" + dcode + " and cropcode= " + cropcode + ""
		        + " and a.cropyear=" + seasonYear + " and a.season='" + seasonType +"' order by mname ";
				 
		
		Query cropwise = (Query) entityManager.createNativeQuery(qry);

		System.out.println("qryyyyyyyyyyyy=>" + cropwise.toString());
		List<Object[]> entryobject = cropwise.getResultList();
		System.out.println("detailsEntities1=>" + entryobject.size());
		System.out.println("detailsEntities1=>" + entryobject.toString());
		
			List<Cropwise> details=new ArrayList<Cropwise>(); 
			
				for (Object[] row : entryobject) {
					Cropwise entity = new Cropwise();
					entity.setNormalarea((BigDecimal) row[0]);
					entity.setTargetarea((BigDecimal) row[1]);
					entity.setMname((String) row[3]);
					details.add(entity);
				}
				System.out.println("detailsEntities===========>" + details.size());

	
		
			
		
	return details;
	}
	


}
