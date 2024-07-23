package com.ecrops.service.impl;

import java.util.ArrayList; 
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.SearchByUIDModel;
import com.ecrops.service.SearchByUIDService;

@Service
public class SearchByUIDServiceImpl implements SearchByUIDService{

	@PersistenceContext
	private EntityManager entitymanager;
	
	@Override
	public List<SearchByUIDModel> getSearchByUIDdet(String tname, String userid, String aNo) {

		String sql="select dname as wbdname,mname as wbmname,vname as wbvname,oc_name as occupname,'XXXXXXXX'||substr(cr_farmeruid,9) as cr_farmeruid,"
		+ "  bookingid, kh_no,vaaauth,vroauth,to_char(dt_vaaauth,'yyyy-mm-dd') as dt_vaaauth,to_char(dt_vroauth,'yyyy-mm-dd') as dt_vroauth,"
		+ "  to_char(dt_ekyc,'yyyy-mm-dd') as dt_ekyc, cultdesc  , varietyname, cr_sno,cr_mix_unmix_ext,"
		+ "   cr_crop, ekyc, cropname, cr_season||','||cr_year as cropduration "
		+ "   from " + tname + " where cr_farmeruid=? and updatedby= ?  order by cr_farmeruid";
		
Query insertQuery = (Query)entitymanager.createNativeQuery(sql);
insertQuery.setParameter(1, aNo);
insertQuery.setParameter(2, userid );
System.out.println("query------>" +sql);
System.out.println("insertQuery=>"+insertQuery);
List<Object[]> detailsEntities1 = insertQuery.getResultList();
System.out.println("detailsEntities=>"+detailsEntities1.size());
List<SearchByUIDModel> list = new ArrayList<SearchByUIDModel>();
for(Object[] row: detailsEntities1) {
	SearchByUIDModel searchByUID  = new SearchByUIDModel();
	searchByUID.setWbvname((String)row[2].toString());
	searchByUID.setCr_farmeruid((String)row[4].toString());
	searchByUID.setOccupname((String)row[3].toString());
	searchByUID.setCr_sno((String)row[14].toString());
	searchByUID.setKh_no((String)row[6].toString());
	searchByUID.setCropname((String)row[18].toString());
	searchByUID.setVarietyname((String)row[13].toString());
	searchByUID.setCr_mix_unmix_ext((String)row[15].toString());
	searchByUID.setVaaauth((String)row[7].toString());
	searchByUID.setDt_vaaauth((String)row[9].toString());
	searchByUID.setVroauth((String)row[8].toString());
	searchByUID.setDt_vroauth((String)row[10].toString());
	searchByUID.setEkyc((String)row[17].toString());
	searchByUID.setDt_ekyc((String)row[11].toString());
	list.add(searchByUID);
}
	
		return list;
	}
	
}
