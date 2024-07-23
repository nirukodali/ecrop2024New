package com.ecrops.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ecrops.entity.Cr_Booking_PartitionEntity;
import com.ecrops.entity.Patta_mst_data;




    @Service
    public class Cr_booking_PartitionRepo {

    	private final JdbcTemplate jdbcTemplate;

    	@Autowired
    	public Cr_booking_PartitionRepo(JdbcTemplate jdbcTemplate) {
    		this.jdbcTemplate = jdbcTemplate;
    	}

//    	public String findSurveyNo(String crbNwb,String tableName, int wbvcode, String surveyno) {
//    	    String queryString = "SELECT  distinct cr_sno FROM " + tableName + " WHERE cr_vcode = ? AND cr_sno = ?  UNION ( SELECT  distinct cr_sno FROM " + crbNwb + " WHERE cr_vcode = ? AND cr_sno = ? ) ";
//    	    System.out.println("queryString===>>"+queryString);
//    		
//    	   
//    	    String cr_sno = null;
//    	    try {
//    	        cr_sno = jdbcTemplate.queryForObject(queryString, String.class, wbvcode, surveyno , wbvcode, surveyno);
//
//    	        //cr_sno = jdbcTemplate.queryForObject(queryString, String.class, wbvcode, surveyno,surveyno,wbvcode);
//    	        
//    	    } catch (EmptyResultDataAccessException e) {
//    	    e.printStackTrace();
//    	    }
//
//    	    return cr_sno;
//    	}
    	
    	public String findSurveyNo( String tableName, int wbvcode, String surveyno) {
    	    String cr_sno = null;
    	    try {
    	        // Construct parameterized SQL query to prevent SQL injection
    	        String queryString = "SELECT DISTINCT cr_sno FROM " + tableName + " WHERE cr_vcode = ? AND cr_sno = ? ";
    	        System.out.println("queryString===>>" + queryString);

    	        // Execute the query using jdbcTemplate
    	        cr_sno = jdbcTemplate.queryForObject(queryString, String.class, wbvcode, surveyno);
    	    } catch (EmptyResultDataAccessException e) {
    	        // Handle case when no results are found
    	        System.out.println("No survey number found for the given parameters.");
    	    } catch (DataAccessException e) {
    	        // Handle other data access errors
    	        System.out.println("An error occurred while accessing data: " + e.getMessage());
    	        e.printStackTrace();
    	    } catch (Exception e) {
    	        // Catch any other unexpected exceptions
    	        System.out.println("An unexpected error occurred: " + e.getMessage());
    	        e.printStackTrace();
    	    }

    	    return cr_sno;
    	}

    	public String findSurveyNoFromCr_nwb( String tableName, int wbvcode, String surveyno) {
    	    String cr_sno = null;
    	    try {
    	        // Construct parameterized SQL query to prevent SQL injection
    	        String queryString = "SELECT DISTINCT cr_sno FROM " + tableName + " WHERE cr_vcode = ? AND cr_sno = ? ";
    	        System.out.println("queryString===>>" + queryString);

    	        // Execute the query using jdbcTemplate
    	        cr_sno = jdbcTemplate.queryForObject(queryString, String.class, wbvcode, surveyno);
    	    } catch (EmptyResultDataAccessException e) {
    	        // Handle case when no results are found
    	        System.out.println("No survey number found for the given parameters.");
    	    } catch (DataAccessException e) {
    	        // Handle other data access errors
    	        System.out.println("An error occurred while accessing data: " + e.getMessage());
    	        e.printStackTrace();
    	    } catch (Exception e) {
    	        // Catch any other unexpected exceptions
    	        System.out.println("An unexpected error occurred: " + e.getMessage());
    	        e.printStackTrace();
    	    }

    	    return cr_sno;
    	}

    	
    	
    	public List<Patta_mst_data> findData(String tableName, int wbvcode, String surveyno) {

    		 String queryString ="SELECT  farmername,fathername,occup_extent,cr_sno FROM  "+tableName+" WHERE cr_vcode = ? AND cr_sno = ?";
    		 List<Patta_mst_data> cr_sno = null;
     	    try {
//     	        cr_sno = jdbcTemplate.queryForObject(queryString, String.class, wbvcode, surveyno);
     	    	  cr_sno = jdbcTemplate.query(queryString, (rs, rowNum) -> {
     	    		 Patta_mst_data entity = new Patta_mst_data();
     	                entity.setFarmername(rs.getString("farmername"));
     	                entity.setFathername(rs.getString("fathername"));
     	                entity.setOccup_extent(rs.getBigDecimal("occup_extent"));
     	                entity.setCr_sno(rs.getString("cr_sno"));
     	                return entity;
     	            }, wbvcode, surveyno);
     	        
     	    } catch (EmptyResultDataAccessException e) {
     	    e.printStackTrace();
     	    }

     	    return cr_sno;
    		 
    	}
    	
    	public List<Patta_mst_data> findDataCr_nwb(String tableName, int wbvcode, String surveyno) {

   		 String queryString ="SELECT  oc_name,oc_fname,occupant_extent,cr_sno FROM  "+tableName+" WHERE cr_vcode = ? AND cr_sno = ?";
   		 List<Patta_mst_data> cr_sno = null;
    	    try {
//    	        cr_sno = jdbcTemplate.queryForObject(queryString, String.class, wbvcode, surveyno);
    	    	  cr_sno = jdbcTemplate.query(queryString, (rs, rowNum) -> {
    	    		 Patta_mst_data entity = new Patta_mst_data();
    	                entity.setOc_name(rs.getString("oc_name"));
    	                entity.setOc_fname(rs.getString("oc_fname"));
    	                entity.setOccupant_extent(rs.getBigDecimal("occupant_extent"));
    	                entity.setCr_sno(rs.getString("cr_sno"));
    	                return entity;
    	            }, wbvcode, surveyno);
    	        
    	    } catch (EmptyResultDataAccessException e) {
    	    e.printStackTrace();
    	    }

    	    return cr_sno;
   		 
   	}
    	
    	
    	    }