package com.ecrops.partition;

import java.math.BigDecimal ;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.VillNormal;

@Component
@Repository

@Transactional
public class Villagewise {
	@PersistenceContext
	private EntityManager entityManager;
	@Transactional
	public List<VillNormal> getvillwise(String  mcode, String cropyear) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		String tab="village_crop_normalareas" ;
		System.out.println(mcode +"------"+seasonType+"----"+seasonYear);
		if (seasonYear.equals(2023)) {
            tab = "ecrop" + seasonYear + "." + tab;
         System.out.println("tab======" + tab);
        } else {
            tab = "village_crop_normalareas" ;
           System.out.println("tab============" +tab);
        }
		 
		 
		String qry="select  b.mname ,c.wbvname ,sum(normalarea) as normalarea  from " + tab + " a\r\n"
				+ "  inner join mandal_2011_cs b on a.mcode=b.mcode inner join wbvillage_mst c on a.vcode=c.wbvcode\r\n"
				+ "  where cropcode in (select cropid from cropnames  where cropnature='H' and active='A') and a.mcode="+mcode+" \r\n"
				+ " and a.cropyear="+seasonYear+" and  season='"+seasonType+"' group by b.mname,c.wbvname";
		
		Query villwise = (Query) entityManager.createNativeQuery(qry);

		System.out.println("qryyyyyyyyyyyy=>" + villwise.toString());
		List<Object[]> entyobject = villwise.getResultList();
		System.out.println("detailsEntities1=>" + entyobject.size());
		System.out.println("detailsEntities1=>" + entyobject.toString());
		
			List<VillNormal> details=new ArrayList<VillNormal>(); 
			
				for (Object[] row : entyobject) {
					VillNormal entity = new VillNormal();
					entity.setMname((String) row[0]);
					entity.setWbvname((String) row[1]);
					entity.setNormalarea((BigDecimal) row[2]);
					details.add(entity);
				}
				System.out.println("detailsEntities===========>" + details.size());

	
		
			
		
	return details;
	}
	


}
