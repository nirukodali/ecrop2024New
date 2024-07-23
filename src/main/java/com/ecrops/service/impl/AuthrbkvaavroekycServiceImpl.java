package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import com.ecrops.entity.AuthrbkvaavroekycModel;
import com.ecrops.service.AuthrbkvaavroekycService;
@Service
public class AuthrbkvaavroekycServiceImpl implements AuthrbkvaavroekycService {
	@PersistenceContext private EntityManager entityManager;

	@Override
	public List<AuthrbkvaavroekycModel> getAuthrbkvaavroekycdet(String tab, Integer vscode, Integer cropyear1,
			String season) {
		// TODO Auto-generated method stub
		
		String sql="select DISTINCT vscode, wbvname, totfarmercount, totalbookings, totextent, vaaauthcount, vaaauthextent, vroauthcount, vroauthextent,"
				+ "totekycbookings, ekycfarmercount,coalesce( ekycbookedext, 0 ) as ekycbookedext from " + tab + " where vscode=?  ";
	
		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1, vscode);
		
		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<AuthrbkvaavroekycModel> list = new ArrayList<AuthrbkvaavroekycModel>();
		

		for(Object[] row: detailsEntities1) {
			AuthrbkvaavroekycModel authrbkvaavroekyc = new AuthrbkvaavroekycModel();
//			authrbkvaavroekyc.setVscode((String) row[0].toString());
			authrbkvaavroekyc.setWbvname((String)row[1].toString());
			authrbkvaavroekyc.setTotfarmercount((String)row[2].toString());
			authrbkvaavroekyc.setTotalbookings((String)row[3].toString());
			authrbkvaavroekyc.setTotextent((String)row[4].toString());
			authrbkvaavroekyc.setVaaauthcount(row[5].toString());
			authrbkvaavroekyc.setVaaauthextent(row[6].toString());
			authrbkvaavroekyc.setVroauthcount (row[7].toString());
			authrbkvaavroekyc.setVroauthextent(row[8].toString());
			authrbkvaavroekyc.setTotekycbookings(row[9].toString());
			authrbkvaavroekyc.setEkycfarmercount(row[10].toString());
			authrbkvaavroekyc.setEkycbookedext(row[11].toString());
			list.add(authrbkvaavroekyc);
		}	
		return list;
	}
}
