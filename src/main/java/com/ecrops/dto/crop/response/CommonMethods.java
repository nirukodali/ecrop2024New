package com.ecrops.dto.crop.response;

import java.util.Calendar; 
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class CommonMethods {

    private static final Logger logger = LoggerFactory.getLogger(CommonMethods.class);

    @PersistenceContext
   EntityManager entityManager;
  
    @Transactional
    public  int DigitalAck_sendSMS(int vcode, String tab, String tab2, String clientip, String userid) throws Exception {
        int smscount = 0;

        String qry4 = "SELECT DISTINCT bookingid, kh_no, cr_sno, cr_mix_unmix_ext, cropname, varietyname, occupname, smsmobileno, wbdname, wbmname, wbvname " +
                      "FROM " + tab + " a, cropnames c, cr_variety_master v, wbvillage_mst w " +
                      "WHERE cr_vcode = " + vcode + " AND cr_vcode = wbvcode AND cr_crop = cropid AND variety = varietycode AND sms_sent IS NULL " +
                      "AND ekyc='Y' AND smsmobileno IS NOT NULL AND social_status IS NULL AND cr_crop <> 888 " +
                      "AND LENGTH(cast(smsmobileno as text)) = 10 AND cast(smsmobileno as text) ~ '^[6-9][0-9]+$'";
        System.out.println("qry4-------------->"+qry4);
        logger.debug("Executing query: {}", qry4);
        Query pst4 = entityManager.createNativeQuery(qry4);
        List<Object[]> resultList = pst4.getResultList();
        System.out.println("resultList---------------" + resultList);
        
        if (resultList.size() > 0) {
            for (Object[] ob : resultList) {
                // Extracting data from the result set
                String bookingid = (String) ob[0];
                String kh_no = (String) ob[1];
                String cr_sno = (String) ob[2];
                String cr_mix_unmix_ext = (String) ob[3];
                String cropname = (String) ob[4];
                String varietyname = (String) ob[5];
                String occupname = (String) ob[6];
                String smsmobileno = (String) ob[7];
                String wbdname = (String) ob[8];
                String wbmname = (String) ob[9];
                String wbvname = (String) ob[10];
                String templateId = "1307166859531513090";

                BookingDetailsPojo setData = new BookingDetailsPojo(
                        bookingid, kh_no, cr_sno, cr_mix_unmix_ext, cropname, varietyname, 
                        occupname, smsmobileno, wbdname, wbmname, wbvname, templateId);
                

                String msg = "e-క్రాప్ - రబీ 2023-24, పంట నమోదు వివరములు: e- క్రాప్ నందు బుకింగ్ నంబరు " + bookingid + " తో " +
                        " శ్రీ " + occupname + " గారు, " + wbdname + " జిల్లా " + wbmname +
                        " మండలము " + wbvname + " గ్రామంలో సర్వే నం: " + cr_sno + "/Khata No:" + kh_no + "లో " + 
                        cr_mix_unmix_ext + " ఎకరములలో మీరు సాగు చేసిన పంట " + cropname + " రకము " + varietyname +
                        " నమోదు చేయబడినది. ధన్యవాదములు. ఆంధ్ర ప్రదేశ్ వ్యవసాయ శాఖ-GOVTAP";


                SendSMSService sendSms = new SendSMSService();
                String res = "";
                System.out.println("sendSms-------------->"+sendSms);
              
                    res = sendSms.sendCDACSMShttpsUnicodeDLT(msg, smsmobileno, templateId);
                    if ("SUCCESS".equalsIgnoreCase(res)) {  // Assuming "SUCCESS" indicates a successful SMS send
                        smscount++;
					  }else {
						  System.out.println("jii");
					  }
        
        String qry2 = "INSERT INTO " + tab2 + "(cr_dist_code, cr_mand_code, cr_vcode, bookingid, cr_crop, cr_no, variety, kh_no, cr_sno, cr_sow_dt, part_key, crt_user, clientip, smsmobileno, dt_sms) " +
                       "SELECT cr_dist_code, cr_mand_code, cr_vcode, bookingid, cr_crop, cr_no, variety, kh_no, cr_sno, cr_sow_date, part_key, '" + userid + "', '" + clientip + "', smsmobileno, now() FROM " + tab +
                       " WHERE cr_vcode = " + vcode + " AND sms_sent IS NULL AND ekyc = 'Y' AND smsmobileno IS NOT NULL AND social_status IS NULL AND cr_crop <> 888 " +
                       "AND LENGTH(smsmobileno::text) = 10 AND smsmobileno::text ~ '^[6-9][0-9]+$'";
        entityManager.createNativeQuery(qry2).executeUpdate();
        System.out.println("qry2-------------->"+qry2);
        String qry3 = "UPDATE " + tab + " SET sms_sent = 'Y', dt_sms = now() WHERE cr_vcode = " + vcode + " AND sms_sent IS NULL AND ekyc = 'Y' AND smsmobileno IS NOT NULL " +
                      "AND cr_crop <> 888 AND social_status IS NULL AND LENGTH(smsmobileno::text) = 10 AND smsmobileno::text ~ '^[6-9][0-9]+$'";
        entityManager.createNativeQuery(qry3).executeUpdate();
            }
        
        }
        return smscount;
    }
    
    
    
    
    
    
    
    
    
    
    
    public Calendar c = null;
    public String dt, mn, hh, mm, ss;

    public static String isBlank(String str) {
        str = str == null ? "" : str;
        str = str.replace('<', ' ');
        str = str.replace('>', ' ');
        str = str.replace('\'', ' ');
        str = str.replace('\"', ' ');
        str = str.replace('&', ' ');
        str = str.replace('%', ' ');
        //System.out.println("----"+str);
        return str;

    }

    public static int isBlankInt(String str) {
        int res = 0;
        str = str == null ? "0" : str;
        try {
            res = Integer.parseInt(str);
        } catch (Exception e) {
            if (e.getLocalizedMessage().equalsIgnoreCase("NumberFormat")) {
                res = 0;
            }
        }
        System.out.println("----%%" + str);
        return res;

    }

    public void getCalender() {

        c = Calendar.getInstance();
        // System.out.println("---"+c.getTime());
        int month = c.get(Calendar.MONTH) + 1;
        if (c.get(Calendar.DAY_OF_MONTH) < 10) {
            dt = "0" + c.get(Calendar.DAY_OF_MONTH);
        } else {
            dt = "" + c.get(Calendar.DAY_OF_MONTH);
        }
        if (month < 10) {
            mn = "0" + month;
        } else {
            mn = "" + month;
        }

        if (c.get(Calendar.DAY_OF_MONTH) < 10) {
            dt = "0" + c.get(Calendar.DAY_OF_MONTH);
        } else {
            dt = "" + c.get(Calendar.DAY_OF_MONTH);
        }

        if (c.get(Calendar.HOUR) < 10) {
            hh = "0" + c.get(Calendar.HOUR);
        } else {
            hh = "" + c.get(Calendar.HOUR);
        }

        if (c.get(Calendar.MINUTE) < 10) {
            mm = "0" + c.get(Calendar.MINUTE);
        } else {
            mm = "" + c.get(Calendar.MINUTE);
        }

        if (c.get(Calendar.SECOND) < 10) {
            ss = "0" + c.get(Calendar.SECOND);
        } else {
            ss = "" + c.get(Calendar.SECOND);
        }

    }

    public static String ReverseDateString(String date) {
        String result = "";
        //System.out.println("------------------"+date);
        if ((date != null) && (date.length() == 10)) {
            String st[] = new String[3];
            int i = 0;
            StringTokenizer st1 = new StringTokenizer(date, "-");
            while (st1.hasMoreTokens()) {
                st[i++] = st1.nextToken();
            }

            result = st[2] + "-" + st[1] + "-" + st[0];
        }
        return result;
    }

    public String DateFormatString(String date) {
        String result = ""; //2011-09-12 //12-09-2011
        String f1 = date.substring(8, date.length());   //05-12-2011
        String f2 = date.substring(5, 7);
        String f3 = date.substring(0, 4);
        result = f1 + "-" + f2 + "-" + f3;
        return result;
    }

    public String Renewal(String date) {
        String result = ""; //2011-09-12 //12-09-2011
        String f1 = date.substring(8, date.length());   //05-12-2011
        String f2 = date.substring(5, 7);
        String f3 = date.substring(0, 4);//12
        int year = Integer.parseInt(f3) + 5;

        result = f1 + "-" + f2 + "-" + year;
        return result;
    }

    public String getSystemTimeStamp() {

        getCalender();
        return dt + "-" + mn + "-" + c.get(Calendar.YEAR) + " " + hh + ":" + mm + ":" + ss;

    }

    public String getCurrentMonth() {

        getCalender();
        return (Integer.parseInt(mn) - 1) + "";

    }

    public String getDBSystemTimeStamp() {

        getCalender();
        return c.get(Calendar.YEAR) + "-" + mn + "-" + dt + " " + hh + ":" + mm + ":" + ss;

    }

    public String getSystemCurrentDate(String delimiter) {

        getCalender();
        return dt + delimiter + mn + delimiter + c.get(Calendar.YEAR);

    }

    public String getSystemCurrentRevDate(String delimiter) {

        getCalender();
        return +c.get(Calendar.YEAR) + delimiter + mn + delimiter + dt;//

    }

    public int getCurrentYear() {
        getCalender();
        return c.get(Calendar.YEAR);
    }
    
   
    public String nextRenewal(String date) throws Exception {

        try {
            String result = ""; //2011-09-12 //12-09-2011

            String st[] = new String[3];
            int i = 0;
            StringTokenizer st1 = new StringTokenizer(date, "-");
            while (st1.hasMoreTokens()) {
                st[i++] = st1.nextToken();
            }

            System.out.println(st[2] + "-" + st[1] + "-" + st[0]);

            int year = Integer.parseInt(st[0]) + 5;
            int day = Integer.parseInt(st[2]) - 1;
            int month = Integer.parseInt(st[1]);

            //last day of previous month
            if (day < 1) {

                //first month of the year
                --month;
                if (month < 1) {

                    month = 12;

                    //previous year
                    --year;
                }

                day = Integer.parseInt(getLastDayOfMonth(year + "", month + ""));
            }
            if (month < 10) {
                st[1] = "0" + month;
            } else {
                st[1] = month + "";
            }
            if (day < 10) {
                st[2] = "0" + day;
            } else {
                st[2] = day + "";
            }

            result = st[2] + "-" + st[1] + "-" + year;

            return result;
        } catch (Exception e) {
            throw e;
        }

    }

    public String getLastDayOfMonth(String year, String month) {

        // get a calendar object
        GregorianCalendar calendar = new GregorianCalendar();

        // convert the year and month to integers
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);

        // adjust the month for a zero based index
        monthInt = monthInt - 1;

        // set the date of the calendar to the date provided
        calendar.set(yearInt, monthInt, 1);

        int dayInt = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return Integer.toString(dayInt);
    }

    public String getSystemCurrentDateForDB(String delimiter) {

        getCalender();
        return c.get(Calendar.YEAR) + delimiter + mn + delimiter + dt;

    }

    public String getMonthName(String monthno) throws Exception {
        String month[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        return month[Integer.parseInt(monthno)];

    }

    public static String getYearFromDate(String date) {
        String result = ""; //2011-09-12 //12-09-2011

        String f3 = date.substring(6, 10);
        result = f3;
        return result;
    }

    public static String getHomemenu(String role) { 
        String result = "";
        //  System.out.println("role "+role);
        if (role.equals("5")) {
            result = "MaoRibbon.jsp";
        }
        if (role.equals("10")) {
            result = "CommRibbon.jsp";
        }
        if (role.equals("11")) {
            result = "AdaComagRibbon.jsp";
        }
        if (role.equals("9")) {
            result = "JdaRibbon.jsp";
        }
        if (role.equals("6")) {
            result = "MPEORibbon.jsp";
        }
        if (role.equals("7")) {
            result = "AEORibbon.jsp";
        }
        //if(role.matches("3|6"))
        // result="usermenu.jsp";
        if (role.equals("1")) //result="NicAdminMenu.jsp";
        {
            result = "nicadminribbon.jsp";
        }
        if (role.equals("12")) {
            result = "AgencyMenu.jsp";
        }
        if (role.equals("13")) {
            result = "StateAgencyRibbon.jsp";
        }
        if (role.equals("14")) {
            //result = "OperatorMenu.jsp";
            result="CmssOpeRibbon.jsp";
        }
         if (role.equals("15")) {
            //result = "OperatorMenu.jsp";
            result="DMAgencyRibbon.jsp";
        }
          if (role.equals("17")) {
            //result = "OperatorMenu.jsp";
            result="DDAPRibbon.jsp";
        }
          if (role.equals("48")) {
            //result = "OperatorMenu.jsp";
            result="AgriRibbon.jsp";
        }
          if (role.equals("16")) {
            //result = "OperatorMenu.jsp";
            result="VARibbon.jsp";
        }
        if (role.equals("18")) {
            //result = "OperatorMenu.jsp";
            result="DDHRibbon.jsp";
        }
         if (role.equals("19")) {
            //result = "OperatorMenu.jsp";
            result="JDHRibbon.jsp";
        }
         if (role.equals("25")) {
            //result = "OperatorMenu.jsp";
            result="VSRibbon.jsp";
        }
         if (role.equals("22")) {
            //result = "OperatorMenu.jsp";
            result="HORibbon.jsp";
        }
        if (role.equals("26")) {
            //result = "OperatorMenu.jsp";
            result="BankerRibbon.jsp";
        }
         if (role.equals("27")) {
            //result = "OperatorMenu.jsp";
            result="MFRibbon.jsp";
        }
          if (role.equals("28")) {
            //result = "OperatorMenu.jsp";
            result="CSRibbon.jsp";
        }
          
  if (role.equals("31")) {
            //result = "OperatorMenu.jsp";
            result="ADARibbon.jsp";
        }
        //  System.out.println("result "+result);
       

     if (role.equals("30")) {
            //result = "OperatorMenu.jsp";
            result="VRORibbon.jsp";
        }
     
     if (role.equals("32")) {
            //result = "OperatorMenu.jsp";
            result="RevADRibbon.jsp";
        }
     
     if (role.equals("33")) {
            //result = "OperatorMenu.jsp";
            result="InsRibbon.jsp";
        }
      if (role.equals("2")) {
            //result = "OperatorMenu.jsp";
            result="MRORibbon.jsp";
        }
      if (role.equals("21")) {
            //result = "OperatorMenu.jsp";
            result="ADHRibbon.jsp";
        }
		 if (role.equals("35")) {           
            result="VFRibbon.jsp";
        }
       if (role.equals("36")) {           
            result="FARibbon.jsp";
        }
        if (role.equals("37")) {
            //result = "OperatorMenu.jsp";
            result="FDORibbon.jsp";
        }
         if (role.equals("38")) {
            //result = "OperatorMenu.jsp";
            result="SARibbon.jsp";
        }
          if (role.equals("39")) {
            //result = "OperatorMenu.jsp";
            result="DESRibbon.jsp";
        }
           if (role.equals("40")) {
            //result = "OperatorMenu.jsp";
            result="CPORibbon.jsp";
        }
            if (role.equals("41")) {
            //result = "OperatorMenu.jsp";
            result="ASORibbon.jsp";
        }
            
              if (role.equals("42")) {
            //result = "OperatorMenu.jsp";
            result="MRIRibbon.jsp";
        }
                if (role.equals("43")) {
            //result = "OperatorMenu.jsp";
            result="DTRibbon.jsp";
        }
                  if (role.equals("44")) {
            //result = "OperatorMenu.jsp";
            result="JCRibbon.jsp";
        }
                   if (role.equals("45")) {
            //result = "OperatorMenu.jsp";
            result="DCRibbon.jsp";
        }
                   
                   if (role.equals("46")) {
            //result = "OperatorMenu.jsp";
            result="RDORibbon.jsp";
        }
                     if (role.equals("47")) {
            //result = "OperatorMenu.jsp";
            result="HODRibbon.jsp";
        }
       if (role.equals("49")) {
            //result = "OperatorMenu.jsp";
            result="ITCRibbon.jsp";
        }
         if (role.equals("52")) {
            //result = "OperatorMenu.jsp";
            result="PMURibbon.jsp";
        }
        //  System.out.println("result "+result);
        return result;
    }
    
}


