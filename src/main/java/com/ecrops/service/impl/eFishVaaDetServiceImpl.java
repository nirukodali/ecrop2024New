package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.VillageSec;
import com.ecrops.entity.eFishVaaDetModel;
import com.ecrops.service.efishVaaDetService;

@Service
public class eFishVaaDetServiceImpl implements efishVaaDetService{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<eFishVaaDetModel> getefishVaaDet(Integer dcode, Integer mcode, Integer vcode1, String tab) {
		// TODO Auto-generated method stub
		
		String sql= "select mapped_extent,CAST(occupant_extent AS numeric) - CAST(mapped_extent AS numeric) AS avail_ext,"
				+ " b.dcode,b.mcode,b.wbvcode,village_name as wbvname,pattadar_name as ocname,pattadar_father_name as ocfname,coalesce(booking_available, ' ') as booking_available, coalesce(allowable_ext, ' ') as allowable_ext,"
				+ " occupant_name  as farmer_name,occupant_father_name  as father_name,"
				+ " cr_sno  as survey_no, kh_no, occupant_extent, total_extent as tot_extent from " + tab + " a, wbvillage_mst b where booking_available is null and"
				+ " a.cr_vcode=CAST(b.wbvcode AS text) and  b.dcode=? and b.mcode=? order by wbvname";
		
		Query insertQuery = (Query)entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1,  dcode);
		insertQuery.setParameter(2,  mcode);
		

		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<eFishVaaDetModel> list = new ArrayList<eFishVaaDetModel>();
		for(Object[] row: detailsEntities1) {
			eFishVaaDetModel eFishVaadet = new eFishVaaDetModel();
			eFishVaadet.setWbvname((String)row[5].toString());
			eFishVaadet.setOcname((String)row[6].toString());
			eFishVaadet.setOcfname((String)row[7].toString());
			eFishVaadet.setFarmer_name((String)row[10].toString());
			eFishVaadet.setFather_name((String)row[11].toString());
			eFishVaadet.setSurvey_no((String)row[12].toString());
			eFishVaadet.setKh_no((String)row[13].toString());
			eFishVaadet.setTot_extent((String)row[15].toString());
			eFishVaadet.setOccupant_extent((String)row[14].toString());
			eFishVaadet.setMapped_extent((String)row[0].toString());
			eFishVaadet.setAvail_ext((String)row[1].toString());
			list.add(eFishVaadet);
		}
		System.out.println("list=>"+list.size());
		return list;
	}	

}
