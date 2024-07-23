package com.ecrops.service.impl;
import java.util.ArrayList; 
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.DataSrcwiseCntModel;
//import com.ecrops.entity.GetCropnamesModel;
import com.ecrops.service.DataSrcWiseCntService;

@Service
public class DataSrcWiseCntServiceImpl implements DataSrcWiseCntService{

	@PersistenceContext
	private EntityManager entitymanager;

	
	@Override
	public List<DataSrcwiseCntModel> getdatasrcwisecntdet(Integer vscode, String tab1,String tab2,int role) {
		System.out.println("role--------->"+role);
	String qry= " select vcode,vsname,wbvname,COALESCE(wb_cnt,0)  as wbcount,COALESCE(ccrc_cnt,0) as ccrccount,\r\n"
			+ "COALESCE(efish_cnt,0) as efishcount,COALESCE(rofr_cnt,0) as rofrcount,\r\n"
			+ " COALESCE(peri_cnt,0) as pericount from " + tab1 + " a, \r\n"
			+ "	" + tab2 + " b where a.cr_vcode=b.vcode \r\n";
	
	if(role == 25) {
		qry+= " and   b.vscode=?";
	}
	if(role == 30) {
		qry+= " and   a.cr_vcode=?";
	}
			
	
	Query insertQuery = (Query) entitymanager.createNativeQuery(qry);
	insertQuery.setParameter(1, vscode);
	
	System.out.println("insertQuery=>"+insertQuery);
	
	List<Object[]> detailsEntities1 = insertQuery.getResultList();
	System.out.println("detailsEntities=>"+detailsEntities1.size());
	List<DataSrcwiseCntModel> list = new ArrayList<DataSrcwiseCntModel>();
	for(Object[] row: detailsEntities1) {
		DataSrcwiseCntModel dataSrcwiseCnt  = new DataSrcwiseCntModel();
		dataSrcwiseCnt.setVsname((String)row[1].toString());
		dataSrcwiseCnt.setWbvname((String)row[2].toString());
		dataSrcwiseCnt.setWbcount((String)row[3].toString());
		dataSrcwiseCnt.setCcrccount((String)row[4].toString());
		dataSrcwiseCnt.setEfishcount((String)row[5].toString());
		dataSrcwiseCnt.setRofrcount((String)row[6].toString());
		dataSrcwiseCnt.setPericount((String)row[7].toString());

		list.add(dataSrcwiseCnt);
	}	
		return list;
	}
}