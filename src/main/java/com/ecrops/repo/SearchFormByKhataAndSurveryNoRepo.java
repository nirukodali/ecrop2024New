package com.ecrops.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ecrops.entity.SearchFormByKhataAndSurveryNo;

@Repository
public class SearchFormByKhataAndSurveryNoRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SearchFormByKhataAndSurveryNo> getCropwise(int dcode,
    		                                               String mcode,
    													   String searchbyname,
    													   String wbdcode, 
    													   int cr_mand_code, 
    													   int cr_vcode, 
    													   String cr_year,
    													   String villcode,String khno,String survey) throws SQLException {
    	System.out.println("dcode---------" + dcode);
        System.out.println("mcode---------" + mcode);
        System.out.println("searchbyname---------" + searchbyname);
        System.out.println("wbdcode---------" + wbdcode);
        System.out.println("cr_mand_code---------" + cr_mand_code);
        System.out.println("cr_vcode---------" + cr_vcode);
        System.out.println("cr_year---------" + cr_year);
        System.out.println("villcode---------" + villcode);
        System.out.println("khno---------" + khno);
        System.out.println("survey---------" + survey);

        List<SearchFormByKhataAndSurveryNo> list = new ArrayList<>();
        
        Connection conn = null;
        
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Register PostgreSQL JDBC driver (if not done already)
            Class.forName("org.postgresql.Driver");

            // Database URL, username, and password
            String url = "jdbc:postgresql://10.72.4.71:5432/ecrop";
            String user = "postgres";
            String password = "postgres";

            // Establish connection
            conn = DriverManager.getConnection(url, user, password);

            // Split cr_year into season and year
            String season = cr_year.split("@")[0];
            int  year =Integer.parseInt(cr_year.split("@")[1]);
            
            String tseason = season.toLowerCase();
            if (wbdcode.length() == 1) {
                wbdcode = "0" + wbdcode;
            }
        

            // Construct table name based on parameters
            String tableName = "cr_crop_det_new_v_" + tseason + wbdcode + year;
            if(year>=2023 && !(year==2023 && tseason.equalsIgnoreCase("S")))
            	tableName= "ecrop"+year+"."+tableName;
            System.out.println("table name------>"+tableName);
            // Prepare SQL query
            String qry = "select bookingid, dcode, mcode, cr_dist_code, cr_mand_code, cr_vcode, "
                    + "cr_year, cr_season, concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid, "
                    + "owner_tenant, oc_name, oc_fname, mobileno, age, email, kh_no, cr_sno, tot_extent, "
                    + "cultivable_land, uncultivable_land, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, "
                    + "cr_no, cr_w_draw, cr_irr_type, wtr_tax, longitude, latitude, cropname, data_src, "
                    + "varietyname from " + tableName
                    + " where dcode=? and cr_mand_code=? and cr_vcode=? and cr_year=? and cr_season=?";
            
            if (Integer.parseInt(searchbyname)==1) {
            	System.out.println("----survey--------"+survey);
                qry += "  and cr_sno=?  order by kh_no,cr_sno,oc_name ";
                
                pst = conn.prepareStatement(qry);
                pst.setInt(1,dcode);
                pst.setInt(2, (cr_mand_code));
                pst.setInt(3, Integer.parseInt(villcode));
                pst.setInt(4, year);
                pst.setString(5, season);
                pst.setString(6, survey);

            }
           else if (Integer.parseInt(searchbyname)==2)  {
        	   System.out.println("-------khno----"+khno);
                qry += " and kh_no=?  order by kh_no,cr_sno,oc_name";
                pst = conn.prepareStatement(qry);
                pst.setInt(1, dcode);
                pst.setInt(2, (cr_mand_code));
                pst.setInt(3, Integer.parseInt(villcode));
                pst.setInt(4, year);
                pst.setString(5, season);
                pst.setLong(6,Integer.parseInt(khno));
            }


            
          
System.out.println("qry---------------->"+qry);
            // Execute query
            rs = pst.executeQuery();

            // Process ResultSet
            while (rs.next()) {
                // Retrieve data from the result set
//                int bookingid = rs.getInt("bookingid");
//                String cr_farmeruid = rs.getString("cr_farmeruid");
//                String dcode1 = rs.getString("dcode");
//                String mcode1 = rs.getString("mcode");
//                String cr_dist_code = rs.getString("cr_dist_code");
//                String cr_mand_code1 = rs.getString("cr_mand_code"); // Assuming cr_mand_code is an int in entity
//                int cr_vcode1 = rs.getInt("cr_vcode"); // Assuming cr_vcode is an int in entity
//                String cr_year1 = rs.getString("cr_year");
//                String cr_season = rs.getString("cr_season");
                String owner_tenant = rs.getString("owner_tenant");
                String oc_name = rs.getString("oc_name");
                String oc_fname = rs.getString("oc_fname");
                String mobileno = rs.getString("mobileno");
//                int age = rs.getInt("age");
//                String email = rs.getString("email");
                String kh_no = rs.getString("kh_no");
                String cr_sno = rs.getString("cr_sno");
//                double tot_extent = rs.getDouble("tot_extent");
//                double cultivable_land = rs.getDouble("cultivable_land");
//                double uncultivable_land = rs.getDouble("uncultivable_land");
//                String cr_month = rs.getString("cr_month");
//                String cr_sow_type = rs.getString("cr_sow_type");
//                String cr_crop = rs.getString("cr_crop");
//                String cr_mix_unmix_ext = rs.getString("cr_mix_unmix_ext");
//                String cr_no = rs.getString("cr_no");
//                String cr_w_draw = rs.getString("cr_w_draw");
//                String cr_irr_type = rs.getString("cr_irr_type");
//                double wtr_tax = rs.getDouble("wtr_tax");
//                double longitude = rs.getDouble("longitude");
//                double latitude = rs.getDouble("latitude");
                String cropname = rs.getString("cropname");
//                String data_src = rs.getString("data_src");
//                String varietyname = rs.getString("varietyname");

                // Create new SearchFormByKhataAndSurveryNo instance
                SearchFormByKhataAndSurveryNo searchForm = new SearchFormByKhataAndSurveryNo();
//                searchForm.setBookingid(bookingid);
//                searchForm.setDcode(Integer.parseInt(dcode1));
//                searchForm.setMcode(Integer.parseInt(mcode1));
//                searchForm.setCr_dist_code(BigDecimal.valueOf(Double.parseDouble(cr_dist_code)));
//                searchForm.setCr_mand_code(BigDecimal.valueOf(Double.parseDouble(cr_mand_code1)));
//                searchForm.setCr_vcode(cr_vcode1);
//                searchForm.setCr_year(BigDecimal.valueOf(Double.parseDouble(cr_year1)));
//                searchForm.setCr_season(cr_season.charAt(0));
//                searchForm.setCr_farmeruid(cr_farmeruid);
                searchForm.setOwner_tenant(owner_tenant.charAt(0));
                searchForm.setOc_name(oc_name);
                searchForm.setOc_fname(oc_fname);
                searchForm.setMobileno(BigDecimal.valueOf(Double.parseDouble(mobileno)));
//                searchForm.setAge(age);
//                searchForm.setEmail(email);
                searchForm.setKh_no(BigDecimal.valueOf(Double.parseDouble(kh_no)));
                searchForm.setCr_sno(cr_sno);
//                searchForm.setTot_extent(BigDecimal.valueOf(tot_extent));
//                searchForm.setCultivable_land(BigDecimal.valueOf(cultivable_land));
//                searchForm.setUncultivable_land(BigDecimal.valueOf(uncultivable_land));
//                searchForm.setCr_month(Integer.parseInt(cr_month));
//                searchForm.setCr_sow_type(Integer.parseInt(cr_sow_type));
//                searchForm.setCr_crop(Integer.parseInt(cr_crop));
//                searchForm.setCr_mix_unmix_ext(BigDecimal.valueOf(Double.parseDouble(cr_mix_unmix_ext)));
//                searchForm.setCr_no(cr_no);
//                searchForm.setCr_w_draw(BigDecimal.valueOf(Double.parseDouble(cr_w_draw)));
//                searchForm.setCr_irr_type(Integer.parseInt(cr_irr_type));
//                searchForm.setWtr_tax(BigDecimal.valueOf(wtr_tax));
//                searchForm.setLongitude(BigDecimal.valueOf(longitude));
//                searchForm.setLatitude(BigDecimal.valueOf(latitude));
                searchForm.setCropname(cropname);
//                searchForm.setData_src(data_src.charAt(0));
//                searchForm.setVarietyname(varietyname);

                // Add searchForm to list
                list.add(searchForm);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exception
        } finally {
            // Close resources in finally block
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                	pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }
}
