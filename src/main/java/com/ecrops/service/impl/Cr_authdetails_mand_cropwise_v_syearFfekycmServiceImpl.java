package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.Cr_authdetails_mand_cropwise_v_syearFfekycm;
import com.ecrops.repo.Cr_authdetails_mand_cropwise_v_syearFfekycmRepository;
import com.ecrops.service.Cr_authdetails_mand_cropwise_v_syearFfekycmService;

@Service
public class Cr_authdetails_mand_cropwise_v_syearFfekycmServiceImpl implements Cr_authdetails_mand_cropwise_v_syearFfekycmService {
    @Autowired
    Cr_authdetails_mand_cropwise_v_syearFfekycmRepository cr_authdetails_mand_cropwise_v_syearFfekycmRepository;
    
    @Override
    public List<Cr_authdetails_mand_cropwise_v_syearFfekycm> getCropwise(int dcode,String sescrpyear,int cropid) {
        return cr_authdetails_mand_cropwise_v_syearFfekycmRepository.getCropwise(dcode,sescrpyear,cropid);
    }
}
