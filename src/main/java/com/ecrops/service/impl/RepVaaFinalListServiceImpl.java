package com.ecrops.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.RepPerFrwdDetModel;
import com.ecrops.entity.RepVaaFinalListModel;
import com.ecrops.service.RepVaaFinalListService;

@Service
public class RepVaaFinalListServiceImpl  implements RepVaaFinalListService{

	
	@PersistenceContext
	private EntityManager entitymanager;
	
	
	@Override
	public List<RepVaaFinalListModel> getVaafinallist(String tab1,String userid,Integer vcode1,Integer year,String season) {
		// TODO Auto-generated method stub
		
		String sql="select a.cr_no,ct.cultdesc_loclang,cultdesc,ftype_short,v.varietyname,occupname,occupfname,to_char(cr_sow_date, 'DD-MM-YYYY') as sdate,"
				+ "  data_src,b.wbdname,b.wbmname,b.wbvname,a.kh_no,a.bookingid,a.vaaauth,a.vroauth,a.ekyc,cn.naturedesc,mao_unlock_appr,"
				+ "  coalesce(length(supercheck_userid),0) as sclength,'XXXXXXXX'||substr(cr_farmeruid,9) as cr_farmeruid,seed_production,a.cr_sno,a.cr_mix_unmix_ext,"
				+ "  a.cropins,c.cropname,w.wsrcdesc, cr_season||','||cr_year as cropduration, a.ins_scheme,a.smsmobileno,supercheck_userid,irrmethod"
				+ "  from " + tab1 + " a,wbvillage_mst_v b,waterresources w ,cropnames c,cr_variety_master v,cultivator_types ct,cropnature cn,cropseed_scheme cs "
				+ "   where a.cr_vcode=b.wbvcode and a.cultivator_type=ct.culttype and a.cr_sow_type=cn.id and updatedby=? and cr_vcode=? and "
				+ "  a.variety=v.varietycode and a.cr_crop=c.cropid and a.cr_w_draw=w.wsrcid and a.cropseed_scheme=cs.cropschtype  and ekyc = 'Y' and vaaauth='Y' and vroauth='Y' order by  kh_no";
//				+ " extent_upd<>'Z' or a.extent_upd is null) and cr_mix_unmix_ext>0 and a.social_status is null and (mark_delet not in('D') or mark_delet is null)"
//				+ " in(select bookingid from \" + supercheckupd + \" where cropyear=? and Season=?  and vaa_sup_rem is null \"\r\n"
//				+ "or (vaa_sup_rem is not null and (mao_remarks='D' or mro_remarks='D') ) or (vaa_sup_rem is not null and (mao_remarks is null or mro_remarks is null)))";
		
		Query insertQuery = (Query) entitymanager.createNativeQuery(sql);
		insertQuery.setParameter(1, userid);
		insertQuery.setParameter(2, vcode1);

		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<RepVaaFinalListModel> list = new ArrayList<RepVaaFinalListModel>();
		for(Object[] row:detailsEntities1) {
			RepVaaFinalListModel RepVaaFinalList = new RepVaaFinalListModel();
			RepVaaFinalList.setBookingid((String)row[13].toString());

			RepVaaFinalList.setOccupname((String)row[5].toString());
			
			RepVaaFinalList.setOccupfname((String)row[6].toString());
			
			RepVaaFinalList.setCr_farmeruid((String)row[20].toString());
			
			BigDecimal smsmobileno = (BigDecimal)row[29];	
			if(smsmobileno == null) {
				RepVaaFinalList.setSmsmobileno("-");
			}else {
				RepVaaFinalList.setSmsmobileno(smsmobileno.toString());
			}		
//			RepVaaFinalList.setSmsmobileno((String)row[29].toString());
			RepVaaFinalList.setCultdesc((String)row[2].toString());
			RepVaaFinalList.setKh_no((String)row[12].toString());
			RepVaaFinalList.setCr_sno((String)row[22].toString());
			RepVaaFinalList.setCr_mix_unmix_ext((String)row[23].toString());
			RepVaaFinalList.setCropname((String)row[25].toString());
			RepVaaFinalList.setVarietyname((String)row[4].toString());
			RepVaaFinalList.setFtype_short((String)row[3].toString());
			RepVaaFinalList.setNaturedesc((String)row[17].toString());
			RepVaaFinalList.setWsrcdesc((String)row[26].toString());
			RepVaaFinalList.setSdate((String)row[7].toString());
			RepVaaFinalList.setIrrmethod((String)row[31].toString());
			RepVaaFinalList.setData_src((String)row[8].toString());
			RepVaaFinalList.setSeed_production((String)row[21].toString());
			
			Character Ekyc = (Character)row[16];	
			if(Ekyc == null) {
				RepVaaFinalList.setEkyc("");
			}else {
				RepVaaFinalList.setEkyc((String)row[16].toString());
			}		
			
//			RepVaaFinalList.setEkyc((String)row[16].toString());
			list.add(RepVaaFinalList);
		}

		return list;
	}	
}
