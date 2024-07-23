package com.ecrops.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.Rep_vaa_draft_listModel;
import com.ecrops.service.Rep_vaa_draft_listService;
@Service
public class RepvaadraftlistSrvcImpl implements Rep_vaa_draft_listService {

	@PersistenceContext
	private EntityManager entitymanager;

	@Override
	public List<Rep_vaa_draft_listModel> getvaadraftlistdet(String supercheckupd, String tab1, String userid,Integer cropyear1, String season, Integer vcode1) {
		// TODO Auto-generated method stub
		
		String sql = "select a.cr_no,ct.cultdesc_loclang,cultdesc,ftype_short,v.varietyname,occupname,occupfname,to_char(cr_sow_date, 'DD-MM-YYYY') as sdate,"
				+ " a.cr_dist_code,a.cr_mand_code,a.cr_vcode,b.wbdname,b.wbmname,b.wbvname,a.kh_no,a.bookingid,coalesce(a.vaaauth,' ') vaaauth,coalesce(a.vroauth,' ') vroauth,coalesce(a.ekyc,' ') as ekyc,cn.naturedesc,coalesce(a.mao_unlock_appr,' ') mao_unlock_appr,"
				+  " coalesce(length(supercheck_userid),0) as sclength,'XXXXXXXX'||substr(cr_farmeruid,9) as cr_farmeruid,"
				+  " a.cr_sno,a.cr_mix_unmix_ext,a.cr_crop,coalesce(a.cropins,' ') cropins,c.cropname,w.wsrcdesc, cr_season||','||cr_year as cropduration,  coalesce(a.ins_scheme,' ') ins_scheme,coalesce(a.smsmobileno,0)as smsmobileno,coalesce(supercheck_userid,' ') supercheck_userid"
				+ " from " + tab1 + " a ,wbvillage_mst_v b,waterresources w ,cropnames c,cr_variety_master v,cultivator_types ct,cropnature cn,cropseed_scheme cs "
				+ " where  a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and a.cr_vcode=b.wbvcode and a.cultivator_type=ct.culttype and a.cr_sow_type=cn.id and "
				+ " a.variety=v.varietycode and a.cr_crop=c.cropid and a.cr_w_draw=w.wsrcid and cr_year=? and cr_mix_unmix_ext>0 and "
				+ "  a.cropseed_scheme=cs.cropschtype and ekyc is not null and cr_season=? and updatedby= ? and cr_vcode=? and (mark_delet not in('D') or mark_delet is null) and bookingid not "
				+ " in(select bookingid from " + supercheckupd + " where cropyear=? and season=?  and vaa_sup_rem is null "
				+ "  or (vaa_sup_rem is not null and (mao_remarks='D' or mro_remarks='D') ) or (vaa_sup_rem is not null and (mao_remarks is null or mro_remarks is null))) order by  kh_no ";	
	
		
		
		System.out.println("qry---------> "+sql);	
	
		Query insertQuery = (Query) entitymanager.createNativeQuery(sql);
	
	insertQuery.setParameter(1, cropyear1);
	insertQuery.setParameter(2, season);
	insertQuery.setParameter(3, userid);
	insertQuery.setParameter(4, vcode1);
	insertQuery.setParameter(5, cropyear1);
	insertQuery.setParameter(6, season);

	
	
	System.out.println("insertQuery=>"+insertQuery);
	
	List<Object[]> detailsEntities1 = insertQuery.getResultList();
	System.out.println("detailsEntities=>"+detailsEntities1.size());
	
	List<Rep_vaa_draft_listModel> list = new ArrayList<Rep_vaa_draft_listModel>();
	for(Object[] row: detailsEntities1) {
		Rep_vaa_draft_listModel vaadraftlist = new Rep_vaa_draft_listModel();
		vaadraftlist.setBookingid((String)row[15].toString());
		vaadraftlist.setSclength((String)row[21].toString());
		vaadraftlist.setOccupname((String)row[5].toString());
		vaadraftlist.setOccupfname((String)row[6].toString());
		vaadraftlist.setCr_farmeruid((String)row[22].toString());
		vaadraftlist.setSmsmobileno((String)row[31].toString());
		vaadraftlist.setCultdesc((String)row[2].toString());
		vaadraftlist.setKh_no((String)row[14].toString());
		vaadraftlist.setCr_sno((String)row[23].toString());
		vaadraftlist.setCr_mix_unmix_ext((String)row[24].toString());
		vaadraftlist.setCropname((String)row[27].toString());
		vaadraftlist.setVarietyname((String)row[4].toString());
		vaadraftlist.setFtype_short((String)row[3].toString());
		vaadraftlist.setNaturedesc((String)row[19].toString());
		vaadraftlist.setWsrcdesc((String)row[28].toString());
		vaadraftlist.setSdate((String)row[7].toString());
		vaadraftlist.setVaaauth((String)row[16].toString());
		vaadraftlist.setVroauth((String)row[17].toString());
		vaadraftlist.setEkyc((String)row[18].toString());
		list.add(vaadraftlist);
	}
	
	return list;
}
}
