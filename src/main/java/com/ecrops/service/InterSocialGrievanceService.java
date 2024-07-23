package com.ecrops.service;

import java.math.BigDecimal; 
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import com.ecrops.dto.crop.response.VcodeWbvnameForSocialGrievance;
import com.ecrops.entity.crop.InterCropDetailsEntity;

@Service
@Transactional

public class InterSocialGrievanceService {

	@PersistenceContext
	private EntityManager entityManager;

	public List<VcodeWbvnameForSocialGrievance> getVillageCodeAndName(int vscode) {

		String qry1 = "select  vcode, wbvname from ecrop2023.villsec_rev_v where vscode=? ";

		Query query = entityManager.createNativeQuery(qry1);
		query.setParameter(1, vscode);
		List<Object[]> resultList = query.getResultList();

		List<VcodeWbvnameForSocialGrievance> getVcodeWbvname = new ArrayList<>();
		for (Object[] ob : resultList) {

			int vcode = (int) ob[0];
			String wbvname = (String) ob[1];

			VcodeWbvnameForSocialGrievance getData = new VcodeWbvnameForSocialGrievance();

			getData.setVcode(vcode);
			getData.setWbvname(wbvname);

			getVcodeWbvname.add(getData);

		}
		return getVcodeWbvname;

	}

	public List<InterCropDetailsEntity> getInterCropData(String booking_id, int vcode, String wbdcode, String season) {

		// ,int cropyear

		int cropyear = 2022;

		String partitionName = "cr_crop_det_new_v_";

		if (Integer.parseInt(wbdcode) <= 9) {  
			partitionName = partitionName + season + "0" + wbdcode + cropyear;
		} else {
			partitionName = partitionName + season + wbdcode + cropyear;
		}

		String sql1 = "select  distinct a.kh_no,a.cr_sno,a.bookingid,"
				+ " cr_farmeruid,tot_extent,oc_name,oc_fname,cropname,varietyname,a.cr_mix_unmix_ext,b.description as cropno,c.wsrcdesc as watersource, d.naturedesc as cropnature,a.cr_sow_date, farmingtype, a.cr_crop, a.variety,"
				+ " a.cr_dist_code,a.cr_mand_code,a.cr_year,a.cr_season,a.cr_no,mname,vname, wsrcid, a.cr_sow_type,a.cropseed_scheme,d.id "
				+ "from " + partitionName
				+ " a, cropnumber b, waterresources c, cropnature d, cropseed_scheme e,farmer_grievances f where a.cr_vcode= ? "
				+ " and  CAST(a.cr_no AS TEXT)=CAST(b.id AS TEXT) and a.cr_w_draw=c.wsrcid and a.cr_sow_type=d.id and a.cropseed_scheme=e.cropschtype and  ekyc='Y' and mark_delet is null and a.cr_sow_type=1  and mismatch is null and a.cr_mix_unmix_ext > 0 and "
				+ "(a.bookingid,a.cr_crop,a.cr_no,a.variety,a.cr_sow_date,a.cr_vcode) not in(select bookingid,cr_crop,cr_no,variety,cr_sow_date,cr_vcode from farmer_grievances)";

		if (!booking_id.equals("")) {
			sql1 += " and a.bookingid='" + booking_id + "'";
		}

		Query query = entityManager.createNativeQuery(sql1);
		query.setParameter(1, vcode);

		List<Object[]> resultList = query.getResultList();

		// int j = 0;
		List<InterCropDetailsEntity> pojoList = new ArrayList<>();
		for (Object[] data : resultList) {
			BigDecimal kh_no = (BigDecimal) data[0];
			String cr_sno = (String) data[1];
			int bookingid = (int) data[2];
			String cr_farmeruid = (String) data[3];
			BigDecimal tot_extent = (BigDecimal) data[4];
			String oc_name = (String) data[5];
			String oc_fname = (String) data[6];
			String cropname = (String) data[7];
			String varietyname = (String) data[8];
			BigDecimal cr_mix_unmix_ext = (BigDecimal) data[9];
			String cropno = (String) data[10];
			String watersource = (String) data[11];
			String cropnature = (String) data[12];
			Date cr_sow_date = (Date) data[13];
			String farmingtype = (String) data[14];
			int cr_crop = (int) data[15];
			int variety = (int) data[16];
			BigDecimal cr_dist_code = (BigDecimal) data[17];
			BigDecimal cr_mand_code = (BigDecimal) data[18];
			BigDecimal cr_year = (BigDecimal) data[19];
			char cr_season = (char) data[20];
			String cr_no = (String) data[21];
			String mname = (String) data[22];
			String vname = (String) data[23];
			int wsrcid = (int) data[24];
			int cr_sow_type = (int) data[25];
			char cropseed_schme = (char) data[26];
			int id = (int) data[27];

			InterCropDetailsEntity setData = new InterCropDetailsEntity();

			setData.setKh_no(kh_no);
			setData.setCr_sno(cr_sno);
			setData.setBookingid(bookingid);
			setData.setCr_farmeruid(cr_farmeruid);
			setData.setTot_extent(tot_extent);
			setData.setOc_name(oc_name);
			setData.setOc_fname(oc_fname);
			setData.setCropname(cropname);
			setData.setVarietyname(varietyname);
			setData.setCr_mix_unmix_ext(cr_mix_unmix_ext);
			setData.setCropno(cropno);
			setData.setWatersource(watersource);
			setData.setCropnature(cropnature);
			setData.setCr_sow_date(cr_sow_date);
			setData.setFarmingtype(farmingtype);
			setData.setCr_crop(cr_crop);
			setData.setVariety(variety);
			setData.setCr_dist_code(cr_dist_code);
			setData.setCr_mand_code(cr_mand_code);
			setData.setCr_year(cr_year);
			setData.setCr_season(cr_season);
			setData.setCr_no(cr_no);
			setData.setMname(mname);
			setData.setVname(vname);
			setData.setWsrcid(wsrcid);
			setData.setCr_sow_type(cr_sow_type);
			setData.setCropseed_schme(cropseed_schme);
			setData.setId(id);

			pojoList.add(setData);
		}
		return pojoList;
	}

