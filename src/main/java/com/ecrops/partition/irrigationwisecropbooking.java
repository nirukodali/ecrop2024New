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

import com.ecrops.entity.Iriigationreport;


@Component
@Repository

@Transactional
public class irrigationwisecropbooking {
	@PersistenceContext
	private EntityManager entityManager;
	@Transactional
	public List<Iriigationreport> getigcropwiseabstrct(String cropid,String cropgrpid, String mcode,String dcode, String cropyear,
				String display ,String waterid) {
		System.out.println("waterid---------"+waterid);
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		String qry="";
		String tab="rep_vill_wise_irrgcropwise_ext_v";
		
		if(seasonYear >= 2023) {
			tab= "ecrop"+seasonYear+".rep_vill_wise_irrgcropwise_ext_v";
		}
		
		if ((cropgrpid).equals("")) {
			System.out.println("entered ifffff");
			
              qry = " select wbvname,sum(totextent) as totext from "+tab+"  where mcode="+mcode+"  and cr_year="+seasonYear+" and cr_season='"+seasonType+"' group by wbvname order by wbvname";
              
          } else if (!(cropgrpid).equals("") && cropid.equals("")) {
        	  System.out.println("entered elseeeeee");
              qry = " select wbvname,sum(totextent) as totext from  "+tab+"   where mcode="+mcode+"  and grpcode="+cropgrpid+"  and cr_year="+seasonYear+" and cr_season='"+seasonType+"' group by wbvname order by wbvname";
         
          }
		
          else if (!(cropgrpid).equals("") && !cropid.equals("") && waterid.equals("")) {
		 qry = " select wbvname,sum(totextent) as totext from  "+tab+"    where mcode="+mcode+"  and grpcode="+cropgrpid+"  and cr_crop ="+cropid+"  and cr_year="+seasonYear+" and cr_season='"+seasonType+"' group by wbvname order by wbvname";
          }
          else {
        	  qry = " select wbvname,sum(totextent) as totext from  "+tab+"    where mcode="+mcode+"  and grpcode="+cropgrpid+"  and cr_crop ="+cropid+" and wsrcid="+waterid+" and cr_year="+seasonYear+" and cr_season='"+seasonType+"' group by wbvname order by wbvname";
          }
		 
		 Query SelectQuery = (Query) entityManager.createNativeQuery(qry);
		
		 System.out.println("SelectQuery========>" + SelectQuery.toString());
		 
		 List<Object[]> details = SelectQuery.getResultList();
		 	
		 	System.out.println("details----------=>" + details.size());
			System.out.println("details------------>" + details.toString());
			
		 List<Iriigationreport> detailsEntities = new ArrayList<Iriigationreport>();
			try {
	 			for (Object[] row : details) {
	 				Iriigationreport entity = new Iriigationreport();
	 				
	 			
	 				entity.setWbvname((String) row[0]);
	 				entity.setTotext((BigDecimal) row[1]); 	
	 				detailsEntities.add(entity);
	 			}
	 			System.out.println("detailsEntities===========>" + detailsEntities.size());
	 
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 			System.out.println("erroe==> " + e);
	 		}	
		return detailsEntities;
		
	}
}
