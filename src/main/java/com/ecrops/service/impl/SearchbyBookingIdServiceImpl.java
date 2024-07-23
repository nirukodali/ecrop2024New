package com.ecrops.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.DataSrcwiseCntModel;
import com.ecrops.entity.SearchbyBookingIdModel;
import com.ecrops.service.SearchbyBookingIdService;

@Service
public class SearchbyBookingIdServiceImpl  implements SearchbyBookingIdService{

	@PersistenceContext
	private EntityManager entitymanager;	
	@Override
	public List<SearchbyBookingIdModel> getSearchbyBookingIddet(String tname,String userid,Long bkId) {
	
		String sql="select dname as wbdname,mname as wbmname,vname as wbvname,oc_name as occupname,'XXXXXXXX'||substr(cr_farmeruid,9) as cr_farmeruid,"
				+ " bookingid, kh_no,vaaauth,vroauth,to_char(dt_vaaauth,'yyyy-mm-dd') as dt_vaaauth,to_char(dt_vroauth,'yyyy-mm-dd') as dt_vroauth,"
				+ " to_char(dt_ekyc,'yyyy-mm-dd') as dt_ekyc, cultdesc  , varietyname, cr_sno,cr_mix_unmix_ext,"
				+ " cr_crop, ekyc, cropname, cr_season||','||cr_year as cropduration "
				+ " from " + tname + " where bookingid=? and updatedby=?  order by bookingid";
		
		Query insertQuery = (Query) entitymanager.createNativeQuery(sql);
		insertQuery.setParameter(1, bkId);
		insertQuery.setParameter(2, userid );
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		List<SearchbyBookingIdModel> list = new ArrayList<SearchbyBookingIdModel>();
		for(Object[] row: detailsEntities1) {
			SearchbyBookingIdModel searchbyBookingId  = new SearchbyBookingIdModel();
			searchbyBookingId.setWbvname((String)row[2].toString());
			searchbyBookingId.setBookingid((String)row[5].toString());
			searchbyBookingId.setOccupname((String)row[3].toString());
			searchbyBookingId.setCr_sno((String)row[14].toString());
			searchbyBookingId.setKh_no((String)row[6].toString());
			searchbyBookingId.setCropname((String)row[18].toString());
			searchbyBookingId.setVarietyname((String)row[13].toString());
			searchbyBookingId.setCr_mix_unmix_ext((String)row[15].toString());
			searchbyBookingId.setVaaauth((String)row[7].toString());
			searchbyBookingId.setDt_vaaauth((String)row[9].toString());
			searchbyBookingId.setVroauth((String)row[8].toString());
			searchbyBookingId.setDt_vroauth((String)row[10].toString());
			searchbyBookingId.setEkyc((String)row[17].toString());
			searchbyBookingId.setDt_ekyc((String)row[11].toString());



			list.add(searchbyBookingId);
		}	

		
		return list;
	}

	
	
}
