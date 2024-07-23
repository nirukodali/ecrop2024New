package com.ecrops.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.CropbookingdetVAModel;
import com.ecrops.service.CropBookingDetVAIntfService;

@Service
public class CropBkingDetVAServiceImpl implements CropBookingDetVAIntfService {

	@PersistenceContext
	private EntityManager entitymanager;

	@Override
	public List<CropbookingdetVAModel> getcropbkingdet(String tab1, Integer dcode, Integer vcode1, String season,
			Integer year, Integer mcode1, String display, Integer display1, Integer crpid,Integer sesmcode) {
		
		
		System.out.println("----------"+dcode);
		System.out.println("----------"+vcode1);
		System.out.println("----------"+season);
		System.out.println("----------"+year);
		System.out.println("----------"+mcode1);
		System.out.println("----------"+display);
		System.out.println("----------"+display1);
		System.out.println("----------"+crpid);
		if (crpid != null  && crpid != 0) {

			String qry = "select  farmingtype,bookingid, rbkname, a.dcode,a.mcode,cr_dist_code,    cr_mand_code,    cr_vcode,    cr_year,    cr_season,   concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,    owner_tenant,"
					+ "    oc_name,    oc_fname,    mobileno,    age,    email,    kh_no,    cr_sno,    tot_extent,     cultivable_land,   uncultivable_land,"
					+ "   cr_month,   cr_sow_type,   cr_crop,   cr_mix_unmix_ext,   cr_no,   cr_w_draw,   cr_irr_type,   wtr_tax,   longitude,   latitude,varietyname ,cropnature,cr_sow_date,watersource,irrmethoddesc,wb.wbvname as lgdvname,wb.wbmname as  lgdmname,"
					+ "cropname, data_src, updatedby  from " + tab1 + " a, wbvillage_mst wb ";

			if (display1 == 2) {
				qry += " join cr_insured_crops b on  a.code=b.dcode and a.cr_season=b.season and a.cr_year=b.cropyear and a.cr_crop=b.cropcode "
						+ " and (b.rainfed is null or (b.rainfed='Y' and a.cr_w_draw=1) or (b.rainfed='N' and a.cr_w_draw!=1) ) ";
			}
			

			qry += " where a.dcode=? and cr_mand_code=? and cr_vcode=? and a.dcode=wb.dcode and a.mcode=wb.mcode and a.cr_vcode=wb.wbvcode ";
			
			if (crpid != null && crpid != 0) {
						qry += "	and cr_crop=? ";
				}
			
			qry += "and cr_year=? and cr_season=? and a.cr_vcode=wb.wbvcode ";

			if (!(display == "" || display == null)) {
				if (display1 == 1) {
                qry += " and dt_cropins_reg is not null ";
				}
				if (display1 == 2) {
                qry += " and dt_cropins_reg is null ";
				}
				if (display1 == 3) {
                qry += " and cr_ins_revalidate is not null and reason_non_auth is null ";
				}
				if (display1 == 4) {
                qry += " and cr_ins_revalidateis is not null and reason_non_auth is not null ";
				}
			}
			qry += " order by cr_vcode,kh_no,cr_sno,oc_name ";
			Query insertQuery = (Query) entitymanager.createNativeQuery(qry);
			insertQuery.setParameter(1, dcode);
			insertQuery.setParameter(2, mcode1);
			insertQuery.setParameter(3, vcode1);
			if (crpid != null || crpid == 0) {
			insertQuery.setParameter(4, crpid);
			insertQuery.setParameter(5, year);
			insertQuery.setParameter(6, season);
			}
			else {
				insertQuery.setParameter(4, year);
				insertQuery.setParameter(5, season);
			}
			System.out.println("insertQuery=>" + insertQuery);

			List<Object[]> detailsEntities1 = insertQuery.getResultList();
			System.out.println("detailsEntities=>" + detailsEntities1.size());

			List<CropbookingdetVAModel> list = new ArrayList<CropbookingdetVAModel>();
			for (Object[] row : detailsEntities1) {
				CropbookingdetVAModel cropbookingdetVA = new CropbookingdetVAModel();
				cropbookingdetVA.setFarmingtype((String) row[0].toString());
				cropbookingdetVA.setBookingid((String) row[1].toString());
				cropbookingdetVA.setCr_mand_code((String) row[38].toString());
				cropbookingdetVA.setCr_vcode((String) row[37].toString());
				cropbookingdetVA.setRbkname((String) row[2].toString());
				cropbookingdetVA.setOc_name((String) row[12].toString());
				cropbookingdetVA.setCr_farmeruid((String) row[10].toString());
				cropbookingdetVA.setOc_fname((String) row[13].toString());
				cropbookingdetVA.setKh_no((String) row[17].toString());
				cropbookingdetVA.setCr_sno((String) row[18].toString());
				cropbookingdetVA.setCropname((String) row[39].toString());
				cropbookingdetVA.setVarietyname((String) row[32].toString());
				cropbookingdetVA.setCr_mix_unmix_ext((String) row[25].toString());
				cropbookingdetVA.setCr_sow_date((String) row[34].toString());
				cropbookingdetVA.setCropnature((String) row[33].toString());
				cropbookingdetVA.setWatersource((String) row[35].toString());
				cropbookingdetVA.setIrrmethoddesc((String) row[36].toString());
				cropbookingdetVA.setLgdvname((String) row[37]);
				BigDecimal mobnoDecimal = null;
				String mobno = null;

				if (row[14] != null) {
					mobnoDecimal = (BigDecimal) row[14];
					mobno = mobnoDecimal.toString();
				} else {
					row[14] = "";
				}

//   		BigDecimal mobnoDecimal = (BigDecimal) row[14];
//   		String mobno = mobnoDecimal.toString();

//   			String mobno = (String)row[14];
				if (mobno == null) {
					cropbookingdetVA.setMobileno("");
				} else {
					cropbookingdetVA.setMobileno(mobno.toString());
				}
//   		cropbookingdetVA.setMobileno((String)row[14].toString());	

				cropbookingdetVA.setUpdatedby((String) row[39].toString());

				list.add(cropbookingdetVA);

			}
			return list;

		} else {

			String qry = "select farmingtype, bookingid,rbkname,   a.dcode,a.mcode,cr_dist_code,    cr_mand_code,    cr_vcode,    cr_year,    cr_season,   concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,    owner_tenant,"
					+ "    oc_name,    oc_fname,    mobileno,    age,    email,    kh_no,    cr_sno,    tot_extent,     cultivable_land,   uncultivable_land,"
					+ "   cr_month,   cr_sow_type,   cr_crop,   cr_mix_unmix_ext,   cr_no,   cr_w_draw,   cr_irr_type,   wtr_tax,   longitude,   latitude,varietyname ,cropnature,cr_sow_date,watersource,irrmethoddesc,wb.wbvname as lgdvname,wb.wbmname as  lgdmname,"
					+ "        cropname, data_src, updatedby  from " + tab1 + " a , wbvillage_mst wb";
			if (display1 == 2) {
				qry += " join cr_insured_crops b on  a.dcode=b.dcode and a.cr_season=b.season and a.cr_year=b.cropyear and a.cr_crop=b.cropcode "
						+ " and (b.rainfed is null or (b.rainfed='Y' and a.cr_w_draw=1) or (b.rainfed='N' and a.cr_w_draw!=1) ) ";
			}
			qry += " where a.dcode=? and a.mcode=? and cr_vcode=? and cr_year=? and cr_season=? and a.dcode=wb.dcode and a.mcode=wb.mcode and a.cr_vcode=wb.wbvcode ";

			if (!(display == "" || display == null)) {
				if (Integer.parseInt(display) == 1) {
                qry += " and dt_cropins_reg is not null ";
				}
				if (Integer.parseInt(display) == 2) {
                qry += " and dt_cropins_reg is null  ";
				}
				if (Integer.parseInt(display) == 3) {
                qry += " and cr_ins_revalidate is not null and reason_non_auth is null ";
				}
				if (Integer.parseInt(display) == 4) {
                qry += " and cr_ins_revalidate  is not null and reason_non_auth is not null ";
				}
			}

			qry += " order by cr_vcode,kh_no,cr_sno,oc_name ";

			Query insertQuery = (Query) entitymanager.createNativeQuery(qry);
			insertQuery.setParameter(1, dcode);
			insertQuery.setParameter(2, sesmcode);
			insertQuery.setParameter(3, vcode1);
			insertQuery.setParameter(4, year);
			insertQuery.setParameter(5, season);
			
			System.out.println("insertQuery=>" + insertQuery);

			List<Object[]> detailsEntities1 = insertQuery.getResultList();
			System.out.println("detailsEntities=>" + detailsEntities1.size());

			List<CropbookingdetVAModel> list = new ArrayList<CropbookingdetVAModel>();
			for (Object[] row : detailsEntities1) {
				CropbookingdetVAModel cropbookingdetVA = new CropbookingdetVAModel();
				cropbookingdetVA.setFarmingtype((String) row[0].toString());

				cropbookingdetVA.setBookingid((String) row[1].toString());
				cropbookingdetVA.setCr_mand_code((String) row[6].toString());
				cropbookingdetVA.setCr_vcode((String) row[7].toString());
				cropbookingdetVA.setRbkname((String) row[2].toString());
				cropbookingdetVA.setOc_name((String) row[12].toString());
				cropbookingdetVA.setCr_farmeruid((String) row[10].toString());
				cropbookingdetVA.setOc_fname((String) row[13].toString());
				cropbookingdetVA.setKh_no((String) row[17].toString());
				cropbookingdetVA.setCr_sno((String) row[18].toString());
				cropbookingdetVA.setCropname((String) row[39].toString());
				cropbookingdetVA.setVarietyname((String) row[32].toString());
				cropbookingdetVA.setCr_mix_unmix_ext((String) row[25].toString());
				cropbookingdetVA.setCr_sow_date((String) row[34].toString());
				cropbookingdetVA.setCropnature((String) row[33].toString());
				cropbookingdetVA.setWatersource((String) row[35].toString());
				cropbookingdetVA.setIrrmethoddesc((String) row[36].toString());
				cropbookingdetVA.setLgdvname ((String) row[37].toString());
				cropbookingdetVA.setMobileno((String) row[14].toString());
				cropbookingdetVA.setUpdatedby((String) row[39].toString());
				list.add(cropbookingdetVA);

			}

			return list;

		}
	}}
