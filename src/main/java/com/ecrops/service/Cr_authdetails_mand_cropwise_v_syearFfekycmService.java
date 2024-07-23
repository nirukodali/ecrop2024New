package com.ecrops.service;

import java.util.List;
import com.ecrops.entity.Cr_authdetails_mand_cropwise_v_syearFfekycm;

public interface Cr_authdetails_mand_cropwise_v_syearFfekycmService {
    public List<Cr_authdetails_mand_cropwise_v_syearFfekycm> getCropwise(int dcode,String sescrpyear,int cropid);
}
