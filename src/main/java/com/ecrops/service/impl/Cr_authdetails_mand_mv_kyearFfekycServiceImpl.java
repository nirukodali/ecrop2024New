package com.ecrops.service.impl;

import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.Cr_authdetails_mand_mv_kyearFfekyc;
import com.ecrops.repo.Cr_authdetails_mand_mv_kyearFfekycRepository;
import com.ecrops.service.Cr_authdetails_mand_mv_kyearFfekycService;

@Service
public class Cr_authdetails_mand_mv_kyearFfekycServiceImpl implements Cr_authdetails_mand_mv_kyearFfekycService {
    @Autowired
    Cr_authdetails_mand_mv_kyearFfekycRepository cr_authdetails_mand_mv_kyearRepository;
    
    @Override
    public List<Cr_authdetails_mand_mv_kyearFfekyc> getCropwise(int dcode,String sescrpyear) {
        return cr_authdetails_mand_mv_kyearRepository.getCropwise(dcode,sescrpyear);
    }
}
