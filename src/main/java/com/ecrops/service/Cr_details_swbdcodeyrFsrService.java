package com.ecrops.service;

import java.util.List;
import com.ecrops.entity.Cr_details_syearFsr;

public interface Cr_details_swbdcodeyrFsrService {
    public List<Cr_details_syearFsr> getCropwise(String sescrpyear,String wbdcode,String userid);
}
