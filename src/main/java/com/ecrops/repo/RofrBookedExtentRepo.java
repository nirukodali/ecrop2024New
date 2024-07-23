package com.ecrops.repo;

import java.util.List; 


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ecrops.entity.RofrBookedExtent;
import com.ecrops.projection.MasterProjections;

public interface RofrBookedExtentRepo extends JpaRepository<RofrBookedExtent, Integer> {

	@Query(value = "select distinct on (a.cropyear,a.season) concat(a.season,'@',cropyear) as col1,concat(b.seasonname,' ',cropyear) as col2 \r\n"
			+ " from activeseason a,season b  where   a.season=b.season and a.active='A' order by a.cropyear,a.season", nativeQuery = true)
	public List<MasterProjections> getAllSeason();

	// =========== e-Fish Details/Rofr==============//
	@Query(value = "select  wbvname,oc_name as ocname,oc_fname as ocfname, occupname as farmer_name,occupfname as father_name,cr_sno as\r\n"
			+ " survey_no,cast(kh_no as character varying) as khno, cast(occupant_extent as character varying) as occupant_extent,\r\n"
			+ " cast(tot_extent  as character varying) as tot_extent  from\r\n"
			+ " ecrop2023.cr_booking_nwb a,wbvillage_mst_v b  where data_src=:dataSrc \r\n"
			+ " and a.cr_vcode=b.wbvcode  and a.dcode=:dcode and a.mcode=:mcode order by wbvname ", nativeQuery = true)
	public List<EfishDetailsR> efishDetailsR(@Param("dataSrc") String dataSrc,
			                                @Param("dcode") Integer dcode,
			                                @Param("mcode") Integer mcode);

	interface EfishDetailsR {
		String getWbvname();
		String getOcname();
		String getOcfname();
		String getFarmer_name();
		String getFather_name(); 
		String getSurvey_no();
		String getKhno();
		String getOccupant_extent();
		String getTot_extent();

	}

	@Query(value = " select b.dcode,b.mcode,village_name as wbvname,pattadar_name as ocname,pattadar_father_name as ocfname,booking_available,\r\n"
			+ "  allowable_ext,occupant_name  as farmer_name,occupant_father_name  as father_name, cr_sno  as survey_no, kh_no, \r\n"
			+ "  occupant_extent, total_extent as tot_extent from ecrop2023.cr_details_efish_2023 a,"
			+ "  wbvillage_mst b where booking_available='Y'\r\n"
			+ "  and  \r\n" + "  cast(a.cr_vcode  as character varying)=\r\n"
			+ "  cast(b.wbvcode  as character varying)  \r\n"
			+ "  and  b.dcode=:dcode and b.mcode=:mcode order by wbvname", nativeQuery = true)
	public List<EfishDetailsC> efishDetailsC(
			@Param("dcode") Integer dcode, @Param("mcode") Integer mcode);

	interface EfishDetailsC {
		Integer getDcode();

		Integer getMcode();

		String getWbvname();

		String getOcname();

		String getOcfname();

		String getBooking_available();

		String getAllowable_ext();

		String getFarmer_name();

		String getFather_name();

		String getSurvey_no();

		String getKh_no();

		String getOccupant_extent();

		String getTot_extent();
		;

	}
	
	@Query(value="select occupname,caste,occupfname,cr_sno,wbvname,wbmname,cropname, cast(bookedext as character varying) as bookedext ,\r\n"
			+ " cast(mobileno as character varying )as mobileno ,varietyname,cr_sow_date \r\n"
			+ " from(select occupname,soc_category,occupfname,cr_sno,wbvname,wbmname,cropname, bookedext,mobileno,varietyname,cr_sow_date \r\n"
			+ " from (select occupname,soc_category,occupfname,cr_sno,cr_vcode,cr_mand_code,cropname, bookedext,mobileno,varietyname,\r\n"
			+ "cr_sow_date from (select occupname,soc_category,occupfname,cr_sno,cr_vcode,cr_mand_code,cropname, bookedext,mobileno,variety,\r\n"
			+ "cr_sow_date from (select occupname,soc_category,occupfname,cr_sno,cr_vcode,cr_mand_code,cr_crop,cr_mix_unmix_ext as bookedext,\r\n"
			+ "mobileno,variety,cr_sow_date  from ecrop2023.cr_details_R042023 where cr_mand_code=:mcode  and cast(dt_ins_reg as character varying)= :date )a\r\n"
			+ "inner join cropnames b on a.cr_crop=b.cropid)m inner join cr_variety_master n on m.variety=n.varietycode)p inner join \r\n"
			+ "wbvillage_mst q on p.cr_vcode=q.wbvcode)c inner join caste_mst d on cast(c.soc_category  as character varying)=d.castecode\r\n"
			+ "order by wbmname,wbvname",nativeQuery=true)
	public List<FarmerDetails> farmerDetails(@Param("mcode") Integer mcode,@Param("date") String date);
	
	interface FarmerDetails{
		String getOccupname();
		String getCaste();
		String getOccufname();
		String getCr_sno();
		String getWbvname();
		String getBookedext();
		String getMobileno();
		String getVarietyname();
		String getCr_sow_date();
		
	}
	
}