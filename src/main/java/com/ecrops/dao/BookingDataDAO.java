
package com.ecrops.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.ecrops.entity.Cr_details;
import com.ecrops.model.BookingData;
import com.ecrops.model.Cr_images;

@Repository
public class BookingDataDAO {

	@PersistenceContext
	EntityManager entityManager;

	public List<BookingData> pattadharDetails(String partitionName, String partitionName1,Integer cr_vcode) {
		String sqlQuery ="select a.data_src,a.bookingid as currBookId,a.kh_no as currKhno,a.cr_sno as currSurNo,a.occupant_extent as currTotExt, a.cr_farmeruid as currUid, a.occupname as currOcName, \r\n" + 
				"a.occupfname as currOcFname, b.bookingid as prevBookId,b.kh_no as prevKhno,b.cr_sno as prevSurNo,b.occupant_extent as prevTotExt,b.cr_farmeruid as prevUid,\r\n" + 
				"b.occupname as prevOcName,b.occupfname as prevOcFname, coalesce (b.cropname,' ') as crop,b.cr_crop as prevCropCode, b.variety as prevVariety, b.cr_sow_date as prevSowDate,\r\n" + 
				"b.cr_dist_code as dcode,b.cr_year as cropYear,b.cr_season as season,b.cr_vcode as villCode,b.cr_mix_unmix_ext as previousBookedExt,b.cr_sow_type as sowType,a.peri_status from  " + partitionName + " a inner join ecrop2024.peri_k2024 b on a.cr_farmeruid=b.cr_farmeruid and b.harvest_date >=now() \r\n" + 
				"and a.cr_sno=b.cr_sno and a.cr_dist_code=b.cr_dist_code and a.cr_vcode=b.cr_vcode where a.cr_vcode= "+ cr_vcode +"   and a.cultivator_type is not null and a.anubhavadar_extent is not null  and a.cr_sno not like '%US%' \r\n" + 
				"and (peri_forward is null or peri_forward='M') and (a.cr_vcode,a.bookingid,a.cr_sno,a.kh_no) not in (select cr_vcode,bookingid,cr_sno,kh_no from "+partitionName1+" where cr_vcode="+ cr_vcode +") \r\n" + 
				"union \r\n" + 
				"select a.data_src,a.bookingid as currBookId,a.kh_no as currKhno,a.cr_sno as currSurNo,a.occupant_extent as currTotExt, a.cr_farmeruid as currUid, a.occupname as currOcName, \r\n" + 
				"a.occupfname as currOcFname, b.bookingid as prevBookId,b.kh_no as prevKhno,b.cr_sno as prevSurNo,b.occupant_extent as prevTotExt,b.cr_farmeruid as prevUid,\r\n" + 
				"b.occupname as prevOcName,b.occupfname as prevOcFname, b.cropname as crop,b.cr_crop as prevCropCode, b.variety as prevVariety, b.cr_sow_date as prevSowDate,\r\n" + 
				"b.cr_dist_code as dcode,b.cr_year as cropYear,b.cr_season as season,b.cr_vcode as villCode,b.cr_mix_unmix_ext as previousBookedExt,b.cr_sow_type as sowType,a.peri_status from "+ partitionName +"  a inner join ecrop2024.peri_r2024 b on a.cr_farmeruid=b.cr_farmeruid and b.harvest_date >=now() \r\n" + 
				" and a.cr_sno=b.cr_sno and a.cr_dist_code=b.cr_dist_code and a.cr_vcode=b.cr_vcode  where a.cr_vcode="+ cr_vcode +"   and a.cultivator_type is not null and a.anubhavadar_extent is not null  and a.cr_sno not like '%US%' \r\n" + 
				"and (peri_forward is null or peri_forward='M') and (a.cr_vcode,a.bookingid,a.cr_sno,a.kh_no) not in (select cr_vcode,bookingid,cr_sno,kh_no from "+partitionName1+" where cr_vcode="+ cr_vcode +") " ;
		Query query = entityManager.createNativeQuery(sqlQuery);
		System.out.println("qury-->"+sqlQuery);
		List<Object> objects = query.getResultList();
		List<BookingData> lm = new ArrayList<>();
		try {
			if (objects != null && objects.size() > 0) {
				for (Object patta : objects) {
					System.out.println(patta);
					Object[] row = (Object[]) patta;
					BookingData bookingData = new BookingData();
					bookingData.setDataSrc(row[0].toString());
					bookingData.setCurrBookId((int) row[1]);
					bookingData.setCurrKhno((BigDecimal) (row[2]));
					bookingData.setCurrsurno(row[3].toString());
					bookingData.setCurrtotext((BigDecimal) row[4]);
					bookingData.setCurruid(row[5].toString());
					bookingData.setCurrocname(row[6].toString());
					bookingData.setCurrocfname(row[7].toString());
					bookingData.setPrevBookId((int) row[8]);
					bookingData.setPrevKhno((BigDecimal) (row[9]));
					bookingData.setPrevsurno(row[10].toString());
					bookingData.setPrevtotext((BigDecimal) row[11]);
					bookingData.setPrevuid(row[12].toString());
					bookingData.setPrevocname(row[13].toString());
					bookingData.setPrevocfname(row[14].toString());
					bookingData.setCrop(row[15].toString());
					bookingData.setPrevcropcode((int) row[16]);
					bookingData.setPrevVariety((int) row[17]);
					bookingData.setPrevsowdate((Date) row[18]);
					bookingData.setDcode((BigDecimal) row[19]);
					bookingData.setCropyear((BigDecimal) row[20]);
					bookingData.setSeason(row[21].toString());
					bookingData.setVillcode((int) row[22]);
					bookingData.setPreviousbookedext((BigDecimal) row[23]);
					bookingData.setSowtype((int) row[24]);
					bookingData.setPeri_status((String) row[25]);
					lm.add(bookingData);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return lm;
	}

	public List<Cr_details> getPrevCr_details(Integer prevBookId, String prevsurno, Integer prevKhno,
			Integer cropCode, Integer varietyCode, Date prevsowdate, Integer crop_year, String crop_season, String ptable) {
		System.out.println("jhgfsfdhbd");
		try {
			String qry = "select bookingid,cr_dist_code,cr_mand_code,cr_vcode,kh_no,cr_sno,cr_year,cr_season, cr_sow_type ,cr_mix_unmix_ext ,cr_no ,cr_w_draw ,cr_sow_date ,dcode ,mcode, irrmethod , data_src , cropseed_scheme \r\n"
					+ ",croptypebyirr , tarahacode ,  irrcategory , soc_category , cr_farmeruid ,owner_tenant , occupname ,occupfname ,\r\n"
					+ " tot_extent ,occupant_extent ,oc_name , oc_fname ,anubhavadar_name , landownership_type ,cultivator_type ,seedprod_agency  from "+ ptable +" where bookingid=? and cr_sno=? and kh_no=? and cr_crop=? and  variety=? and cr_sow_date=? and cr_year=? and cr_season=?";

			Query query = entityManager.createNativeQuery(qry);
			query.setParameter(1, prevBookId);
			query.setParameter(2, prevsurno);
			query.setParameter(3, prevKhno);
			query.setParameter(4, cropCode);
			query.setParameter(5, varietyCode);
			query.setParameter(6, prevsowdate);
			query.setParameter(7, crop_year);
			query.setParameter(8, crop_season);

			List<Object[]> objects = query.getResultList();
			System.out.println("objects--->"+objects.size());

			List<Cr_details> crDetailsList = new ArrayList<>();

			for (Object object : objects) {
				Object[] row = (Object[]) object;
				Cr_details crDetails = new Cr_details();
				crDetails.setBookingid((Integer) row[0]);
				crDetails.setCr_dist_code( (BigDecimal) row[1]);
				crDetails.setCr_mand_code( (BigDecimal) row[2]);
				crDetails.setCr_vcode( (int) row[3]);
				crDetails.setKh_no((BigDecimal) row[4]);
				crDetails.setCr_sno((String) row[5]);
				crDetails.setCr_year((BigDecimal) row[6]);
				crDetails.setCr_season( (Character) row[7]);
				crDetails.setCr_sow_type((int) row[8]);
				crDetails.setMixUnmixExt((BigDecimal) row[9]);
				crDetails.setCr_no((String) row[10]);
				crDetails.setwDraw((BigDecimal) row[11]);
				crDetails.setCr_sow_date((Date) row[12]);
				crDetails.setDcode((int) row[13]);
				crDetails.setMcode((int) row[14]);
				crDetails.setIrrMethod((int) row[15]);
				crDetails.setDataSrc((String) row[16]);
				crDetails.setCropseed_scheme((Character) row[17]);
				crDetails.setCroptypebyirr((int) row[18]);
				crDetails.setTarahacode((Character) row[19]);
				crDetails.setIrrcategory( (int) row[20]);
				crDetails.setSoc_category((int) row[21]);
				crDetails.setCr_farmeruid((String) row[22]);
				crDetails.setOwner_tenant((Character) row[23]);
				crDetails.setOccupname((String) row[24]);
				crDetails.setOccupfname((String) row[25]);
				crDetails.setTot_extent((BigDecimal) row[26]);
				crDetails.setOccupant_extent((BigDecimal) row[27]);
				crDetails.setOc_name((String) row[28]);
				crDetails.setOc_fname((String) row[29]);
				crDetails.setAnubhavadar_name((String) row[30]);
				crDetails.setLandownership_type((Character) row[31]);
				crDetails.setCultivator_type((Character) row[32]);
				crDetails.setSeedprod_agency((String) row[33]);
				crDetailsList.add(crDetails);
			}

			return crDetailsList;

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Transactional
	public int insertToCrImages(String partitionName2, int bookingid, BigDecimal crDistCode, BigDecimal crMandCode,
			int crVcode, int khNo, String crSno, int crYear, String crSeason, int dcode, int mcode,
			int crCrop, Character cropseedScheme, String photo, String partKey) {

		String query = "insert into " + partitionName2 + " (bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, dcode, mcode, cr_crop, cropseed_scheme, part_key, photo) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		int result = 0;

		try {
			result = entityManager.createNativeQuery(query).setParameter(1, bookingid).setParameter(2, crDistCode)
					.setParameter(3, crMandCode).setParameter(4, crVcode).setParameter(5, khNo).setParameter(6, crSno)
					.setParameter(7, crYear).setParameter(8, crSeason).setParameter(9, dcode).setParameter(10, mcode)
					.setParameter(11, crCrop).setParameter(12, cropseedScheme).setParameter(13, partKey)
					.setParameter(14, photo).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Transactional
	public int insertToCrDetails(String partitionName, int bookingId, BigDecimal districtCode, BigDecimal mandalCode,
			int villageCode, int khataNo, String surveyNo, int cropYear, String cropSeason, int sowType,
			int cropCode, Double exetntId, String cropNumber, BigDecimal waterDraw, Date sowDate, String userid,
			int distcode, int mandCode, int varietyCode, int irrMethod, String dataSource,
			Character seedShceme, int cropType, Character tarahaCode, int irrCategory, int socCategory,
			String farmerUid, Character ownerTen, String occupName, String occupFname, BigDecimal curreTotExt,
			BigDecimal currOccupantExt, String ocName, String ocFname, String anubhavdarName, Character landOwnerType,
			Character cultType, int prevBookingId, String seedProdAgency, String partKey) {
		System.out.println("partKey--->"+partKey);
		System.out.println("cr_sno---->"+surveyNo);
		String query = "INSERT INTO " + partitionName
				+ "(bookingid,cr_dist_code,cr_mand_code,cr_vcode,kh_no,cr_sno,cr_year,cr_season,"
				+ "cr_sow_type,cr_crop,cr_mix_unmix_ext,cr_no,cr_w_draw, cr_sow_date,updatedby,dcode,mcode,variety,irrmethod,"
				+ "data_src, cropseed_scheme,croptypebyirr,tarahacode,irrcategory,soc_category, dt_ins_reg,cr_farmeruid,"
				+ "owner_tenant,occupname,occupfname,tot_extent, occupant_extent,oc_name,oc_fname,anubhavadar_name,"
				+ "landownership_type,cultivator_type,part_key,refbookingid,seedprod_agency,seed_production,geo_referred) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		int result = 0;

		try {
			result = entityManager.createNativeQuery(query).setParameter(1, bookingId)
					.setParameter(2, districtCode).setParameter(3, (mandalCode))
					.setParameter(4, (villageCode)).setParameter(5, (khataNo))
					.setParameter(6, (surveyNo))
					.setParameter(7, (cropYear)).setParameter(8, cropSeason)
					.setParameter(9, (sowType)).setParameter(10, cropCode)
					.setParameter(11, exetntId).setParameter(12, cropNumber)
					.setParameter(13, waterDraw).setParameter(14, sowDate)
					.setParameter(15, userid).setParameter(16, (distcode))
					.setParameter(17, (mandCode)).setParameter(18, (varietyCode))
					.setParameter(19, (irrMethod)).setParameter(20, dataSource)
					.setParameter(21, seedShceme).setParameter(22, (cropType))
					.setParameter(23, tarahaCode).setParameter(24, (irrCategory))
					.setParameter(25, (socCategory)).setParameter(26, farmerUid)
					.setParameter(27, ownerTen).setParameter(28, occupName).setParameter(29, occupFname)
					.setParameter(30, (curreTotExt))
					.setParameter(31, (currOccupantExt)).setParameter(32, ocName)
					.setParameter(33, ocFname).setParameter(34, anubhavdarName).setParameter(35, landOwnerType)
					.setParameter(36, cultType).setParameter(37, partKey)
					.setParameter(38, (prevBookingId)).setParameter(39, seedProdAgency).setParameter(40, 2).setParameter(41, "N").executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getRbkUserid(String qry1) {
		Query query = entityManager.createNativeQuery(qry1);
		Object obj = query.getSingleResult();
		return obj.toString();
	}

	public List<Cr_images> getPrevCr_Images(Integer prevBookId, String prevsurno, Integer prevKhno,  Integer crop_year, String crop_season, String ptable) {
		try {
			String qry = "select bookingid,cr_dist_code,cr_mand_code,cr_vcode,kh_no,cr_sno,photo from "+ptable+" where bookingid=? and cr_sno=? and kh_no=? and cr_year=? and cr_season=?";
			Query query = entityManager.createNativeQuery(qry);
			query.setParameter(1, prevBookId);
			query.setParameter(2, prevsurno);
			query.setParameter(3, prevKhno);
			query.setParameter(4, crop_year);
			query.setParameter(5, crop_season);

			List<Object[]> objects = query.getResultList();
			System.out.println("objects--->"+objects.size());
			List<Cr_images> crDetailsList = new ArrayList<>();

			for (Object object : objects) {
				Object[] row = (Object[]) object;

				Cr_images crImages = new Cr_images();
				crImages.setBookingid((Integer) row[0]);
				crImages.setCr_dist_code( (BigDecimal) row[1]);
				crImages.setCr_mand_code( (BigDecimal) row[2]);
				crImages.setCr_vcode( (int) row[3]);
				crImages.setKh_no((BigDecimal) row[4]);
				crImages.setCr_sno((String) row[5]);
				crImages.setPhoto((String) row[6]);
				crDetailsList.add(crImages);
			}
			return crDetailsList;

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<Cr_details> getCurrCr_Booking_Partition(Integer bookingId, String surveyNo, Integer khNo, String ptable) {
		try {
			String qry = "select occupant_extent, tot_extent from " + ptable + " where bookingid=? and cr_sno=? and kh_no=?";
			Query query = entityManager.createNativeQuery(qry);
			query.setParameter(1, bookingId);
			query.setParameter(2, surveyNo);
			query.setParameter(3, khNo);

			List<Object[]> objects = query.getResultList();
			List<Cr_details> crDetailsList = new ArrayList<>();
			for (Object object : objects) {
				Object[] row = (Object[]) object;

				Cr_details crDetails = new Cr_details();
				crDetails.setOccupant_extent((BigDecimal) row[0]);
				crDetails.setTot_extent( (BigDecimal) row[1]);
				crDetailsList.add(crDetails);
			}

			return crDetailsList;

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Transactional
	public int updateCr_booking_partition(String partitionName1, int prevBookId,  int dcode, int bookingId,
			int vcode, int khNo, String crSno, String peri_forward) {

		String query = "update " + partitionName1 + " set peri_forward=?, refbookingid=? where cr_dist_code=? and bookingid=? and  cr_vcode=?  and kh_no=? and cr_sno=?";
		int result = 0;
		try {
			result = entityManager.createNativeQuery(query).setParameter(1, peri_forward).
					 setParameter(2, prevBookId).setParameter(3, dcode).setParameter(4, bookingId)
					.setParameter(5, vcode).setParameter(6, khNo).setParameter(7, crSno).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional
	public int updateCr_booking_partition1(String partitionName1, int prevBookId,  int dcode, int bookingId,
			int vcode, int khNo, String crSno, String peri_forward) {

		String query = "update " + partitionName1 + " set peri_forward =?,culti_ext_upd_flag=?, refbookingid=? where cr_dist_code=? and bookingid=? and  cr_vcode=?  and kh_no=? and cr_sno=?";

		int result = 0;

		try {
			result = entityManager.createNativeQuery(query).setParameter(1, peri_forward).setParameter(2, "Y")
					.setParameter(3, prevBookId).setParameter(4, dcode).setParameter(5, bookingId)
					.setParameter(6, vcode).setParameter(7, khNo).setParameter(8, crSno).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	@Transactional
	public int notToForward(String partitionName1, String remarks, int prevBookId,  int dcode, int bookingId,
			int vcode, int khNo, String crSno, String peri_forward) {
		String query = "update " + partitionName1 + " set peri_forward=?, peri_remarks=?, peri_status='N', refbookingid=? where cr_dist_code=? and bookingid=? and  cr_vcode=?  and kh_no=? and cr_sno=?";
		int result = 0;

		try {
			result = entityManager.createNativeQuery(query).setParameter(1, peri_forward).setParameter(2, remarks)
					.setParameter(3, prevBookId).setParameter(4, dcode).setParameter(5, bookingId)
					.setParameter(6, vcode).setParameter(7, khNo).setParameter(8, crSno).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
