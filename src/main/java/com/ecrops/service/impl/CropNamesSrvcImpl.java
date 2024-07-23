package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.CropnamesModel;
import com.ecrops.entity.eFishVaaDetModel;
import com.ecrops.service.Cropnamesrvc;
@Service
public class CropNamesSrvcImpl implements Cropnamesrvc{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CropnamesModel> getcropnames() {
		// TODO Auto-generated method stub
		
	String sql = "select cropid,cropname from cropnames  where active='A' order by cropname";	
		
	Query insertQuery = (Query)entityManager.createNativeQuery(sql);

	System.out.println("insertQuery=>"+insertQuery);
	List<Object[]> detailsEntities1 = insertQuery.getResultList();
	System.out.println("detailsEntities=>"+detailsEntities1.size());
	List<CropnamesModel> list = new ArrayList<CropnamesModel>();
	for(Object[] row: detailsEntities1) {
		CropnamesModel getcropnames  = new CropnamesModel();
		getcropnames.setCropid((String)row[0].toString());
		
		String crpnam = (String)row[1];	
		if(crpnam == null) {
			getcropnames.setCropname("");
		}else {
			getcropnames.setCropname((String)row[1].toString());;
		}			
//		getcropnames.setCropname((String)row[1].toString());
		list.add(getcropnames);
	}
		return list;
	}
	
	@Override
	public List<CropnamesModel> getcropnamesPeri() {
		// TODO Auto-generated method stub
		
	String sql = "select cropid,cropname from cropnames  where active='A' and cropclass in ('P','B') order by cropname";	
		
	Query insertQuery = (Query)entityManager.createNativeQuery(sql);

	System.out.println("insertQuery=>"+insertQuery);
	List<Object[]> detailsEntities1 = insertQuery.getResultList();
	System.out.println("detailsEntities=>"+detailsEntities1.size());
	List<CropnamesModel> list = new ArrayList<CropnamesModel>();
	for(Object[] row: detailsEntities1) {
		CropnamesModel getcropnames  = new CropnamesModel();
		getcropnames.setCropid((String)row[0].toString());
		
		String crpnam = (String)row[1];	
		if(crpnam == null) {
			getcropnames.setCropname("");
		}else {
			getcropnames.setCropname((String)row[1].toString());;
		}			
//		getcropnames.setCropname((String)row[1].toString());
		list.add(getcropnames);
	}
		return list;
	}
}
