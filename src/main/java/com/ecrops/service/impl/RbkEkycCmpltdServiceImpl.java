package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.RbkEkycCompltdModel;
import com.ecrops.entity.nonwebViewModel;
import com.ecrops.service.RbkEkycCompltdService;

@Service
public class RbkEkycCmpltdServiceImpl implements RbkEkycCompltdService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RbkEkycCompltdModel> getRbkEkycCompltddet(String tname, Integer vcode1) {
		// TODO Auto-generated method stub
		
		String sql= " select cr_vcode,a.ekycfarmername,dname as wbdname,mname as wbmname,vname as wbvname,oc_name as occupname,'XXXXXXXX'||substr(cr_farmeruid,9) as cr_farmeruid, "
                + "  a.bookingid, kh_no, dtekyc , updatedby,cultdesc, varietyname, cr_sno,cr_mix_unmix_ext, a.cr_crop, ekyc, a.cropname, cr_season||','||cr_year as cropduration,vaaauth  "
                + "  from " + tname + " a  "
                + "  where a.ekyc='Y' AND a.cr_vcode=? order by updatedby,bookingid ";
		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1, vcode1);
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<RbkEkycCompltdModel> list = new ArrayList<RbkEkycCompltdModel>();
		
		for(Object[] row: detailsEntities1) {
			RbkEkycCompltdModel rbkEkycCompltd = new RbkEkycCompltdModel();
			rbkEkycCompltd.setWbvname((String)row[4].toString());
			rbkEkycCompltd.setUpdatedby((String)row[10].toString());
			rbkEkycCompltd.setBookingid((String)row[7].toString());
			rbkEkycCompltd.setOccupname((String)row[5].toString());
			rbkEkycCompltd.setCr_sno((String)row[13].toString());
			rbkEkycCompltd.setKh_no((String)row[8].toString());
			rbkEkycCompltd.setCropname((String)row[17].toString());
			rbkEkycCompltd.setVarietyname((String)row[12].toString());
			rbkEkycCompltd.setCr_mix_unmix_ext((String)row[14].toString());
			rbkEkycCompltd.setDtekyc((String)row[9].toString());
			
			list.add(rbkEkycCompltd);
		}	

		return list;
	}
	
	
	
}
