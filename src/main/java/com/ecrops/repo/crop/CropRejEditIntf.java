package com.ecrops.repo.crop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.dto.webland.CrDetailsEntity;
import com.ecrops.entity.crop.Validations;

@Service
@Transactional
@Component
public class CropRejEditIntf {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public List<CrDetailsEntity> cropRejEditDetails(String cropyear, String season, String searchParam, String surveyNo,
			String aadharNo, String khataNo, String vcode, String wbdcode, String prejcode, String activeYear) {
		String qry = "";
	
		try {
			int j = 0;
			Validations validations = new Validations();
			boolean validflag = false;
			String tseason = "";

			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			if (cropyear != null && searchParam != null && vcode != null) {
				if (Validations.checkseason(cropyear) && validations.check_number(searchParam)
						&& validations.check_number(vcode) && validations.checkmaxLength(vcode, 7)
						&& validations.checkminLength(vcode, 6)) 
				{
				if ((surveyNo == null || surveyNo != "" )
					|| (khataNo == null || (khataNo != "" ))
					||(aadharNo == null || (aadharNo != ""))){
					validflag = true;
					}
				}
			}

			if (validflag) {

				String vrorejdetTab = "vro_rej_details";
				String table = "cr_details_" + season + wbdcode + cropyear;
					int year= Integer.parseInt(activeYear);
				System.out.println("activeYear ---> "+activeYear+"    cropyear -----> "+cropyear);
				if (year>=2023) {
					table = "ecrop" + activeYear + "." + table;
					vrorejdetTab = "ecrop" + activeYear + "." + vrorejdetTab;
				}
				tseason = "'" + season + "'";

				qry = "SELECT crpn.cropname,var.varietyname, wr.wsrcdesc,cs.cropschdesc, cropseed_scheme, cr_mix_unmix_ext, seed_production, irrmethod, irm.irgdesc, cr_w_draw, oc_name, oc_fname, cr_sno, kh_no, occupname, occupfname, cr_vcode, bookingid, cr_crop, cr_no, cr_sow_date, variety, cr_farmeruid , cr_vcode FROM "
						+ table
						+ " a, cropirrgmethod_master irm, cropseed_scheme cs, cropnames crpn, cr_variety_master var, waterresources wr WHERE a.cr_crop = crpn.cropid and a.variety = var.varietycode and a.cr_w_draw = wr.wsrcid and cs.cropschtype = a.cropseed_scheme AND irrmethod = irm.irgcode AND cr_vcode = "
						+ vcode + " AND cr_year = " + cropyear + " AND cr_season = " + tseason;

				if (searchParam.equals("1")) {
					qry += " AND cr_sno = '" + surveyNo + "'";
				} else if (searchParam.equals("2")) {
					qry += " AND kh_no = " + khataNo;
				} else if (searchParam.equals("3")) {
					qry += " AND cr_farmeruid = '" + aadharNo + "'";
				}

				qry += " AND vroauth = 'N' AND vro_verify = 'Y' AND bookingid IN (SELECT bookingid FROM " + vrorejdetTab
						+ " WHERE cr_vcode = " + vcode + " AND rej_reason = " + prejcode + ")"
						+ " AND (bookingid NOT IN (SELECT bookingid FROM ecrop2023.cr_details_org_details WHERE correctiontype = '6' AND cr_vcode = "
						+ vcode + ") "
						+ " OR (bookingid NOT IN (SELECT bookingid FROM ecrop2023.cr_details_org_details WHERE correctiontype = '6' AND cr_vcode = "
						+ vcode + " GROUP BY bookingid HAVING COUNT(*) > 1))) " ;

				
				Query pstmt1 = entityManager.createNativeQuery(qry, CrDetailsEntity.class); // Specify the entity class
				System.out.println("Query correction vro ---------> "+pstmt1);
				List<CrDetailsEntity> resultList = pstmt1.getResultList();
				
//				for (CrDetailsEntity result : resultList) {
//					j++;
//					String bookingId = result.getBookingId();
//					String crcrop = result.getCrCrop();
//					String crno = result.getCrNo();
//					String sowdate = result.getCrSowDate();
//					String variety = result.getVariety();
//					String cropDescription = result.getCropSchDesc();//cropSchDesc
//					String cropseedScheme = result.getCropseedScheme();
//					String mixUnmixExt = result.getCrMixUnmixExt();
//					String seedProduction = result.getSeedProduction();
//					String irrigMethod = result.getIrrMethod();
//					String irrigMethodDesc = result.getIrgDesc();
//					String waterDraw = result.getCrWDraw();
//					String ocName = result.getOcName();
//					String ocFname = result.getOcFname();
//					surveyNo = result.getCrSno();
//					khataNo = result.getKhNo();
//					String occupName = result.getOccupName();
//					String occupFname = result.getOccupFname();
//					String farmerUid = result.getCrFarmerUid();
//					String cr_vcode= result.getCr_vcode();
//				}

				return resultList; // Return the list of CropDetailsDTO
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Entered************************");
		return null; // Return null in case of any failure
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
