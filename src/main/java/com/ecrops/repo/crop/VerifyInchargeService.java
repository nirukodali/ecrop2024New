package com.ecrops.repo.crop;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.dto.crop.response.VerifyInchargeUserRegistrationData;

@Service
@Transactional
public class VerifyInchargeService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<VerifyInchargeUserRegistrationData> viewAndGetDataFromUserRegistration(String dcode, String mcode, String role) {
        String qry = "";
       
        if (role.equals("2")) {
        	qry="select userid,tempuid,mobile_phone from user_registration where district=? and blockortehsil=? and type_user='30' and userid like 'VRO_%' and regular_sts='I'  and tempuid is not null";
        } else if (role.equals("5")) {
        	qry="select userid,tempuid,mobile_phone from user_registration where district=? and blockortehsil=? and type_user='25' and userid like 'RBK_%' and regular_sts='I' and  tempuid is not null ";
        }

               
        Query pstmt = entityManager.createNativeQuery(qry);
        pstmt.setParameter(1, dcode);
        pstmt.setParameter(2, mcode);
        List<Object[]> resultList = pstmt.getResultList();
        
        List<VerifyInchargeUserRegistrationData> verifyInchargeUserRegistrationData = new ArrayList<>();

        for (Object obj : resultList) {
            Object[] data = (Object[]) obj; 
            String userid = (String) data[0]; 
            String tempuid = (String) data[1];
            String mobile_phone = (String) data[2];

            VerifyInchargeUserRegistrationData userRegistrationData = new VerifyInchargeUserRegistrationData();
            userRegistrationData.setUserid(userid);
            userRegistrationData.setTempuid(tempuid);
            userRegistrationData.setMobile_phone(mobile_phone);

            verifyInchargeUserRegistrationData.add(userRegistrationData);
        }

        for (VerifyInchargeUserRegistrationData data : verifyInchargeUserRegistrationData) {
            System.out.println("verifyInchargeUserRegistrationData---" + "getUserid-" + data.getUserid() + "getTempuid--" + data.getTempuid() + "getMobile_phone--" + data.getMobile_phone());
        }
        
        return verifyInchargeUserRegistrationData;
    }
    
   
   
    
    public String approveRecords(String userid, String tempuid, String mobile_phone, String sesdcode, String sesmcode,
            String role) {
        String status = "";
        int flag = 0;

        try {
            String[] rbkuserArray = userid.split(",");
            String[] userUidArray = tempuid.split(",");
            String[] phoneNumberArray = mobile_phone.split(",");

            for (int i = 0; i < rbkuserArray.length; i++) {
                String rbkuser = rbkuserArray[i];
                String userUid = userUidArray[i];
                String phoneNumber = phoneNumberArray[i];

                if (userUid.length() > 12) {
                    userUid = userUid.substring(0, 12);
                }

                String qry = "";
                if (role.equals("2")) {
                    qry = "update user_registration set tempuid=null, aadhaar_id=? where district=? and blockortehsil=? and userid=? and tempuid=? and mobile_phone=? and type_user='30' and status='A'";
                } else if (role.equals("5")) {
                    qry = "update user_registration set tempuid=null, aadhaar_id=? where district=? and blockortehsil=? and userid=? and tempuid=? and mobile_phone=? and type_user='25' and status='A'";
                }

                Query query = entityManager.createNativeQuery(qry);

                query.setParameter(1, userUid);
                query.setParameter(2, sesdcode);
                query.setParameter(3, sesmcode);
                query.setParameter(4, rbkuser);
                query.setParameter(5, userUid);
                query.setParameter(6, phoneNumber);

                int result = query.executeUpdate();

                if (result == 0) {
                    flag = 1;
                } else if (flag == 0) {
                    status = "Successfully Approved Records";
                } else {
                    status = "Insertion Failed";
                }
            }

        } catch (Exception e) {
            status = "Improper Data Found";
            e.printStackTrace();
        }

        return status;
    }
}


