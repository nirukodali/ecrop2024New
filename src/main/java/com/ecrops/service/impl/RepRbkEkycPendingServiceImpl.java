package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.AllocationSurveyNoModel;
import com.ecrops.entity.RepRbkekycPendingModel;
import com.ecrops.service.RepRbkekycPendingService;

@Service
public class RepRbkEkycPendingServiceImpl implements RepRbkekycPendingService{
	
	@PersistenceContext private EntityManager entityManager;

	@Override
	public List<RepRbkekycPendingModel> getRbkekycPendingdet(String tname, Integer vcode1) {

		String sql=" select mobileno,dname as wbdname,mname as wbmname,vname as wbvname,oc_name as occupname,  concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid, bookingid, kh_no,"
				+ " cultdesc, varietyname, cr_sno,cr_mix_unmix_ext, updatedby,cr_crop, ekyc, cropname, cr_season||','||cr_year as cropduration,vaaauth "
				+ "  from " + tname + " where cr_vcode=?   and  ekyc is null and (vroauth is null or vroauth ='N' ) and (vaaauth is null or vaaauth ='N' )  order by bookingid  ";
		
		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1,  vcode1);
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());

		List<RepRbkekycPendingModel> list = new ArrayList<RepRbkekycPendingModel>();
		
		for(Object[] row: detailsEntities1) {
			RepRbkekycPendingModel repRbkekycPending = new RepRbkekycPendingModel();
			repRbkekycPending.setWbvname((String)row[3].toString());
			repRbkekycPending.setBookingid((String)row[6].toString());
			repRbkekycPending.setOccupname((String)row[4].toString());
			repRbkekycPending.setCr_farmeruid((String)row[5].toString());
			repRbkekycPending.setCr_sno((String)row[10].toString());
			repRbkekycPending.setKh_no((String)row[7].toString());
			repRbkekycPending.setCropname((String)row[15].toString());
			repRbkekycPending.setVarietyname((String)row[9].toString());
			repRbkekycPending.setCr_mix_unmix_ext((String)row[11].toString());
			repRbkekycPending.setUpdatedby((String)row[12].toString());
			repRbkekycPending.setMobileno((String)row[0].toString());

			list.add(repRbkekycPending);

		}	
			System.out.println("list=>"+list.size());
		    return list;
	}
}
