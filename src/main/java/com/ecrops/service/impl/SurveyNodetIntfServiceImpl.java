package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.SurveyNodetIntfModel;
import com.ecrops.entity.nonwebViewModel;
import com.ecrops.service.RepSurveyNodetIntfService;

@Service
public class SurveyNodetIntfServiceImpl implements RepSurveyNodetIntfService{

	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public List<SurveyNodetIntfModel> getSurveyNodet(String table, String tab2,String rbksrnoMapTab, String userid, Integer vcode1, String choiceId) {
		String qry = "";
        if(choiceId.equals("1")){ 
    
          qry="select cr_dist_code,cr_mand_code,b.wbvname,concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,farmername,fathername,occupname,occupfname,kh_no,cr_sno,tot_extent,occup_extent,regno,'-' as cultivator_type,"
          		+ "   sjointoccupant,cultivable_land,uncultivable_land,a.dcode,a.mcode,coalesce(a.mobileno,0) AS mobileno,part_key"
          		+ " from "+table+" a JOIN  wbvillage_mst b ON CAST(b.wbvcode AS text) = CAST(a.cr_vcode AS text)"
          		+ " where cr_vcode=? and  kh_no not in (select code from obj_unobj where trim(crb_remarks) in ('No'))"
          		+ " and (cr_vcode,cr_sno,kh_no)  in (select cr_vcode,cr_sno,kh_no from "+tab2+" where vs_sel= 'Y' and cr_vcode="+vcode1+" and srno_userid=?) order by kh_no,cr_sno"	
          		+ "";
          
         
        }else if(choiceId.equals("2")){
            
            
              
       qry=" select cr_dist_code,cr_mand_code,b.wbvname,concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,farmername,fathername,occupname,occupfname,kh_no,cr_sno,tot_extent,occup_extent,regno,"
                     + " sjointoccupant,cultivable_land,uncultivable_land,a.dcode,a.mcode,coalesce(a.mobileno,0) AS mobileno,part_key,'-' as cultivator_type "+
                    " from "+table+" a JOIN  wbvillage_mst b ON CAST(b.wbvcode AS text) = CAST(a.cr_vcode AS text) where cr_vcode="+vcode1+"  and  kh_no not in (select code from obj_unobj where trim(crb_remarks) in ('No')) "
                     + " and (cr_vcode,cr_sno,kh_no) not in (select cr_vcode,cr_sno,kh_no from "+tab2+" where cr_vcode="+vcode1+" and srno_userid='"+userid+"') and (cr_vcode,cr_sno,kh_no)  in (select vcode,cr_sno,kh_no from "+rbksrnoMapTab+" where vcode=? and rbkuserid=? )order by kh_no,cr_sno ";
  
             
                  
        }else if(choiceId.equals("3")){
            
           
             qry = "  select cr_dist_code,cr_mand_code,b.wbvname,concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,oc_name as farmername,oc_fname as fathername,occupname,occupfname,kh_no,cr_sno,tot_extent,occupant_extent as occup_extent,regno,cultivator_type,"
                     + " sjointoccupant,0 as cultivable_land,0 as uncultivable_land,a.dcode,a.mcode,coalesce(a.mobileno,0) AS mobileno,part_key ,vs_sel  "+
                    " from "+tab2+" a JOIN  wbvillage_mst b ON CAST(b.wbvcode AS text) = CAST(a.cr_vcode AS text) where cr_vcode=? and srno_userid=? and cr_sno like 'ROFR%' order by  cr_sno ";      
             
        }
             
else if(choiceId.equals("4")){
            
             
             qry="select cr_dist_code,cr_mand_code,b.wbvname,concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,oc_name as farmername,oc_fname as fathername,occupname,occupfname,kh_no,cr_sno,tot_extent,occupant_extent as occup_extent,regno,cultivator_type,"
             		+ " sjointoccupant,0 as cultivable_land,0 as uncultivable_land,a.dcode,a.mcode,coalesce(a.mobileno,0) AS mobileno,part_key ,vs_sel  "+
             		" from  "+tab2+" a JOIN  wbvillage_mst b ON CAST(b.wbvcode AS text) = CAST(a.cr_vcode AS text) where cr_vcode=? and srno_userid=?  and cr_sno like 'US-%' order by  cr_sno ";
             		
             		
        }
               
else if(choiceId.equals("5")){
             
  qry =   " select cr_dist_code,cr_mand_code,b.wbvname,concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,oc_name as farmername,oc_fname as fathername,occupname,occupfname,kh_no,cr_sno,tot_extent,occupant_extent as occup_extent,regno,cultivator_type,"
              + " sjointoccupant,0 as cultivable_land,0 as uncultivable_land,a.dcode,a.mcode,coalesce(a.mobileno,0) AS mobileno,part_key ,vs_sel  "+
  		        " from "+tab2+" a JOIN  wbvillage_mst b ON CAST(b.wbvcode AS text) = CAST(a.cr_vcode AS text) where cr_vcode=?  and srno_userid=? and cultivator_type is not null order by  cr_sno ";

      
      
        }
        System.out.println("Query:::" +qry );
        Query insertQuery = (Query) entityManager.createNativeQuery(qry);
 
        if (choiceId.equals("1") || choiceId.equals("2") || choiceId.equals("3")|| choiceId.equals("4")|| choiceId.equals("5")) {
            insertQuery.setParameter(1, vcode1);
    		insertQuery.setParameter(2, userid);
     
            
        } else {
        	insertQuery.setParameter(1, vcode1);
        }

		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<SurveyNodetIntfModel> list = new ArrayList<SurveyNodetIntfModel>();
		
		for(Object[] row: detailsEntities1) {
			SurveyNodetIntfModel surveyNodetIntf = new SurveyNodetIntfModel();
			surveyNodetIntf.setCr_farmeruid((String)row[3].toString());
			surveyNodetIntf.setCr_sno((String)row[9].toString());
			surveyNodetIntf.setKh_no((String)row[8].toString());
			surveyNodetIntf.setOccupname((String)row[6].toString());
			surveyNodetIntf.setOccupfname((String)row[7].toString());
			surveyNodetIntf.setFarmername((String)row[4].toString());
			surveyNodetIntf.setFathername((String)row[5].toString());
			surveyNodetIntf.setTot_extent((String)row[10].toString());
			surveyNodetIntf.setOccup_extent((String)row[11].toString());
			surveyNodetIntf.setWbvname((String)row[2].toString());
//			surveyNodetIntf.setReason(reason);	
			surveyNodetIntf.setRegno((String)row[12].toString());
			surveyNodetIntf.setSjointoccupant((String)row[14].toString());
			surveyNodetIntf.setCultivable_land((String)row[15].toString());
			surveyNodetIntf.setUncultivable_land((String)row[16].toString());
			surveyNodetIntf.setDcode((String)row[17].toString());
			surveyNodetIntf.setMcode((String)row[18].toString());
			surveyNodetIntf.setMobileno((String)row[19].toString());
			surveyNodetIntf.setPart_key((String)row[20].toString());
//			surveyNodetIntf.set((String)row[4].toString());
			surveyNodetIntf.setCr_dist_code((String)row[0].toString());
			surveyNodetIntf.setCr_mand_code((String)row[1].toString());
			surveyNodetIntf.setCultivator_type((String)row[13].toString());
			String CultivatorType = (String)row[13].toString();
			System.out.println("CultivatorType::::" +CultivatorType);
			if(CultivatorType.equalsIgnoreCase("O")) { 
			
				surveyNodetIntf.setCultivator_type("Pattadar");
			}else if(CultivatorType.equalsIgnoreCase("L")) {
				surveyNodetIntf.setCultivator_type("Enjoyer");
			}else if(CultivatorType.equalsIgnoreCase("K")) {
				surveyNodetIntf.setCultivator_type("Cultivator");
			}else {
				surveyNodetIntf.setCultivator_type("-");
			}		
			list.add(surveyNodetIntf);
		}			
		return list;	
	}
	

}
