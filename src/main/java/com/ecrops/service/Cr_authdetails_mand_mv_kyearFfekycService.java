package com.ecrops.service;

import java.util.List;
import com.ecrops.entity.Cr_authdetails_mand_mv_kyearFfekyc;

public interface Cr_authdetails_mand_mv_kyearFfekycService {
    public List<Cr_authdetails_mand_mv_kyearFfekyc> getCropwise(int dcode,String sescrpyear);
}
