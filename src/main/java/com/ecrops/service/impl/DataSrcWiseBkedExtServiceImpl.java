package com.ecrops.service.impl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.DataSrcWiseBkedExtModel;
import com.ecrops.service.DataSourceWiseBookedExtService;

@Service
public class DataSrcWiseBkedExtServiceImpl implements DataSourceWiseBookedExtService{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DataSrcWiseBkedExtModel> getdatasrcwisebkedextdet(String t1, String userid) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT wbdcode, wbedname,wbmcode,wbemname, sum(web_farmers) as web_farmers, sum(web_ext) as web_ext, sum(nweb_farmers) as nweb_farmers, sum(nweb_ext)as nweb_ext ,\r\n"
				+ " sum(ccrc_farmers)as ccrc_farmers,sum(ccrc_ext)as ccrc_ext, sum(rofr_farmers)as rofr_farmers,sum(rofr_ext)as rofr_ext, sum(usus_farmers) as usus_farmers, \r\n"
				+ " sum(usus_ext) as usus_ext FROM " + t1 + " where wbdcode<>88 and  updatedby=? group by wbdcode, wbedname,wbmcode,wbemname \r\n"
				+ " ORDER BY wbemname ";
		
		
		Query insertQuery = (Query) entityManager.createNativeQuery(sql);
		insertQuery.setParameter(1, userid);
		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities=>"+detailsEntities1.size());
		
		List<DataSrcWiseBkedExtModel> list = new ArrayList<DataSrcWiseBkedExtModel>();
		
		for(Object[] row: detailsEntities1) {
			DataSrcWiseBkedExtModel datasrcWiseBkedExtModel = new DataSrcWiseBkedExtModel();
			datasrcWiseBkedExtModel.setWbemname((String)row[3].toString());
			datasrcWiseBkedExtModel.setWeb_farmers((String)row[4].toString());
			datasrcWiseBkedExtModel.setWeb_ext((String)row[5].toString());
			datasrcWiseBkedExtModel.setNweb_farmers((String)row[6].toString());
			datasrcWiseBkedExtModel.setNweb_ext((String)row[7].toString());
			datasrcWiseBkedExtModel.setCcrc_farmers((String)row[8].toString());
			datasrcWiseBkedExtModel.setCcrc_ext((String)row[9].toString());
			datasrcWiseBkedExtModel.setRofr_farmers((String)row[10].toString());
			datasrcWiseBkedExtModel.setRofr_ext((String)row[11].toString());
			datasrcWiseBkedExtModel.setUsus_farmers((String)row[12].toString());
			datasrcWiseBkedExtModel.setUsus_ext((String)row[13].toString());
			
			list.add(datasrcWiseBkedExtModel);
		}
	
		
		return list;
	}

	
	
}
