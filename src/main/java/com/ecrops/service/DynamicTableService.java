package com.ecrops.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ecrops.entity.Cr_BookingEntity;
import com.ecrops.entity.Cr_Booking_PartitionEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicTableService {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public DynamicTableService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String findSurveyNo(String dynamicTableName, int wbvcode, String surveyno) {
		System.out.println("usurveyno"+surveyno);
	    String sql = "SELECT cr_sno FROM " + dynamicTableName + " WHERE data_src in ('A','B','F','G')  and cr_vcode = ? AND (cr_sno = ? or cr_sno = ?)";

	    String cr_sno = null;
	    try {
	    	// System.out.println("befus qry::"+sql);
	        cr_sno = jdbcTemplate.queryForObject(sql, String.class, wbvcode, surveyno,"US-"+surveyno); System.out.println("sql is ::"+sql);
	        //System.out.println("us qry::"+sql);
	    } catch (EmptyResultDataAccessException e) {
	    e.printStackTrace();
	    }
//System.out.println("cr_sno "+cr_sno);
	    return cr_sno;
	}

	@SuppressWarnings("deprecation")
	public List<Cr_BookingEntity> getEntitiesFromDynamicTable(String dynamicTableName, Integer wbvcode, String surveyno) {
	    System.out.println("executed");
	    String sql = "SELECT * FROM " + dynamicTableName + " WHERE data_src in ('A','B','F','G')  and cr_vcode = ? AND cr_sno = ?";
	    return jdbcTemplate.query(sql, new Object[]{wbvcode, "US-" + surveyno}, (resultSet) -> {
	        List<Cr_BookingEntity> entities = new ArrayList<>();
	        while (resultSet.next()) {
	            Cr_BookingEntity entity = new Cr_BookingEntity();
	            entity.setCr_dist_code(resultSet.getInt("cr_dist_code"));
	            entities.add(entity);
	        }
	        return entities;
	    });
	}

	public void insertIntoDynamicTable(String dynamicTableName, String crSno, String part_key, int cr_dist_code,
			int cr_mand_code, int cr_vcode, int cr_year, String cr_season, String cr_farmeruid, int kh_no,
			String oc_name, String oc_fname, String occupname, String occupfname, Double tot_extent, Double occupant_extent, String gender,long mobileno,Integer dcode,Integer mcode) {
		String owner_tenant = "O";
		int cr_wsno=0;
		String sql = "INSERT INTO " + dynamicTableName
				+ " (cr_sno,part_key,cr_dist_code,cr_mand_code,cr_vcode,cr_year,cr_season,cr_farmeruid,kh_no,owner_tenant,oc_name,oc_fname,occupname,occupfname,tot_extent,occupant_extent,gender,mobileno,data_src,dcode,mcode,cr_wsno,regno,sjointoccupant) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'N',?,?,?,0,0)";

		jdbcTemplate.update(sql, crSno, part_key, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season,
				cr_farmeruid, kh_no, owner_tenant, oc_name, oc_fname, occupname, occupfname, tot_extent,occupant_extent,gender,mobileno,dcode,mcode,cr_wsno);

	}
	
	public void insertIntoDynamicTable2(String dynamicTableName2, String crSno, String part_key, int cr_dist_code,
			int cr_mand_code, int cr_vcode, int cr_year, String cr_season, String cr_farmeruid, int kh_no,
			String oc_name, String oc_fname, String occupname, String occupfname, Double tot_extent, Double occupant_extent,  String gender, String reason,long mobileno,Integer dcode,Integer mcode,String userid) {
		String owner_tenant = "o";

		String sql = "INSERT INTO " + dynamicTableName2
				+ " (cr_sno,part_key,cr_dist_code,cr_mand_code,cr_vcode,cr_year,cr_season,cr_farmeruid,kh_no,owner_tenant,oc_name,oc_fname,occupname,occupfname,tot_extent,occupant_extent,gender,reason,mobileno,dcode,mcode,regno,sjointoccupant,entry_by) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,0,?)";

		jdbcTemplate.update(sql, crSno, part_key, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season,
				cr_farmeruid, kh_no, owner_tenant, oc_name, oc_fname, occupname, occupfname, tot_extent,occupant_extent,gender,reason,mobileno,dcode,mcode,userid);

	}
}

