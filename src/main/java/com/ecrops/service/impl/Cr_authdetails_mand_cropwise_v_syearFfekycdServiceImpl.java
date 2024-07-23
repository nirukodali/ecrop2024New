package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.Cr_authdetails_mand_cropwise_v_syearFfekycd;
import com.ecrops.repo.Cr_authdetails_mand_cropwise_v_syearFfekycdRepository;
import com.ecrops.service.Cr_authdetails_mand_cropwise_v_syearFfekycdService;

@Service
public class Cr_authdetails_mand_cropwise_v_syearFfekycdServiceImpl implements Cr_authdetails_mand_cropwise_v_syearFfekycdService {
    @Autowired
    Cr_authdetails_mand_cropwise_v_syearFfekycdRepository cr_authdetails_mand_cropwise_v_syearFfekycdRepository;
    
    @Override
    public List<Cr_authdetails_mand_cropwise_v_syearFfekycd> getCropwise(int dcode,String sescrpyear,int cropid) {
        return cr_authdetails_mand_cropwise_v_syearFfekycdRepository.getCropwise(dcode,sescrpyear,cropid);
    }
}
