package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.RbkVroAuthComplModel;
import com.ecrops.service.RbkVroAuthComplService;
@Service
public class RbkVroAuthComplServiceImpl implements RbkVroAuthComplService{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RbkVroAuthComplModel> getVroAuthComplDet(String t1, String userid) {
		// TODO Auto-generated method stub
		
		String sql = "select dname as wbdname,mname as wbmname,vname as wbvname,oc_name as occupname,'XXXXXXXX'||substr(cr_farmeruid,9) as cr_farmeruid, \r\n"
				+ "bookingid, kh_no, cultdesc, varietyname, cr_sno,cr_mix_unmix_ext, cr_crop, ekyc, cropname, cr_season||','||cr_year as cropduration,\r\n"
				+ "vaaauth from " + t1 + " where  updatedby= ? and  vroauth='Y'  order by bookingid ";
		
		System.out.println("qry--------->  "+sql);

		
		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1, userid);
		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<RbkVroAuthComplModel> list = new ArrayList<RbkVroAuthComplModel>();
		
		for(Object[] row: detailsEntities1) {
			RbkVroAuthComplModel rbkVroAuthComplModel = new RbkVroAuthComplModel();
			rbkVroAuthComplModel.setWbvname((String)row[2].toString());
			rbkVroAuthComplModel.setBookingid((String)row[5].toString());
			rbkVroAuthComplModel.setOccupname((String)row[3].toString());
			rbkVroAuthComplModel.setCr_sno((String)row[9].toString());
			rbkVroAuthComplModel.setKh_no((String)row[6].toString());
			rbkVroAuthComplModel.setCropname((String)row[13].toString());
			rbkVroAuthComplModel.setVarietyname((String)row[8].toString());
			rbkVroAuthComplModel.setCr_mix_unmix_ext((String)row[10].toString());
			rbkVroAuthComplModel.setVaaauth((String)row[15].toString());
			list.add(rbkVroAuthComplModel);
		}

		return list;
	}

}