	public String isBlankInt(String str) {
		str = str == null ? "0" : str;

		return str;
	}

	public boolean isNum(String str) {
		str = isBlankInt(str);
		String str1 = str;
		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(str1);
		boolean b = m.matches();
		return b;

	}

	public boolean insertCropDetails(String wbldcode, String mandcode, String cropyear, String seasonStr, String season,
			String vscode, String crpNature, String cropName, String varietyName, String tot_Extent, String occupFather,
			String aadharId, String bookingIds, String crNos, String khatano, String surveyno, String sowDt,
			String farmername, String ratioOne, String applID, String mobId, String applName, String crcropCode,
			String varietyCode, String crpNatSuges, String clientIp) {
		
		
		System.out.println("wbldcode--->"+wbldcode); 
		System.out.println("vscode--->"+vscode); 
		System.out.println("bookingIds--->"+bookingIds); 


		String[] cr_no = crNos.split("\\$\\$");
		//System.out.println("cr_no--->"+cr_no); 

		String cropNatureId = crpNature;
		String[] sowDtArray = sowDt.split("\\$\\$");
		String[] totExtent = tot_Extent.split("\\$\\$");

		String[] cropNameArray = cropName.split("\\$\\$");
		String[] varietyNameArray = varietyName.split("\\$\\$");
		//System.out.println("varietyNameArray--->"+varietyNameArray); 

		String[] ratioNumer = ratioOne.split("\\$\\$");

		System.err.println("ratioNumer len : " + ratioNumer.length);

		int valid = 1;
		for (int i = 0; i < bookingIds.length(); i++) {
			if (!(isNum(bookingIds))) {
				valid = 0;
			}
			break;
		}

		if (valid == 0) {
			throw new RuntimeException("Improper Data");
		}

		try {
//			entityManager.getTransaction().begin();

			double numY = 0;
			double numX = 0;
			String corr_type = "2";

			String QRY_INS_USER_DET = "insert into farmer_grievances("
					+ "	cr_dist_code, cr_mand_code, cr_vcode,  bookingid, cr_sno, "
					+ "	kh_no, cr_crop, variety, cr_sow_date, cr_no, "
					+ "	dt_crt, applicant_name, applicant_mobile, applicant_id, clientip, "
					+ "	famername_crd, fathername_crd, uid_crd,cr_mix_unmix_ext,calculated_area,"
					+ "correction_type,cr_sow_type,cr_sow_type_sug,re_submit_flag) " + " values("
					+ "	:cr_dist_code,:cr_mand_code,:cr_vcode,:bookingid,:cr_sno,"
					+ "	:kh_no,:cr_crop,:variety,:cr_sow_date,:cr_no,"
					+ "	now(),:applicant_name,:applicant_mobile,:applicant_id,:clientip,"
					+ "	:famername_crd,:fathername_crd,:uid_crd,:cr_mix_unmix_ext,:calculated_area,"
					+ "	:correction_type,:cr_sow_type,:cr_sow_type_sug,:re_submit_flag)";

			for (int i = 0; i < cropNameArray.length; i++) {
				
				if (ratioNumer.length > 1) {
					numX = Double.parseDouble(ratioNumer[0]);
					numY = Double.parseDouble(ratioNumer[1]);
				}
				double bookedExt = Double.parseDouble(totExtent[i]);
				double fistrArea = (bookedExt * (numX)) / (numX + numY);
				double secondArea = (bookedExt * (numY)) / (numX + numY);
				double finalArea;
				if (i == 0) {
					finalArea = Double.valueOf(fistrArea);
				} else {
					finalArea = Double.valueOf(secondArea);
				}

				entityManager.createNativeQuery(QRY_INS_USER_DET)
						.setParameter("cr_dist_code", Integer.parseInt(wbldcode))
						.setParameter("cr_mand_code", Integer.parseInt(mandcode))
						.setParameter("cr_vcode", Integer.parseInt(vscode))
						.setParameter("bookingid", Integer.parseInt(bookingIds)).setParameter("cr_sno", surveyno)

						.setParameter("kh_no", Integer.parseInt(khatano))
						.setParameter("cr_crop", Integer.parseInt(cropNameArray[i]))
						.setParameter("variety", Integer.parseInt(varietyNameArray[i]))
						.setParameter("cr_sow_date", Date.valueOf(sowDtArray[i])).setParameter("cr_no", cr_no[i])

						.setParameter("applicant_name", applName).setParameter("applicant_mobile", mobId)
						.setParameter("applicant_id", applID).setParameter("clientip", clientIp)

						.setParameter("famername_crd", farmername).setParameter("fathername_crd", occupFather)
						.setParameter("uid_crd", aadharId)
						.setParameter("cr_mix_unmix_ext", Double.valueOf(totExtent[i]))
						.setParameter("calculated_area", Double.valueOf(finalArea))

						.setParameter("correction_type", Integer.parseInt(corr_type))
						//.setParameter("cr_sow_type",Integer.parseInt(cropNatureId))
						.setParameter("cr_sow_type",Integer.parseInt( cr_no[i]))
						.setParameter("cr_sow_type_sug", Integer.parseInt(crpNatSuges))
						.setParameter("re_submit_flag", "Y").executeUpdate();
			}

			// Update query
			String partitionName = "cr_details_";

			if (Integer.parseInt(wbldcode) <= 9) {
				partitionName = partitionName + season + "0" + wbldcode + cropyear;
			} else {
				partitionName = partitionName + season + wbldcode + cropyear;
			}
			String griev_comp = "Y";
			String qry2 = "update " + partitionName
					+ " set griev_comp=:griev_comp, cr_mix_unmix_ext=:cr_mix_unmix_ext, "
					+ "cr_sow_type=:cr_sow_type where cr_dist_code=:cr_dist_code and "
					+ "bookingid=:bookingid and cr_crop=:cr_crop and cr_no=:cr_no and cr_vcode=:cr_vcode and variety=:variety";
			System.out.println("UPDATE TABLE NAME ===== " + partitionName);

			for (int j = 0; j < cropNameArray.length; j++) {
				if (ratioNumer.length > 1) {
					numX = Double.parseDouble(ratioNumer[0]);
					numY = Double.parseDouble(ratioNumer[1]);
				}
				double bookedExt = Double.parseDouble(totExtent[j]);
				double fistrArea = (bookedExt * (numX)) / (numX + numY);
				double secondArea = (bookedExt * (numY)) / (numX + numY);
				double finalArea;
				if (j == 0) {
					finalArea = Double.valueOf(fistrArea);
				} else {
					finalArea = Double.valueOf(secondArea);
				}

				entityManager.createNativeQuery(qry2).setParameter("griev_comp", griev_comp)
						.setParameter("cr_mix_unmix_ext", finalArea)
						//.setParameter("cr_sow_type",Integer.parseInt(cropNatureId))
						.setParameter("cr_sow_type",Integer.parseInt(cr_no[j]))
						.setParameter("cr_dist_code", Integer.parseInt(wbldcode))
						.setParameter("bookingid", Integer.parseInt(bookingIds))
						.setParameter("cr_crop", Integer.parseInt(cropNameArray[j]))
						.setParameter("cr_no", cr_no[j])
						.setParameter("cr_vcode", Integer.parseInt(vscode))
						.setParameter("variety", Integer.parseInt(varietyNameArray[j])).executeUpdate();

			}
//			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
//			entityManager.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}

}
