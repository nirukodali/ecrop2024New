package com.ecrops.repo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.DistwiseStatusRepForAllCrops;

@Repository
public class DistwiseStatusRepForAllCropsRepo  {
	
		@PersistenceContext
		private EntityManager entitymanger;
	
	    public  List<DistwiseStatusRepForAllCrops> getCropwise(String sescrpyear) {
	    	
	    	String cr_season = sescrpyear.split("@")[0];
	    	
			String cr_year = sescrpyear.split("@")[1]; 
			
			String t1 = "distwisestat_"+cr_season+cr_year;
			 
			if(Integer.parseInt(cr_year)>=2023 && !(Integer.parseInt(cr_year)==2023 && cr_season.equalsIgnoreCase("S"))) {
				t1="ecrop"+cr_year+"."+ "distwisestat_"+cr_season+cr_year;
			}
			
			System.out.println("season---------"+cr_season);
			System.out.println("season---------"+cr_year);
			System.out.println("t1---------"+t1);
			
			String qry = "select distcode,wbdname,tmandals,tvillages,dagri_mandals,dagri_villages, \r\n"
					+ "dagrinormalarea,dagri_farmers,dagri_ext,dhorti_mandals,dhorti_villages, \r\n"
					+ "dhortinormalarea,dhorti_farmers,dhorti_ext,dfish_mandals,dfish_villages,\r\n"
					+ "dfish_farmers,dfish_ext,dseri_mandals,dseri_villages,dseri_farmers,dseri_ext,\r\n"
					+ "0 as dsnormalarea,0 as dfisheriesnormalarea,0 as dfnormalarea,dsoc_farmers,dsoc_ext,dsoc_villages,dsoc_mandals,\r\n"
					+ "0 as dsocnormalarea from "+t1;
			
			Query query = entitymanger.createNativeQuery(qry);
			List<Object[]> result = query.getResultList();
			
		
			List<DistwiseStatusRepForAllCrops> list = new ArrayList<DistwiseStatusRepForAllCrops>();
			for (Object[] bean : result) 
			{
				DistwiseStatusRepForAllCrops cropwiseExtModel = new DistwiseStatusRepForAllCrops();
				System.out.println(bean[1]+"bean---------"+bean[0]);
				cropwiseExtModel.setDistcode((BigDecimal) bean[0]); 
			    cropwiseExtModel.setWbdname((String) bean[1]);
			    cropwiseExtModel.setTmandals(((BigInteger) bean[2]).longValue()); // Convert BigInteger to long
			    cropwiseExtModel.setTvillages(((BigInteger) bean[3]).longValue());
			    cropwiseExtModel.setDagri_mandals(((BigInteger) bean[4]).longValue());
			    cropwiseExtModel.setDagri_villages(((BigInteger) bean[5]).longValue());
			    cropwiseExtModel.setDagri_ext((BigDecimal) bean[8]); 
			    cropwiseExtModel.setDagri_farmers(((BigInteger) bean[7]).longValue());
			    cropwiseExtModel.setDhorti_mandals(((BigInteger) bean[9]).longValue());
			    cropwiseExtModel.setDhorti_villages(((BigInteger) bean[10]).longValue());
			    cropwiseExtModel.setDhorti_ext(((BigDecimal) bean[13]).longValue());
			    cropwiseExtModel.setDhorti_farmers(((BigInteger) bean[12]).longValue());
			    cropwiseExtModel.setDseri_mandals(((BigInteger) bean[18]).longValue());
			    cropwiseExtModel.setDseri_villages(((BigInteger) bean[19]).longValue());
			    cropwiseExtModel.setDseri_ext((BigDecimal) bean[21]); 
			    cropwiseExtModel.setDseri_farmers(((BigInteger) bean[20]).longValue());
			    cropwiseExtModel.setDsoc_mandals(((BigInteger) bean[28]).longValue());
			    cropwiseExtModel.setDsoc_villages(((BigInteger) bean[27]).longValue());
			    cropwiseExtModel.setDsoc_ext((BigDecimal) bean[26]);
			    cropwiseExtModel.setDsoc_farmers(((BigInteger) bean[27]).longValue());



			   list.add(cropwiseExtModel);
			  
			}
			System.out.println("list---------------->"+list);
	
			return list;
	}
	    
	    
}