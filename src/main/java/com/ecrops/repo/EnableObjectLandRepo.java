package com.ecrops.repo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.ecrops.dto.EnableObjectLandPojo;
import com.ecrops.dto.PattadharPojo;
import com.ecrops.entity.PattadarMasterEntity;

import java.util.List;

@Repository
public class EnableObjectLandRepo {

	@PersistenceContext
	EntityManager entityManager;

	

	public List<EnableObjectLandPojo> eolList(Integer dcode, Integer mcode, Integer role,HttpSession httpSession) {
		// TODO Auto-generated method stub
		String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();
		String var = "SELECT wbmname,category,b.wbvname,b.vsname, kh_no, \r\n" + "cr_sno,  occup_extent, \r\n"
				+ "  rec_id,sug_ext,\r\n"
				+ "mro_sts, mro_remarks, mro_upd_sts, dao_sts, dao_remarks, dao_upd_ts, jc_sts, jc_remarks, jc_upd_ts, mao_remarks, mao_sts, mao_upd_sts\r\n"
				+ "	FROM ecrop"+activeYear+".enable_obj_lands a, ecrop"+activeYear+".villsec_rev_v b ,obj_unobj c  where a.kh_no=c.code and   a.vscode=b.vscode ";
		if (role.toString().equals("5")) {
		var += " and a.mcode=" + mcode + " and a.dcode=" + dcode + " and mao_sts is null";
		} else if (role.toString().equals("2")) {

			var += " and a.mcode=" + mcode + " and a.dcode=" + dcode + " and mao_sts ='A' and mro_sts is null ";
		} else if (role.toString().equals("9")) {
			// Dao
			var += " and  a.dcode=" + dcode + " and mro_sts ='A' and dao_sts is null";
		} else if (role.toString().equals("44")) {
			// jc
			var += " and a.dcode=" + dcode + " and dao_sts ='A' and jc_sts is null";
		}

		List<EnableObjectLandPojo> pojo = new ArrayList<>();

		Query query = entityManager.createNativeQuery(var);//System.out.println("query is:"+query);System.out.println("objland qry:"+var);
		List<Object> objects = query.getResultList();

		if (objects != null && objects.size() > 0) {
			System.out.println("jjj");
			for (Object eol : objects) {

				Object[] row = (Object[]) eol;

				EnableObjectLandPojo pojos = new EnableObjectLandPojo();
				pojos.setMname(row[0].toString());
				pojos.setCategory(row[1].toString());

				pojos.setVname(row[2].toString());
				pojos.setRbkname(row[3].toString());

				pojos.setRecId((Integer) row[7]);

				pojos.setKhNo((BigDecimal) row[4]);
				pojos.setCrSno((String) row[5]);
				;
				pojos.setOccupExtent((BigDecimal) row[6]);
				pojos.setRequestExtent((BigDecimal) row[8]);

				pojo.add(pojos);
			}
		}

		return pojo;

	}
    
	@Transactional
	public String acceptingData(int id, String role, String status, String remarks,HttpSession httpSession) {
		System.out.println(id+role+status);
		String query="";
		String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();
		String activeSeason= (String) httpSession.getAttribute("seasonActive");
		String wbdcode= httpSession.getAttribute("wbdcode").toString();
		if(httpSession.getAttribute("wbmcode")!=null) {
		String wmcode= httpSession.getAttribute("wbmcode").toString();
		}
		String table="update ecrop"+activeYear+".enable_obj_lands";
		if (wbdcode.length() == 1) {
			wbdcode = "0" + wbdcode;
		}

	
		String table2="update ecrop"+activeYear+".pattadarmast_wb_partition_"+activeSeason+wbdcode+activeYear;
		String query2="";
		String message="";
		if(role.equals("5") ) {
		 query=	""+table+" set mao_sts =?, mao_remarks=? , mao_upd_sts=now() where rec_Id=?";
		}
		else if(role.equals("2")) {
			query=	""+table+" set mro_sts =?, mro_remarks=? , mro_upd_sts=now() where rec_Id=?";
		}
		else if(role.equals("9")) {
			query=	""+table+" set dao_sts =?, dao_remarks=? , dao_upd_ts=now() where rec_Id=?";
		}else if(role.equals("44")) {
			query=	""+table+" set jc_sts =?, jc_remarks=? , jc_upd_ts=now() where rec_Id=?";
			
		}
		Query qu = entityManager.createNativeQuery(query);
	       if(status.equals("A")) {
		qu.setParameter(1,status);
		qu.setParameter(2,status );
         message="Accepted successfully";
	       }else {
	    		qu.setParameter(1,status);
	    		qu.setParameter(2,remarks );
	    		  message="Rejected successfully";
	       }
		qu.setParameter(3,id);
	
		int count = qu.executeUpdate();
		if(count==1  && role.equals("44")) {
			   if(status.equals("A") && role.equals("44")) {
					message="Accepted successfully";
					
//					query2="update ecrop2024.pattadarmast_wb_partition_k012024 set allowObj_flag='Y' , req_ext=1.44 where rec_id=2127388";
					query2=""+table2+" set allowObj_flag='Y' , req_ext=? where rec_id=? ";
					Query quer = entityManager.createNativeQuery(query2);
					
					String query3= "select sug_ext from   ecrop"+activeYear+".enable_obj_lands where rec_id="+id+"";
					Query quer3 = entityManager.createNativeQuery(query3);
				  BigDecimal sugext=	(BigDecimal) quer3.getSingleResult();
					quer.setParameter(1,sugext);
					quer.setParameter(2,id );
					quer.executeUpdate();
				       }else {
				    		message="Rejected successfully";
				       }
		}
		return message;
	  

	}

}
