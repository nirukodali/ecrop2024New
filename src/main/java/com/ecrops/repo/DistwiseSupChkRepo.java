package com.ecrops.repo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;
import com.ecrops.entity.DistwiseSupChk;

@Repository
public class DistwiseSupChkRepo  {
	
		@PersistenceContext
		private EntityManager entitymanger;
	
	    public  List<DistwiseSupChk> getCropwise(HttpSession session) {
	    	
	    	String cr_season =session.getAttribute("seasonActive").toString();
	    	
		String cr_year = session.getAttribute("ACTIVEYEAR").toString();
			
			String t1 ="ecrop"+cr_year+".supercheck_status_"+cr_season+cr_year;
			
			System.out.println("----------------"+t1);
			
			
			String qry = "select cr_dist_code,dname,dao_allotted,dao_approved,dao_rejected,dho_allotted,dho_approved,dho_rejected,rdo_allotted,\r\n"
					+ "rdo_approved,rdo_rejected,ada_allotted,ada_approved,ada_rejected,tah_allotted,tah_approved,tah_rejected,mao_allotted,\r\n"
					+ "mao_approved,mao_rejected,ho_allotted,ho_approved,ho_rejected,dc_allotted,dc_approved,dc_rejected,jc_allotted,jc_approved,\r\n"
					+ "jc_rejected  from "+t1+" order by dname";
			
			Query query = entitymanger.createNativeQuery(qry);
			List<Object[]> result = query.getResultList();
			
		
			List<DistwiseSupChk> list = new ArrayList<DistwiseSupChk>();
			for (Object[] bean : result) 
			{
				DistwiseSupChk cropwiseExtModel = new DistwiseSupChk();
				System.out.println(bean[1]+"bean---------"+bean[0]);
				cropwiseExtModel.setCr_dist_code((BigDecimal)bean[0]);
				cropwiseExtModel.setDname((String) bean[1]);
				cropwiseExtModel.setDho_allotted(((BigInteger) bean[5]).longValue());
				cropwiseExtModel.setHo_allotted(((BigInteger) bean[20]).longValue());

			   list.add(cropwiseExtModel);
			  
			}
			System.out.println("list---------------->"+list);
	
			return list;
	}
	    
	    
}