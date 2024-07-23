package com.ecrops.partition;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.SuperCheckRevenueEntity;

@Repository
@Transactional
public class SuperCheckRevenueRepo {

	@PersistenceContext
	private EntityManager entityManager;

	public List<SuperCheckRevenueEntity> getsupercheckrevenue(String wbdcode, String cropyear) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String tableName = "supercheck_status_distwise_";
		 if (seasonYear>=2023) {
			 tableName = "ecrop" + seasonYear + "." + tableName+seasonType+seasonYear;
	        }else{
	        	tableName = "supercheck_status_distwise_"+seasonType+seasonYear;
	    		}
		System.out.println("tableName---------------->" + tableName);
		String Sql = "select mname,hod_allotted,hod_approved,hod_rejected,dao_allotted,dao_approved,dao_rejected,"
				   + "ada_allotted,ada_approved,ada_rejected,mao_allotted,mao_approved,mao_rejected,"
				   + "dho_allotted,dho_approved,dho_rejected,ho_allotted,ho_approved,ho_rejected,"
				   + "rdo_allotted,rdo_approved,rdo_rejected,tah_allotted,tah_approved,tah_rejected,"
				   + "dc_allotted,dc_approved,dc_rejected,jc_allotted,jc_approved,"
				   + "jc_rejected from "+tableName+" where cr_dist_code="+wbdcode+" order by mname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();

		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<SuperCheckRevenueEntity> detailsEntities = new ArrayList<SuperCheckRevenueEntity>();

		try {
			for (Object[] row : detailsEntities1) {
				SuperCheckRevenueEntity entity = new SuperCheckRevenueEntity();

				entity.setMname((String) row[0]);
				entity.setHod_alloted((BigInteger) row[1]);
				entity.setHod_approved((BigInteger) row[2]);
				entity.setHod_rejected((BigInteger) row[3]);
				entity.setDao_alloted((BigInteger) row[4]);
				entity.setDao_approved((BigInteger) row[5]);
				entity.setDao_rejected((BigInteger) row[6]);
				entity.setAda_alloted((BigInteger) row[7]);
				entity.setAda_approved((BigInteger) row[8]);
				entity.setAda_rejected((BigInteger) row[9]);
				entity.setMao_alloted((BigInteger) row[10]);
				entity.setMao_approved((BigInteger) row[11]);
				entity.setMao_rejected((BigInteger) row[12]);
				entity.setDho_alloted((BigInteger) row[13]);
				entity.setDho_approved((BigInteger) row[14]);
				entity.setDho_rejected((BigInteger) row[15]);
				entity.setHo_alloted((BigInteger) row[16]);
				entity.setHo_approved((BigInteger) row[17]);
				entity.setHo_rejected((BigInteger) row[18]);
				entity.setRdo_alloted((BigInteger) row[19]);
				entity.setRdo_approved((BigInteger) row[20]);
				entity.setRdo_rejected((BigInteger) row[21]);
				entity.setTah_alloted((BigInteger) row[22]);
				entity.setTah_approved((BigInteger) row[23]);
				entity.setTah_rejected((BigInteger) row[24]);
				entity.setDc_alloted((BigInteger) row[25]);
				entity.setDc_approved((BigInteger) row[26]);
				entity.setDc_rejected((BigInteger) row[27]);
				entity.setJc_alloted((BigInteger) row[28]);
				entity.setJc_approved((BigInteger) row[29]);
				entity.setJc_rejected((BigInteger) row[30]);
				
				detailsEntities.add(entity);
			}
			System.out.println("detailsEntities===========>" + detailsEntities.size());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("erroe==> " + e);
		}
		return detailsEntities;
	}
}