package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.Rep_SearchFormbyKhsnoVAModel;
import com.ecrops.entity.VillageSec;
//import com.ecrops.repo.MasterFunctions;
import com.ecrops.service.Rep_searchFormby_KhsnoVAService;
//import com.ecrops.repo.MasterFunctions;

@Service
public class Rep_SerchFormbyKhsnoVAIntfServiceImpl  implements Rep_searchFormby_KhsnoVAService{

	@PersistenceContext
	private EntityManager entityManager;	
//	@Autowired
//	private MasterFunctions masterFunctions; 

	@Override
	public List<Rep_SearchFormbyKhsnoVAModel> getsearchformbykhsnodet(String tab1,
			String  searchParam,Integer dcode,Integer vcode1,Integer year,String season,String surveyno,
			String khno,Integer mandalcode,String vcode)  {
		int search= Integer.parseInt(searchParam);
		
		Query insertQuery=null;
		
		String sql="select  a.bookingid, a.dcode,a.mcode,cr_dist_code,b.wbvname,b.wbmname, cr_vcode,cr_year,cr_season,concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,owner_tenant,"
				+ " oc_name,    oc_fname,    mobileno, age, coalesce(email,' ') as email, kh_no,cr_sno, tot_extent, cultivable_land,uncultivable_land,"
				+ " coalesce(cr_month,0) as cr_month,   cr_sow_type,   cr_crop,   cr_mix_unmix_ext,   cr_no,   cr_w_draw, cr_irr_type, coalesce(wtr_tax,0) as wtr_tax,coalesce(longitude,0) as longitude ,coalesce(latitude,0) as latitude,"
				+ " cropname, data_src,varietyname from "+tab1+" a, wbvillage_mst b "
			   + "	where  CAST(b.wbvcode AS text) = CAST(a.cr_vcode AS text) and a.dcode=? and a.cr_mand_code=? and a.cr_vcode=? and a.cr_year=? and a.cr_season=?  ";

        if (search== 1) {
        	sql += "  and cr_sno=?   order by kh_no,cr_sno,oc_name ";
        	
    		System.out.println(surveyno+"-----qry--------->  "+sql);

    	 insertQuery = (Query)entityManager.createNativeQuery(sql);
    		insertQuery.setParameter(1,  dcode);
   		insertQuery.setParameter(2,  mandalcode);
    		insertQuery.setParameter(3,  vcode1);
    		insertQuery.setParameter(4,  year);
    		insertQuery.setParameter(5,  season);
    		insertQuery.setParameter(6,  surveyno);
   
  }else if (search== 2) {
	  Double kh_no= Double.parseDouble(khno);
	  sql += " and kh_no=?  order by kh_no,cr_sno,oc_name";
	  
		System.out.println("qry--------->  "+sql);

    	 insertQuery = (Query)entityManager.createNativeQuery(sql);

            
    		insertQuery.setParameter(1,  dcode);
    		insertQuery.setParameter(2,  mandalcode);
    		insertQuery.setParameter(3,  vcode1);
    		insertQuery.setParameter(4,  year);
    		insertQuery.setParameter(5,  season);
    		insertQuery.setParameter(6,  kh_no);
    		System.out.println("insertQuery=>"+insertQuery);

        }	
		
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<Rep_SearchFormbyKhsnoVAModel> list = new ArrayList<Rep_SearchFormbyKhsnoVAModel>();
		for(Object[] row: detailsEntities1) {
			Rep_SearchFormbyKhsnoVAModel SearchFormbyKhsnoVA = new Rep_SearchFormbyKhsnoVAModel();	
		
//			String  name= masterFunctions.MasterFunction( dcode + "@" + row[4] , "wbmnamefromdcode");	
//			SearchFormbyKhsnoVA.setMcode(name);	
			
//			String villname = masterFunctions.MasterFunction(vcode,"wbvillage");
//			SearchFormbyKhsnoVA.setCr_vcode(villname);
			
//            <td> <%=util.MasterFunctions.MasterFunction(dcode + "@" + mandalcode, "wbmnamefromdcode")%> </td>
//            <td> <%=util.MasterFunctions.MasterFunction(vill, "wbvillage")%> </td>   	
			SearchFormbyKhsnoVA.setWbmname((String)row[05].toString());
			SearchFormbyKhsnoVA.setWbvname((String)row[04].toString());
			SearchFormbyKhsnoVA.setOc_name((String)row[11].toString());
			SearchFormbyKhsnoVA.setOc_fname((String)row[12].toString());
			SearchFormbyKhsnoVA.setOwner_tenant((String)row[9].toString());
			SearchFormbyKhsnoVA.setKh_no((String)row[16].toString());
			SearchFormbyKhsnoVA.setCr_sno((String)row[17].toString());
			SearchFormbyKhsnoVA.setCropname((String)row[31].toString());
			SearchFormbyKhsnoVA.setMobileno((String)row[13].toString());
			list.add(SearchFormbyKhsnoVA);
		}

		return list;
	}

}
