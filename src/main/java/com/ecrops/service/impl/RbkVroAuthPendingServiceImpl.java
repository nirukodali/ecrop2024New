package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.RbkVroAuthComplModel;
import com.ecrops.entity.RbkVroAuthPend;
import com.ecrops.service.RbkVroAuthComplService;
import com.ecrops.service.RbkVroAuthPendingService;

@Service
public class RbkVroAuthPendingServiceImpl implements RbkVroAuthPendingService{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RbkVroAuthPend> getVroAuthPendingDet(String t1, String userid) {
		// TODO Auto-generated method stub
		
		String sql = "select dname as wbdname,mname as wbmname,vname as wbvname,oc_name as "
				+ "occupname,'XXXXXXXX'||substr(cr_farmeruid,9) as cr_farmeruid, bookingid, kh_no, "
				+ "cultdesc, varietyname, cr_sno,cr_mix_unmix_ext, cr_crop,coalesce(ekyc , ' ') as  ekyc, cropname, "
				+ "cr_season||','||cr_year as cropduration,coalesce(vaaauth , ' ') as vaaauth,data_src from "+t1+ "  where  updatedby= ? and  (vroauth is null or vroauth='N') order by bookingid";
		
		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1, userid);
		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<RbkVroAuthPend> list = new ArrayList<RbkVroAuthPend>();
		
		for(Object[] row: detailsEntities1) {
			RbkVroAuthPend rbkVroAuthPend = new RbkVroAuthPend();
			rbkVroAuthPend.setWbvname((String)row[2].toString());
			rbkVroAuthPend.setBookingid((String)row[5].toString());
			rbkVroAuthPend.setOccupname((String)row[3].toString());
			rbkVroAuthPend.setCr_sno((String)row[9].toString());
			rbkVroAuthPend.setKh_no((String)row[6].toString());
			rbkVroAuthPend.setCropname((String)row[13].toString());
			rbkVroAuthPend.setVarietyname((String)row[8].toString());
			rbkVroAuthPend.setCr_mix_unmix_ext((String)row[10].toString());
			rbkVroAuthPend.setVaaauth((String)row[15].toString());
			rbkVroAuthPend.setData_src((String)row[16].toString());
			list.add(rbkVroAuthPend);
		}
	
		return list;
	}

	

	
	
	
}
