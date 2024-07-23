package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ecrops.dto.ObjectionalLandPojo;

import com.ecrops.entity.ObjectionallandtempEntity;
import com.ecrops.entity.PattadharPojoLand;
import com.ecrops.entity.Villsec_revEntity;

@Component
public class LandPattadarDetailsRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
//	public List<PattadharPojo> getLandPattadardetails(Integer dcode, Integer mcode,Integer vcode,Integer khano){
//		
//		String detailQry = null;
//		detailQry = "select dcode,mcode,cr_vcode,cropyear,season,"
//				+ " kh_no,farmername,fathername,mobileno,tot_extent,occupname,cr_sno"
//				+ " from  ecrop2023.pattadarmast_wb_partition_r012023"
//				+ "  where dcode=? and mcode=? and cr_vcode=? and kh_no = ? ";
//	
//	Query sql = entityManager.createNativeQuery(detailQry);
//	sql.setParameter(1, dcode);
//	sql.setParameter(2, mcode);
//	sql.setParameter(3, vcode);
//	sql.setParameter(4, khano);
//	List<Object[]> pattadardetails = sql.getResultList();
//	List<PattadharPojo> pojoList= new ArrayList<>();
//	for (Object[] list : pattadardetails) {
//		PattadharPojo pojo = new PattadharPojo();
//		
//		Object[] value = list;
//		pojo.setFarmerName((String) value[6]);
//		pojo.setFatherName((String) value[7]);
//		pojo.setKhatNo((BigDecimal) value[5]);
//		pojo.setTotalExtent((BigDecimal) value[9]);
//		pojo.setOccupName((String) value[10]);
//		pojo.setSurveyNo((String) value[11]);;
//		pojoList.add(pojo);
//	}
//	
//	return pojoList;
//
//		
//	}
//	
	
	public List<Villsec_revEntity> getvillagedrpdwn(Integer vscode) {
		// TODO Auto-generated method stub
	String sql = "select distinct vcode,wbvname  from ecrop2024.villsec_rev_v where vscode=? order by wbvname";	
		
			Query insertQuery = (Query)entityManager.createNativeQuery(sql);
			insertQuery.setParameter(1,  vscode);
			System.out.println("insertQuery=>"+insertQuery);
			List<Object[]> detailsEntities1 = insertQuery.getResultList();
			System.out.println("detailsEntities=>"+detailsEntities1.size());
			
			List<Villsec_revEntity> list = new ArrayList<Villsec_revEntity>();
			for(Object[] row: detailsEntities1) {
				Villsec_revEntity villSec = new Villsec_revEntity(sql, sql);
				villSec.setVscode((String)row[0].toString());
				villSec.setVsname((String)row[1].toString());
				list.add(villSec);
			}
			System.out.println("list=>"+list.size());
			return list;
	
	}
	
	public List<PattadharPojoLand> getkhatanos(String partitionName, Integer vcode) {
//	    String surveyQry = "select distinct kh_no, cr_vcode from " + partitionName +
//	                       " where cr_vcode=:vcode and kh_no in (select code from obj_unobj  WHERE crb_remarks NOT IN ('No')) ";
//	    
	    String surveyQry = "select distinct kh_no, cr_vcode from " + partitionName +
                " where cr_vcode=:vcode and kh_no  in (select code from obj_unobj  WHERE trim(crb_remarks) in ('No')) ";


	    Query sql = entityManager.createNativeQuery(surveyQry);
	    sql.setParameter("vcode", vcode);

	    List<Object[]> khatadetails = sql.getResultList();
	    List<PattadharPojoLand> khatList = new ArrayList<>();

	    for (Object[] list : khatadetails) {
	        PattadharPojoLand pojo = new PattadharPojoLand();
	        
	        BigDecimal khNo = (BigDecimal) list[0];
	        Integer crVcode = (Integer) list[1];
	        
	        pojo.setKhatNo(khNo);
	        pojo.setCr_vcode(crVcode);    

	        khatList.add(pojo);
	    }

	    return khatList; 
	}

	
	 public List<PattadharPojoLand> getsurveynodetails(String partitionName, Integer vcode, int khatano) {
	        String surveyQry = "select distinct cr_vcode, kh_no, cr_sno,rec_id,occup_extent from " + partitionName + " where cr_vcode = :vcode and kh_no = :khatano and  (kh_no,cr_vcode,cr_sno) not in (select kh_no,cr_vcode,cr_sno  from ecrop2024.enable_obj_lands  where cr_vcode=:vcode )";

	        Query sql = entityManager.createNativeQuery(surveyQry);

	        sql.setParameter("vcode", vcode);
	        sql.setParameter("khatano",khatano);
	        
	        List<Object[]> pattadardetails = sql.getResultList();
	    	List<PattadharPojoLand> pojoList= new ArrayList<>();
	    	for (Object[] list : pattadardetails) {
	    		PattadharPojoLand pojo = new PattadharPojoLand();
	    		
	    		Object[] value = list;
	    		
	    		pojo.setKhatNo((BigDecimal) value[1]);
//	    		System.out.println((String) value[2]);
	    		pojo.setCr_vcode((Integer) value[0]);	
	    		pojo.setSurveyNo((String) value[2]);;
	    		pojo.setRec_id((Integer) value[3]);
	    		pojo.setOccup_extent((BigDecimal) value[4]);
	    		pojoList.add(pojo);
	    	}
	        return pojoList; 
	    }
	 
	 public List<PattadharPojoLand> validateextent(String partitionName, Integer vcode, String surveyno, int khatano, String season) {
		    String extentqry = "select cr_vcode, kh_no, cr_sno , occup_extent, tot_extent from " + partitionName + " where cr_vcode = :vcode and kh_no = :khatano and cr_sno =:surveyno and season=:season";
		    System.out.println("validateqry: " + extentqry);
		    
		    Query sql = entityManager.createNativeQuery(extentqry);
		    sql.setParameter("vcode", vcode);
		    sql.setParameter("khatano", khatano);
		    sql.setParameter("surveyno", surveyno);
		    sql.setParameter("season", season);
		    
		    List<Object[]> extentdetails = sql.getResultList();
		    System.out.println("vcode-------------------------" +vcode);
		    System.out.println("khatano-------------------------" +khatano);
		    System.out.println("surveyno-------------------------" +surveyno);
		    System.out.println("season-------------------------" +season);
		    System.out.println("extentdetails size: " + extentdetails.size());
		    
		    List<PattadharPojoLand> pojoList = new ArrayList<>();
		    for (Object[] list : extentdetails) {
		        PattadharPojoLand pojo = new PattadharPojoLand();
		        
		        pojo.setKhatNo((BigDecimal) list[1]);
		        System.out.println("-----------------------------------------------------------------" + (String) list[2]);
		        pojo.setCr_vcode((Integer) list[0]);
		        pojo.setSurveyNo((String) list[2]);
		        pojo.setOccup_extent((BigDecimal) list[3]);
		        pojo.setTotalExtent((BigDecimal) list[4]);
		        
		        pojoList.add(pojo);
		    }
		    return pojoList;
		}
	 public List<ObjectionalLandPojo> getobjdetails(int vscode,String activeyear) {
		
		 System.out.println("activeyear is ::"+activeyear);
		 String enableObjTab="ecrop"+activeyear+".enable_obj_lands";
		 
		
		 
	        String surveyQry = "select wbvname,sug_ext,kh_no,cr_sno,wbvcode,occup_extent,mao_sts,mro_sts,dao_sts,jc_sts from "+enableObjTab+" a,wbvillage_mst b where a.cr_vcode=b.wbvcode and a.vscode=? ";

	        Query objsql = entityManager.createNativeQuery(surveyQry);
	        objsql.setParameter(1, vscode);
	       

	        List<Object[]> objdetails = objsql.getResultList();
	    	List<ObjectionalLandPojo> objList= new ArrayList<>();
	    	for (Object[] list : objdetails) {
	    		ObjectionalLandPojo obj = new ObjectionalLandPojo();
	    		
	    		Object[] value = list;
	    		
	    		obj.setWbvname((String) value[0]);
	    		obj.setSug_ext((BigDecimal) value[1]);
	    		obj.setKh_no((BigDecimal) value[2]);
	    		obj.setCr_sno((String) value[3]);	
	    		obj.setWbvcode((Integer) value[4]);
	    		obj.setOccup_extent((BigDecimal) value[5]);
	    		obj.setMao_sts(list[6] != null ? list[6].toString() : null);
    		    obj.setMro_sts(list[7] != null ? list[7].toString() : null);
    		    obj.setDao_sts(list[8] != null ? list[8].toString() : null);
    		    obj.setJc_sts(list[9] != null ? list[9].toString() : null);
    		    
	    		System.out.println((BigDecimal) value[5]);
	    		objList.add(obj);
	    	}
	        return objList; 
		
	 }
	 
	 
	 @Transactional
	 public int updatesugest(String survey,Integer khatano,Integer vcode,String activeyear){
		 String enableObjTab="ecrop"+activeyear+".enable_obj_lands";
		 
			
		 String updateqry = " delete from "+enableObjTab+" where cr_vcode=? and kh_no=? and cr_sno=? and mao_remarks is null and mao_sts is null";
//		 System.out.println("suvey----------------------------------------------"+survey);
//		 System.out.println("khatano-------------------------------------------------------"+khatano);
//		 System.out.println("vcoce-------------------------------------"+vcode);
		 Query sql = entityManager.createNativeQuery(updateqry);
			sql.setParameter(1, vcode);		
			sql.setParameter(2, khatano);
			sql.setParameter(3, survey.toString());
			int executeUpdate = sql.executeUpdate();
			return executeUpdate;
		
		 
	 }
	
}

