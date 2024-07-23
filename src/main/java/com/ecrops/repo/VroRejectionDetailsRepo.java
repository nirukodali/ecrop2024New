package com.ecrops.repo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ecrops.entity.CrVarietyMaster;
import com.ecrops.entity.CropName;
import com.ecrops.entity.VroRejDetails;
import com.ecrops.entity.VroRejReasons;
import com.ecrops.entity.WbVillageMst;

@Repository
public class VroRejectionDetailsRepo  {
	
		@PersistenceContext
		private EntityManager entitymanger;
	
	    public  List<VroRejDetails> getCropwise(int vcode,String cr_year) {
	    	String season = cr_year.split("@")[0]; 
			String year = cr_year.split("@")[1]; 
			
			String vrorejdetails = "vro_rej_details";
			
			if(Integer.parseInt(year)>= 2023 && !(Integer.parseInt(year)== 2023 && season.equalsIgnoreCase("S")))
			vrorejdetails = "ecrop" + year + "." + vrorejdetails;

			String qry = "select  \r\n"
			        + "m.wbvname,\r\n"
			        + "a.cr_dist_code,\r\n"
			        + "a.cr_mand_code,\r\n"
			        + "a.cr_vcode,\r\n"
			        + "a.bookingid,\r\n"
			        + "a.cr_no,\r\n"
			        + "c.cropname,\r\n"
			        + "d.varietyname,\r\n"
			        + "a.cr_sow_dt,\r\n"
			        + "a.cr_sno,\r\n"
			        + "a.kh_no,\r\n"
			        + "a.rej_reason,\r\n"
			        + "b.reason\r\n"
			        + "from " + vrorejdetails + " a, vro_rej_reasons b, cropnames c, cr_variety_master d, wbvillage_mst m, activeseason s \r\n"
			        + "where  a.rej_reason=b.code and a.cr_crop=c.cropid and a.cr_crop=d.cropcode and a.variety=d.varietycode and a.cr_vcode=m.wbvcode \r\n"
			        + "and cr_vcode in (select \r\n"
			        + "                vcode \r\n"
			        + "                from vs_rev_villages \r\n"
			        + "                where vscode='" + vcode + "') and a.part_key like 'R%' and s.season='" + season + "' and s.cropyear=" + year;

            Query query = entitymanger.createNativeQuery(qry);
			List<Object[]> result = query.getResultList();
			
			List<VroRejDetails> list = new ArrayList<VroRejDetails>();
			for (Object[] bean : result) 
			{
				VroRejDetails cropwiseExtModel = new VroRejDetails();
				WbVillageMst villageMaster = new WbVillageMst();
				CrVarietyMaster crVarietyMaster=new CrVarietyMaster();
				VroRejReasons reasons = new VroRejReasons();
				CropName cropName=new CropName();
				         villageMaster.setWbvname((String)bean[0]);
			    cropwiseExtModel.setBookingid((Integer) bean[4]);
			    cropwiseExtModel.setKh_no(((Integer) bean[10])); // Convert BigInteger to long
			    //cropwiseExtModel.setTotalbookings(((BigInteger) bean[3]).longValue());
			    cropwiseExtModel.setCr_sno((String) bean[9]);
			    cropwiseExtModel.setCr_no((String) bean[5]);
			    cropName.setCropname(((String) bean[6]));
			    crVarietyMaster.setVarietyname(((String) bean[7]));
			    cropwiseExtModel.setCr_sow_dt((Date) bean[8]); 
			    reasons.setReason((String) bean[12]);
				cropwiseExtModel.setVillageMaster(villageMaster);
				cropwiseExtModel.setVarietyMaster(crVarietyMaster);
				cropwiseExtModel.setCropName(cropName);
				cropwiseExtModel.setRejectionReason(reasons);

			   list.add(cropwiseExtModel);
			  
			}
			System.out.println("list---------------->"+list);
	
			return list;
	}
	    
	    
}
