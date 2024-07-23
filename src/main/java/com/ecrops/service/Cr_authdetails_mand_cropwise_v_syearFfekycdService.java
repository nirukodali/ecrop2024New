package com.ecrops.service;

import java.util.List;
import com.ecrops.entity.Cr_authdetails_mand_cropwise_v_syearFfekycd;

public interface Cr_authdetails_mand_cropwise_v_syearFfekycdService {
    public List<Cr_authdetails_mand_cropwise_v_syearFfekycd> getCropwise(int dcode,String sescrpyear,int cropid);
}
