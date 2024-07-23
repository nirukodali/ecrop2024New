package com.ecrops.repo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import com.ecrops.entity.CropwiseExtBooked;

@Repository
public class CropwiseExtBookedRepository  {
	
		@PersistenceContext
		private EntityManager entitymanger;
	
	    public List<CropwiseExtBooked> getCropwise(int dcode,String sescrpyear,String cropname,String crpgrp,String mcode) {
	    	String season = sescrpyear.split("@")[0];
			String cropyear = sescrpyear.split("@")[1];
			System.out.println("seasopn--------->"+season);
			String t1 = "rep_vill_wise_cropwise_ext_v_" +season+cropyear;
			if(season.equalsIgnoreCase("S") || season =="S") {
				t1=t1;
			}
			
			else if(Integer.parseInt(cropyear)>2022) {
			t1= "ecrop"+cropyear+"."+t1;
			}
			System.out.println("t1---------"+t1);
			System.out.println("sea---------"+season);
			System.out.println("cyear---------"+cropyear);
			String qry = "select mcode,mname,sum(totextent) as totext from " + t1 + " where dcode=? and cr_year=? and cr_season=? and cr_crop=? and grpcode=? "
					+ "and mcode in (select mcode from adamandals_v where divcode="+mcode+")  group by mcode,mname order by mname";
			Query query = entitymanger.createNativeQuery(qry);
			query.setParameter(1, dcode);
			query.setParameter(2, Integer.parseInt(cropyear));
			query.setParameter(3, season);
			query.setParameter(4, Integer.parseInt(cropname));
			query.setParameter(5, Integer.parseInt(crpgrp));
//			query.setParameter(6, Integer.parseInt(mcode));
			List<Object[]> result = query.getResultList();
		System.out.println(qry);
			List<CropwiseExtBooked> list = new ArrayList<CropwiseExtBooked>();
	
		
		for (Object[] bean : result) 
		{
			CropwiseExtBooked wbVillageModel = new CropwiseExtBooked();
			wbVillageModel.setMcode((Integer) bean[0]);
			wbVillageModel.setMname((String) bean[1]);
			wbVillageModel.setTotextent((BigDecimal) bean[2]);
			list.add(wbVillageModel);
		}
		System.out.println("list---------------->"+list);
	
		return list;
	}
	    
	    public List<CropwiseExtBooked> getCropwise(int dcode,String sescrpyear,String cropname,String crpgrp,int role) {
	    	String season = sescrpyear.split("@")[0];
			String cropyear = sescrpyear.split("@")[1];
			System.out.println("seasopn--------->"+season);
			String t1 = "rep_vill_wise_cropwise_ext_v_" +season+cropyear;
			if(season.equalsIgnoreCase("S") || season =="S") {
				t1=t1;
			}
			
			else if(Integer.parseInt(cropyear)>2022) {
			t1= "ecrop"+cropyear+"."+t1;
			}
			System.out.println("t1---------"+t1);
			System.out.println("sea---------"+season);
			System.out.println("cyear---------"+cropyear);
			String qry = "select mcode,mname,sum(totextent) as totext from " + t1 + "";
			if(role == 46) {
				qry+=" where dcode=(select distinct dcode from district_2011_cs where wbdcode=?) and cr_year=? and cr_season=? and cr_crop=? and grpcode=? group by mcode,mname order by mname";
			}
			if(role != 46) {
				qry+=" where dcode=? and cr_year=? and cr_season=? and cr_crop=? and grpcode=? group by mcode,mname order by mname";
			}
			Query query = entitymanger.createNativeQuery(qry);
			query.setParameter(1, dcode);
			query.setParameter(2, Integer.parseInt(cropyear));
			query.setParameter(3, season);
			query.setParameter(4, Integer.parseInt(cropname));
			query.setParameter(5, Integer.parseInt(crpgrp));
			List<Object[]> result = query.getResultList();
		System.out.println(qry);
			List<CropwiseExtBooked> list = new ArrayList<CropwiseExtBooked>();
	
		
		for (Object[] bean : result) 
		{
			CropwiseExtBooked wbVillageModel = new CropwiseExtBooked();
			wbVillageModel.setMcode((Integer) bean[0]);
			wbVillageModel.setMname((String) bean[1]);
			wbVillageModel.setTotextent((BigDecimal) bean[2]);
			list.add(wbVillageModel);
		}
		System.out.println("list---------------->"+list);
	
		return list;
	}
	    
	    public List<CropwiseExtBooked> getCropwise(int dcode,String sescrpyear) {
	    	String season = sescrpyear.split("@")[0];
			String cropyear = sescrpyear.split("@")[1]; 
			String t1="rep_vill_wise_cropwise_ext_v_" +season+cropyear;
			if(cropyear.equals(2023) || cropyear.equals(2024))
			 t1 = "ecrop"+cropyear+"."+"rep_vill_wise_cropwise_ext_v_" +season+cropyear;
			System.out.println("t1---------"+t1);
			System.out.println("season---------"+season);
			System.out.println("cropyear---------"+cropyear);
			String qry = "select mcode,mname,sum(totextent) as totext from " + t1 + " where dcode=?  and cr_year=? and cr_season=? group by mcode,mname order by mname";
			Query query = entitymanger.createNativeQuery(qry);
			query.setParameter(1, dcode);
			query.setParameter(2, Integer.parseInt(cropyear));
			query.setParameter(3, season);
			
			List<Object[]> result = query.getResultList();
		
			List<CropwiseExtBooked> list = new ArrayList<CropwiseExtBooked>();
	
		
			System.out.println("list---------------->"+list);
			for (Object[] bean : result) 
			{
			CropwiseExtBooked cropwiseExtModel = new CropwiseExtBooked();
			cropwiseExtModel.setMcode((Integer) bean[0]);
			cropwiseExtModel.setMname((String) bean[1]);
			cropwiseExtModel.setTotextent((BigDecimal) bean[2]);
			list.add(cropwiseExtModel);
			}
			System.out.println("list---------------->"+list.size());
	
			return list;
	}

}