package com.ecrops.partition;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecrops.entity.Abstractreport;


@Repository
@Transactional
public class AbstractReportsRepo {
	@PersistenceContext
	private EntityManager entityManager;
	

	public List<Abstractreport> getAbstractReportsRepo(String cropid,String cropgrpid, String mcode,String dcode, String cropyear,
		String display) {
		System.out.println();
		String displayname = "";
		System.out.println("cropp"+cropgrpid);
		System.out.println("cpppppppppppppppppp"+cropid);
		System.out.println("mcodeeeeeeeeeee"+mcode);
		System.out.println("dcvodeeeeeeeeeeeeee"+dcode);
		
		
		
		if (display== null ||display == "") {
        	display = " sum(totextent) ";
            displayname = "Current Season and Forwarded";
        } else if (Integer.parseInt(display) == 1) {
        	display = " sum(totextent) ";
            displayname = "Current Season";
        } else if (Integer.parseInt(display) == 2) {
        	display = " sum(totextent) ";
            displayname = "Forwarded";
        }
		
		System.out.println("display----->"+display);
		int year = Year.now().getValue();
		System.out.println("year-----------------------"+year);
		String[] season = cropyear.split("@");
		System.out.println("season---------"+season);
		String seasonType = season[0];
		System.out.println("seasonType---------"+seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear---------"+seasonYear);
	
			String tab = "rep_vill_wise_cropwise_ext_v_ho";
			
			
			
			if(seasonType=="K" &&seasonYear==2023)
			{
				System.out.println("erroorororororor");
				
			}
			if(seasonYear >= 2023) {
				tab = "ecrop"+seasonYear+".rep_vill_wise_cropwise_ext_v_ho";
			}
		
		
		String qry = null;
		 if (cropgrpid.equals("") && cropid .trim().equals("")) {
			 System.out.println("enterdddd11111");
             qry = " select cr_vcode,wbvname, "+display+" as totext from " + tab + " v,cropnames c "
                     + " where dcode="+dcode+" and mcode="+mcode+" and cr_year="+seasonYear+" and cr_season='"+seasonType+"' and v.cr_crop=c.cropid and c.cropnature='H' "
                     + "group by cr_vcode,wbvname  ";
          } 
		 else if (!cropgrpid.equals("") && cropid.equals("")) 
		 {
			 System.out.println("enterdddd22222222");
             qry = " select cr_vcode,wbvname," + display + " as totext from " + tab + "  where dcode="+dcode+" and mcode="+mcode+" and "
                     + "grpcode="+cropgrpid+" and cr_year="+seasonYear+" and cr_season='"+seasonType+"' group by cr_vcode,wbvname  ";
            
         } else if ( cropid != null) {
        	 System.out.println("enterdddd333333");
             qry = "select wbvname,sum(totextent)  as totext  from "+tab+" where dcode="+dcode+" and mcode="+mcode+" and \r\n"
             		+ "cr_crop="+cropid+" and cr_year="+seasonYear+" and cr_season='"+seasonType+"' group by wbvname  ";
            
         }
		 Query Query = (Query) entityManager.createNativeQuery(qry);
	 
		 List<Object[]> details = Query.getResultList();
		 System.out.println("detailsEntities1=>"+details.size());
	     System.out.println("detailsEntities1=>"+details.toString());
		 List<Abstractreport> detailsEntities = new ArrayList<Abstractreport>();
	
		 		try {
		 			for (Object[] row : details) {
		 				Abstractreport entity = new Abstractreport();
		 				
		 				if (cropgrpid.equals("") && cropid .trim().equals("") || (!cropgrpid.equals("") && cropid.equals(""))) {
		 				entity.setWbvname((String) row[1]);
		 				entity.setTotext((BigDecimal) row[2]); 			
		 				detailsEntities.add(entity);
		 				}
		 				else {
		 					entity.setWbvname((String) row[0]);
			 				entity.setTotext((BigDecimal) row[1]); 			
			 				detailsEntities.add(entity);
		 				}
		 			}
		 			System.out.println("detailsEntities===========>" + detailsEntities.size());
		 
		 		} catch (Exception e) {
		 			e.printStackTrace();
		 			System.out.println("erroe==> " + e);
		 		}
		 	
		 		return detailsEntities;
		 

		        
         

	}

}
