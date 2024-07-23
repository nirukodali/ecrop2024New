package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.nonwebViewModel;
import com.ecrops.service.NonwebViewService;

@Service
public class nonwebViewServiceImpl implements NonwebViewService{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<nonwebViewModel> getnonwebviewdet(String t1, Integer dcode, Integer mcode, String userid, String season,
			Integer cropyear1) {
		// TODO Auto-generated method stub
		
		String sql = "select wbdname,wbmname,wbvname,cast(kh_no as character varying) as kh_no,cr_sno,oc_name,oc_fname,cr_farmeruid,cast(tot_extent as character varying) as tot_extent,cast( occupant_extent as character varying) as occupant_extent,occupname,occupfname,gender,cast(mobileno  as character varying) as  mobileno"
				+ "  from " + t1 + " a,wbvillage_mst b where a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode"
				+ "  and a.cr_vcode=b.wbvcode  and a.dcode=? and a.mcode=? "
				+ "  and entry_by= ? and cr_season=? and cr_year=? order by wbdname,wbmname,wbvname";
		
		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1, dcode);
		insertQuery.setParameter(2, mcode);
		insertQuery.setParameter(3, userid);
		insertQuery.setParameter(4, season);
		insertQuery.setParameter(5, cropyear1);

		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<nonwebViewModel> list = new ArrayList<nonwebViewModel>();
		
		for(Object[] row: detailsEntities1) {
			nonwebViewModel nonwebview = new nonwebViewModel();
			nonwebview.setOc_name((String)row[5].toString());
			nonwebview.setOc_fname((String)row[6].toString());
			nonwebview.setOccupname((String)row[10].toString());
			nonwebview.setOccupfname((String)row[11].toString());
			nonwebview.setTot_extent((String)row[8].toString());
			nonwebview.setOccupant_extent((String)row[9].toString());
			nonwebview.setCr_farmeruid((String)row[7].toString());
			nonwebview.setMobileno((String)row[13].toString());
			nonwebview.setKh_no((String)row[3].toString());
			nonwebview.setCr_sno((String)row[4].toString());
			
			String gender = (String)row[12].toString();
			if(gender.equalsIgnoreCase("M")) { 
				nonwebview.setGender("Male");
			}else if(gender.equalsIgnoreCase("F")) {
				nonwebview.setGender("Female");
			}else {
				nonwebview.setGender("Transgender");
			}
			
			list.add(nonwebview);
		}	
		return list;
	
		
	}
	
	
	

	
	
	
	
	
}
