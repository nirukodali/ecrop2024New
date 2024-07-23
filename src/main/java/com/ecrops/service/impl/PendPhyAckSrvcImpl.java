package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.AllocationSurveyNoModel;
import com.ecrops.entity.pendingphyackModel;
import com.ecrops.service.PendPhyAckService;

@Service
public class PendPhyAckSrvcImpl implements PendPhyAckService {
	@PersistenceContext private EntityManager entityManager;

	@Override
	public List<pendingphyackModel> getPendPhyAckdet(String tab, Integer wbdcode, String userid) {

		
		String sql= "SELECT DISTINCT x.dcode, x.mcode, x.wbdname, x.wbmname, x.wbvname, 'xxxxxxxx'||substr(x.cr_farmeruid,9) as cr_farmeruid, x.bookingid, x.kh_no, x.cr_sno, x.smsmobileno, x.updatedby, x.occupname"
				+ " FROM (SELECT DISTINCT b.dcode,b.mcode,b.wbdname,b.wbmname,b.wbvname,a.cr_farmeruid,a.bookingid,a.kh_no,a.cr_sno,a.smsmobileno,a.occupname,a.updatedby,c.addedby,c.no_gen"
				+ " FROM " + tab + " a JOIN wbvillage_mst b ON a.cr_vcode = b.wbvcode LEFT JOIN "
				+ " (SELECT DISTINCT no_gen,cr_farmeruid,addedby FROM ecrop2023.ekycgenerated WHERE wbdcode=?) c ON a.cr_farmeruid= c.cr_farmeruid AND a.updatedby=c.addedby"
				+ "  WHERE a.ekyc = CAST('Y' AS bpchar) AND a.mark_delet IS NULL AND a.mismatch IS NULL AND a.social_status IS NULL AND a.sup_status IS NULL ORDER BY a.cr_farmeruid) x "
				+ "  WHERE x.no_gen IS NULL and updatedby=? ORDER BY cr_farmeruid";
	
		
		System.out.println("Query:::::" +sql);
		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1,  wbdcode);
		insertQuery.setParameter(2, userid);
		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<pendingphyackModel> list = new ArrayList<pendingphyackModel>();
		
		for(Object[] row: detailsEntities1) {
			pendingphyackModel pendingphyack = new pendingphyackModel();
			pendingphyack.setWbvname((String)row[4].toString());
			pendingphyack.setOccupname((String)row[11].toString());
			pendingphyack.setBookingid((String)row[6].toString());
			pendingphyack.setKh_no((String)row[7].toString());
			pendingphyack.setCr_sno((String)row[8].toString());
			pendingphyack.setCr_farmeruid((String)row[5].toString());
			pendingphyack.setSmsmobileno((String)row[9].toString());

			list.add(pendingphyack);
		}
		System.out.println("list=>"+list.size());

		
		return list;
	}

}
