package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.RBKVAAAuthComplModel;
import com.ecrops.service.RbkVaaAuthComplService;

@Service
public class RbkVaaAuthComplServiceImpl implements RbkVaaAuthComplService  {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RBKVAAAuthComplModel> getVaaAuthComplDet(String t1, String userid) {
		// TODO Auto-generated method stub
		
		
        String    sql = "select dname as wbdname,mname as wbmname,vname as wbvname,oc_name as occupname,"
                + "'XXXXXXXX'||substr(cr_farmeruid,9) as cr_farmeruid, bookingid, kh_no, cultdesc,"
                + " varietyname, cr_sno,cr_mix_unmix_ext, cr_crop,  cropname, cr_season||','||cr_year as cropduration,"
                + "vaaauth  from " + t1 + " where  updatedby= ? and  vaaauth='Y' "
                + " order by bookingid ";
     
		System.out.println("qry---------> "+sql);

		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1, userid);
		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<RBKVAAAuthComplModel> list = new ArrayList<RBKVAAAuthComplModel>();
		
		for(Object[] row: detailsEntities1) {
			RBKVAAAuthComplModel rbkvaaauthcomplmodel = new RBKVAAAuthComplModel();
			rbkvaaauthcomplmodel.setWbvname((String)row[2].toString());
			rbkvaaauthcomplmodel.setBookingid((String)row[5].toString());
			rbkvaaauthcomplmodel.setOccupname((String)row[3].toString());
			rbkvaaauthcomplmodel.setCr_sno((String)row[9].toString());
			rbkvaaauthcomplmodel.setKh_no((String)row[6].toString());
			rbkvaaauthcomplmodel.setCropname((String)row[12].toString());
			rbkvaaauthcomplmodel.setVarietyname((String)row[8].toString());
			rbkvaaauthcomplmodel.setCr_mix_unmix_ext((String)row[10].toString());
			rbkvaaauthcomplmodel.setVaaauth((String)row[14].toString());
			
			list.add(rbkvaaauthcomplmodel);
		}

	    		return list;
	
	}

}
