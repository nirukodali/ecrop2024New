package com.ecrops.service.impl;
import java.util.ArrayList; 
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.Rep_VsPerennialModel;
import com.ecrops.service.Rep_VsPerennialService;

@Service
public class Rep_VsPerennialServiceImpl implements Rep_VsPerennialService {
	@PersistenceContext private EntityManager entityManager;
	@Override
	public List<Rep_VsPerennialModel> getVsPerennialdet(String tab1, String dcode,Integer dcode1, Integer wbmcode, String vcode,
			Integer cropyear, String season, Integer crpid, Integer vcode1) {
	
        if (crpid != 0) {
	
		
String sql="select  bookingid, a.dcode,b.wbmname,cr_dist_code, cr_mand_code,    b.wbvname,    cr_year,    cr_season,   concat('XXXXXXX',substr(cr_farmeruid,9,4)) "
		+ "	as cr_farmeruid, owner_tenant, oc_name,  oc_fname,  mobileno, age,    email,    kh_no,    cr_sno,    tot_extent,     cultivable_land,   "
		+ "	uncultivable_land,cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext,   cr_no,   cr_w_draw,   cr_irr_type,   wtr_tax,   longitude,   latitude,"
		+ "	uploaded,  cropname, data_src  from " + tab1 + "  a,  wbvillage_mst b "
		+ "	where  CAST(b.wbvcode AS text) = CAST(a.cr_vcode AS text)  and a.dcode=? and cr_mand_code=? and cr_vcode=?  and cr_crop=? and cr_year=? and cr_season=? "
		+ "	order by cr_vcode,kh_no,cr_sno,oc_name ";
			
		Query insertQuery = (Query) entityManager.createNativeQuery(sql);		
		insertQuery.setParameter(1, dcode1);
		insertQuery.setParameter(2, wbmcode);
		insertQuery.setParameter(3, vcode1);
		insertQuery.setParameter(4, crpid);
		insertQuery.setParameter(5, cropyear);
		insertQuery.setParameter(6, season);
		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		System.out.println("detailsEntities=>"+detailsEntities1.toString());

		List<Rep_VsPerennialModel> list = new ArrayList<Rep_VsPerennialModel>();
		
		
//		String sql2="SELECT  wbmname FROM wbvillage_mst WHERE dcode=:dcode and wbmcode=:wbmcode";
//		Query insertQuery2 = (Query) entityManager.createNativeQuery(sql2);	

		for(Object[] row: detailsEntities1) {
			Rep_VsPerennialModel VsPerennial = new Rep_VsPerennialModel();
			
//		String  name= masterFunctions.MasterFunction( dcode + "@" + row[4] , "wbmnamefromdcode");	
//			VsPerennial.setMcode(name);
		    VsPerennial.setWbmname((String)row[2].toString());
//			VsPerennial.setCr_vcode((String)row[5].toString());	
		    VsPerennial.setWbvname((String)row[5].toString());
//			String villname = masterFunctions.MasterFunction(vcode,"wbvillage");
//			VsPerennial.setCr_vcode(villname);	
			VsPerennial.setOwner_tenant((String)row[9].toString());
			VsPerennial.setCr_mand_code((String)row[4].toString());
//			VsPerennial.setCr_vcode((String)row[5].toString());
			VsPerennial.setOc_name((String)row[10].toString());
			VsPerennial.setOc_fname((String)row[11].toString());	
			VsPerennial.setKh_no((String)row[15].toString());
			VsPerennial.setCr_sno((String)row[16].toString());
			VsPerennial.setCropname((String)row[31].toString());
			VsPerennial.setCr_mix_unmix_ext((String)row[23].toString());
			VsPerennial.setMobileno((String)row[12].toString());
			VsPerennial.setAge((String)row[13].toString());
			list.add(VsPerennial);
		}
		return list;
        }else {
        	String sql="select b.wbvname, b.wbmname  concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,owner_tenant,"
        			+ " oc_name,    oc_fname,    mobileno,    age,    email,    kh_no,    cr_sno, tot_extent,cultivable_land,uncultivable_land,"
        			+ " cr_month,   cr_sow_type,   cr_crop,   cr_mix_unmix_ext,   cr_no,   cr_w_draw, cr_irr_type, wtr_tax,longitude,latitude,"
        			+ " uploaded,  cropname, data_src  from " + tab1 + " a, JOIN  wbvillage_mst b   where CAST(b.wbvcode AS text) = CAST(a.cr_vcode AS text) and dcode=? and "
        			+ "cr_mand_code=? and cr_vcode=?  and cr_year=? and cr_season=? order by cr_vcode,kh_no,cr_sno,oc_name";

    		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
    		
    		insertQuery.setParameter(1, dcode1);
    		insertQuery.setParameter(2, wbmcode);
    		insertQuery.setParameter(3, vcode1);
    		insertQuery.setParameter(4, cropyear);
    		insertQuery.setParameter(5, season);    	 
    	
    		System.out.println("insertQuery=>"+insertQuery);
    		List<Object[]> detailsEntities1 = insertQuery.getResultList();
    		System.out.println("detailsEntities=>"+detailsEntities1.size());
    		
    		List<Rep_VsPerennialModel> list = new ArrayList<Rep_VsPerennialModel>();
    		

    		for(Object[] row: detailsEntities1) {
    			Rep_VsPerennialModel VsPerennial = new Rep_VsPerennialModel();
    		
//    			String  name= masterFunctions.MasterFunction( dcode + "@" + row[4] , "wbmnamefromdcode");	
//    			VsPerennial.setMcode(name);
    		
    			VsPerennial.setWbmname((String)row[1].toString());
    			
    			VsPerennial.setWbvname((String)row[0].toString());
	 			
//    			String villname = masterFunctions.MasterFunction(vcode,"wbvillage");
//    			VsPerennial.setCr_vcode(villname);
	
    			VsPerennial.setOwner_tenant((String)row[9].toString());
    			VsPerennial.setCr_mand_code((String)row[4].toString());
//    			VsPerennial.setCr_vcode((String)row[5].toString());
    			VsPerennial.setOc_name((String)row[10].toString());
    			VsPerennial.setOc_fname((String)row[11].toString());	
    			VsPerennial.setKh_no((String)row[15].toString());
    			VsPerennial.setCr_sno((String)row[16].toString());
    			VsPerennial.setCr_mix_unmix_ext((String)row[23].toString());
    			VsPerennial.setMobileno((String)row[12].toString());
    			VsPerennial.setAge((String)row[13].toString());
    			list.add(VsPerennial);
    		}	
	
    		return list;
    		
        }	
	}
			
}
