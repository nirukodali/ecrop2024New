package com.ecrops.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.RbkVaaAuthPendingModel;
import com.ecrops.service.RbkVaaAuthPendingService;

@Service
public class RbkVaaAuthPendingServiceImpl implements RbkVaaAuthPendingService {

	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public List<RbkVaaAuthPendingModel> getVaaAuthPendingDet(String t1, String userid) {
		// TODO Auto-generated method stub
		
	    String    sql = "select dname as wbdname,mname as wbmname,vname as wbvname,oc_name as occupname,'XXXXXXXX'||substr(cr_farmeruid,9) as cr_farmeruid, bookingid, kh_no,coalesce(vaaauth , ' ') as vaaauth,coalesce(vao_verify , ' ') as vao_verify,coalesce(vro_verify , ' ') as vro_verify,coalesce(vroauth , ' ') as vroauth ,  cultdesc  , varietyname, cr_sno,cr_mix_unmix_ext, cr_crop,coalesce(ekyc , ' ') as  ekyc, cropname, cr_season||','||cr_year as cropduration"
	            + " from " + t1 + " where  updatedby= ?  and (vaaauth is null or vaaauth ='N' ) order by bookingid";
		
		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1, userid);
		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<RbkVaaAuthPendingModel> list = new ArrayList<RbkVaaAuthPendingModel>();
		
		for(Object[] row: detailsEntities1) {
			RbkVaaAuthPendingModel rbkVaaAuthPendingModel = new RbkVaaAuthPendingModel();
			rbkVaaAuthPendingModel.setWbvname((String)row[2].toString());
			rbkVaaAuthPendingModel.setBookingid((String)row[5].toString());
			rbkVaaAuthPendingModel.setOccupname((String)row[3].toString());
			rbkVaaAuthPendingModel.setCr_sno((String)row[12].toString());
			rbkVaaAuthPendingModel.setKh_no((String)row[6].toString());
			rbkVaaAuthPendingModel.setCropname((String)row[17].toString());
			rbkVaaAuthPendingModel.setVarietyname((String)row[12].toString());
			rbkVaaAuthPendingModel.setCr_mix_unmix_ext((String)row[14].toString());
			list.add(rbkVaaAuthPendingModel);
		}
	
		
		return list;
	}      

	
	
	
}
