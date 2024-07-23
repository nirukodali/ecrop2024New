package com.ecrops.repo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ecrops.dto.CultivatorDto;
import com.ecrops.entity.Cultivator;
import com.ecrops.entity.CultivatorEntity;
import com.ecrops.entity.CultivatorPeri;

@Component
public class AddUpdateCultivatorRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public List<CultivatorEntity> getExtentDetails(String partitionName, String vill_code, String cr_sno) {
		String qry = "select coalesce (avg(tot_extent),0) as tot_ext,coalesce(sum(occupant_extent),0) as occup_ext from " + partitionName
				+ " where cr_vcode=" + vill_code + " and cr_sno='" + cr_sno + "' and cultivator_type is not null";
		List<CultivatorEntity> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();
		if (objects != null && objects.size() > 0) {
			for (Object culti : objects) {
				Object[] row = (Object[]) culti;
				CultivatorEntity pojos = new CultivatorEntity();
				pojos.setTotalExtent((BigDecimal) row[0]);
				pojos.setOccupantExtent((BigDecimal) row[1]);
				data.add(pojos);
			}
		}
		return data;
	}

	@Transactional
	public List<CultivatorPeri> getKharifPerenialDetails(String vill_code, String cr_sno, String khataNo,
			String farmerUid) {
		String qry = "select cropname,varietyname,cr_sno,kh_no,occupname,occupfname,cr_mix_unmix_ext from ecrop2024.peri_k2024 "
				+ "where cr_vcode=" + vill_code + " and cr_sno='" + cr_sno + "' and kh_no=" + khataNo
				+ " and cr_farmeruid='" + farmerUid + "'";
		List<CultivatorPeri> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();
		if (objects != null && objects.size() > 0) {
			for (Object culti : objects) {
				Object[] row = (Object[]) culti;
				CultivatorPeri pojos = new CultivatorPeri();
				pojos.setCropname((String) row[0]);
				pojos.setVarietyname((String) row[1]);
				pojos.setCr_sno((String) row[2]);
				pojos.setKh_no((BigDecimal) row[3]);
				pojos.setOccupname((String) row[4]);
				pojos.setOccupfname((String) row[5]);
				pojos.setCr_mix_unmix_ext((BigDecimal) row[6]);
				data.add(pojos);
			}
		}
		return data;
	}

	@Transactional
	public List<CultivatorPeri> getRabiPerenialDetails(String vill_code, String cr_sno, String khataNo,
			String farmerUid) {
		String qry = "select cropname,varietyname,cr_sno,kh_no,occupname,occupfname,cr_mix_unmix_ext from ecrop2024.peri_r2024 "
				+ "where cr_vcode=" + vill_code + " and cr_sno='" + cr_sno + "' and kh_no=" + khataNo
				+ " and cr_farmeruid='" + farmerUid + "'";
		List<CultivatorPeri> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();
		if (objects != null && objects.size() > 0) {
			for (Object culti : objects) {
				Object[] row = (Object[]) culti;
				CultivatorPeri pojos = new CultivatorPeri();
				pojos.setCropname((String) row[0]);
				pojos.setVarietyname((String) row[1]);
				pojos.setCr_sno((String) row[2]);
				pojos.setKh_no((BigDecimal) row[3]);
				pojos.setOccupname((String) row[4]);
				pojos.setOccupfname((String) row[5]);
				pojos.setCr_mix_unmix_ext((BigDecimal) row[6]);
				data.add(pojos);
			}
		}
		return data;
	}

	@Transactional
	public int updateOwnerDetails(String partitionName, String occup_extent, String cult_type, String userId,
			String periStatus, String vill_code, String bookingId, String khataNo, String cr_sno, String farmerUid,String mobile) {
		BigDecimal mob = new BigDecimal(mobile);
		String qry = "update " + partitionName
				+ " set occupant_extent=?,cultivator_type=?,cult_updatedby=?,cult_updateon=now(),peri_status=?,mobileno=? where cr_vcode=? and bookingid=? and kh_no=? and  cr_sno=? and cr_farmeruid=?";
		entityManager.createNativeQuery(qry).setParameter(1, Float.parseFloat(occup_extent)).setParameter(2, 'O')
				.setParameter(3, userId).setParameter(4, periStatus).setParameter(5,mob).setParameter(6, Integer.parseInt(vill_code))
				.setParameter(7, Integer.parseInt(bookingId)).setParameter(8, Integer.parseInt(khataNo))
				.setParameter(9, cr_sno).setParameter(10, farmerUid).executeUpdate();
		return 1;
	}
	
	@Transactional
	public int updateOwnerDetails(String partitionName, String occup_extent, String cult_type, String userId,
			String periStatus, String vill_code, String bookingId, String khataNo, String cr_sno, String farmerUid) {
		
		String qry = "update " + partitionName
				+ " set occupant_extent=?,cultivator_type=?,cult_updatedby=?,cult_updateon=now(),peri_status=? where cr_vcode=? and bookingid=? and kh_no=? and  cr_sno=? and cr_farmeruid=?";
		entityManager.createNativeQuery(qry).setParameter(1, Float.parseFloat(occup_extent)).setParameter(2, 'O')
				.setParameter(3, userId).setParameter(4, periStatus).setParameter(5, Integer.parseInt(vill_code))
				.setParameter(6, Integer.parseInt(bookingId)).setParameter(7, Integer.parseInt(khataNo))
				.setParameter(8, cr_sno).setParameter(9, farmerUid).executeUpdate();
		return 1;
	}

	@Transactional
	public int updateEnjoyerDetails(String partitionName, String occup_extent, String cult_type, String userId,
			String periStatus, String vill_code, String bookingId, String khataNo, String cr_sno, String farmerUid,String mobile) {
		BigDecimal mob = new BigDecimal(mobile);
		String qry = "update " + partitionName
				+ " set occupant_extent=?,cultivator_type=?,cult_updatedby=?,cult_updateon=now(),peri_status=?, cr_farmeruid=?,mobileno=? where cr_vcode=? and bookingid=? and kh_no=? and  cr_sno=?";
		entityManager.createNativeQuery(qry).setParameter(1, Float.parseFloat(occup_extent)).setParameter(2, cult_type)
				.setParameter(3, userId).setParameter(4, periStatus).setParameter(5, farmerUid).setParameter(6, mob)
				.setParameter(7, Integer.parseInt(vill_code)).setParameter(8, Integer.parseInt(bookingId))
				.setParameter(9, Integer.parseInt(khataNo)).setParameter(10, cr_sno).executeUpdate();
		return 1;
	}

	@Transactional
	public List<CultivatorEntity> getOrgDetailsForCultivator(String partitionName, String bookingId) {
		String qry = "select oc_name, oc_fname, kh_no,cr_sno, tot_extent, "
				+ " anubhavadar_name,anubhavadar_extent, cr_farmeruid, occupname, occupfname, occupant_extent,mobileno,"
				+ " data_src,  updatedby, regno, cr_wsno, geo_reffered, rec_id, "
				+ " sjointoccupant, vs_sel, owner_tenant,emp_code from " + partitionName + " where bookingid=" + bookingId + "";
		List<CultivatorEntity> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();
		if (objects != null && objects.size() > 0) {
			for (Object culti : objects) {
				Object[] row = (Object[]) culti;
				CultivatorEntity pojos = new CultivatorEntity();
				pojos.setOcName(((String) row[0]));
				pojos.setFatherName((String) row[1]);
				pojos.setKhNo((BigDecimal) row[2]);
				pojos.setCrSno((String) row[3]);
				pojos.setTotalExtent((BigDecimal) row[4]);
				pojos.setAnubhavadar_name((String) row[5]);
				pojos.setAnubhavadarExtent((BigDecimal) row[6]);
				pojos.setAadharNo((String) row[7]);
				pojos.setOccupname((String) row[8]);
				pojos.setOccupfname((String) row[9]);
				pojos.setOccupantExtent((BigDecimal) row[10]);
				pojos.setMobileno((BigDecimal) row[11]);
				pojos.setData_src((String) row[12].toString());
				pojos.setUpdatedby((String) row[13]);
				pojos.setRegno((Integer) row[14]);
				pojos.setCr_wsno((BigInteger) row[15]);
				pojos.setGeo_reffered((String) row[16].toString());
				pojos.setRec_id((BigDecimal) row[17]);
				pojos.setSjointoccupant((Integer) row[18]);
				pojos.setVs_sel((String) row[19].toString());
				pojos.setOwner_tenant((String) row[20].toString());
				pojos.setEmp_code((Integer) row[21]);
				data.add(pojos);
			}
		}
		return data;
	}

	@Transactional
	public int updateCultivatorDetails(String partitionName, String ocName, String fatherName, String khNo,
			String crSno, String anubhavadar_name, BigDecimal anubhavadarExtent, String aadharNo, String occupname,
			String occupfname, Double occup_extent, BigDecimal mobileno, Character data_src, Character cultType,
			String updatedby, Integer regno, BigInteger cr_wsno, Character geoReffered, BigDecimal rec_id,
			Integer sjointoccupant, Character vs_sel, String userId, String periStatus, String vill_code,
			String booking_id, String kh_no, String cr_sno, String cr_farmeruid, String partKey, String dist_code,
			String mand_code, String oc_name, String oc_fname, String crop_year, String crop_season,
			BigDecimal totalExtent, Character ownerTenant,String dcode,String mcode,Integer empCode,String mobile) {
		System.out.println(cultType+"----------------------"+empCode);
		BigDecimal mob = new BigDecimal(mobile);
		String qry = "INSERT INTO " + partitionName
				+ "(part_key, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season,oc_name, oc_fname,kh_no, cr_sno, tot_extent, dcode, mcode," /// 13
				+ " anubhavadar_name,anubhavadar_extent, cr_farmeruid, occupname, occupfname,occupant_extent,mobileno, data_src,  " // 8
				+ " cultivator_type, refbookingid,updatedby,regno,peri_status,cult_updatedby,cr_wsno, geo_reffered," // 10,4
				+ " srno_userid,rec_id,sjointoccupant,vs_sel,updateon,cult_updateon,owner_tenant,emp_code) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		entityManager.createNativeQuery(qry).setParameter(1, partKey).setParameter(2, Integer.parseInt(dist_code))
				.setParameter(3, Integer.parseInt(mand_code)).setParameter(4, Integer.parseInt(vill_code))
				.setParameter(5, Integer.parseInt(crop_year)).setParameter(6, crop_season).setParameter(7, occupname) //oc_name
				.setParameter(8, occupfname).setParameter(9, Integer.parseInt(khNo)).setParameter(10, crSno) //oc_fname
				.setParameter(11, (totalExtent)).setParameter(12, Integer.parseInt(dcode))
				.setParameter(13, Integer.parseInt(mcode)).setParameter(14, anubhavadar_name)
				.setParameter(15, anubhavadarExtent).setParameter(16, cr_farmeruid).setParameter(17, oc_name)//occupname
				.setParameter(18, oc_fname).setParameter(19, (occup_extent)) //occupfname
				.setParameter(20, mob).setParameter(21, data_src).setParameter(22, cultType)
				.setParameter(23, Integer.parseInt(booking_id)).setParameter(24, updatedby).setParameter(25, regno)
				.setParameter(26, periStatus).setParameter(27, updatedby).setParameter(28, cr_wsno)
				.setParameter(29, geoReffered).setParameter(30, userId).setParameter(31, rec_id)
				.setParameter(32, sjointoccupant).setParameter(33, vs_sel)
				.setParameter(34, Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()))
				.setParameter(35, Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()))
				.setParameter(36, cultType).setParameter(37, Integer.parseInt(empCode.toString()))
				.executeUpdate();
		return 1;
	}
	
	@Transactional
	public int updateCcrcDetails(String partitionName, String occup_extent, String cult_type, String userId,
			String periStatus, String vill_code, String bookingId, String khataNo, String cr_sno, String farmerUid,String mobile) {
		BigDecimal mob = new BigDecimal(mobile);
		String qry = "update " + partitionName
				+ " set occupant_extent=?,cultivator_type=?,cult_updatedby=?,cult_updateon=now(),peri_status=?,mobileno=? where cr_vcode=? and bookingid=? and kh_no=? and  cr_sno=? and cr_farmeruid=?";
		entityManager.createNativeQuery(qry).setParameter(1, Float.parseFloat(occup_extent)).setParameter(2, cult_type)
				.setParameter(3, userId).setParameter(4, periStatus).setParameter(5, mob).setParameter(6, Integer.parseInt(vill_code))
				.setParameter(7, Integer.parseInt(bookingId)).setParameter(8, Integer.parseInt(khataNo))
				.setParameter(9, cr_sno).setParameter(10, farmerUid).executeUpdate();
		return 1;
	}

	@Transactional
	public void updateRecord(CultivatorDto cultivatorDto,String partition,String bookingid) {
		System.out.println("getCr_vcode----------"+cultivatorDto.getCr_vcode());
		System.out.println("getKhNo----------"+cultivatorDto.getKhNo());
		System.out.println("getCrSno----------"+cultivatorDto.getCrSno());
		System.out.println("getAadharNo----------"+cultivatorDto.getAadharNo());
		System.out.println("bookingid----------"+bookingid);
		String qry = "update " + partition
				+ " set cultivator_type = null where cr_vcode=? and bookingid=? and kh_no=? and  cr_sno=? and cr_farmeruid=?";
		entityManager.createNativeQuery(qry).setParameter(1, Integer.parseInt(cultivatorDto.getCr_vcode().toString()))
				.setParameter(2, Integer.parseInt(bookingid)).setParameter(3, Integer.parseInt(cultivatorDto.getKhNo().toString()))
				.setParameter(4, cultivatorDto.getCrSno()).setParameter(5, cultivatorDto.getAadharNo()).executeUpdate();
		
		
	}



}
