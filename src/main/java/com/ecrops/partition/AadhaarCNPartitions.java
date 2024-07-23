package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.AadhaarCN;

@Repository
@Transactional
public class AadhaarCNPartitions {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<AadhaarCN> aadhaar(String dcode){
		

		String tab1 = "";

		tab1 = "user_registration";
		System.out.println("tab1---------------->" + tab1);

		String sql = " SELECT district, count(*) FILTER(WHERE length(aadhaar_id::text)=12 )AS updatedcount,count(*) FILTER(WHERE aadhaar_id='' ) AS notupdatedcount\r\n"
				+ "from "+ tab1 +" where status='A'  and type_user='25' and district!='999' group by district  order by district  ";

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		List<Object[]> aadha = sesnyr.getResultList();
		List<AadhaarCN> entityDetails = new ArrayList<AadhaarCN>();

		for (Object[] row : aadha) {

			AadhaarCN entity = new AadhaarCN();

			entity.setDistrict((String) row[0]);
			entity.setUpdatedcount(Long.valueOf(row[1].toString()));
			entity.setNotupdatedcount(Long.valueOf(row[2].toString()));
			entityDetails.add(entity);

		}

		return entityDetails;

	}

}
