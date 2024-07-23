package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.Cr_details_syearFsr;
import com.ecrops.repo.Cr_details_swbdcodeyrFsrRepository;
import com.ecrops.service.Cr_details_swbdcodeyrFsrService;

@Service
public class Cr_details_swbdcodeyrFsrServiceImpl implements Cr_details_swbdcodeyrFsrService {
    @Autowired
    Cr_details_swbdcodeyrFsrRepository cr_details_swbdcodeyrRepository;
    
    @Override
    public List<Cr_details_syearFsr> getCropwise(String sescrpyear,String wbdcode,String userid) {
        return cr_details_swbdcodeyrRepository.getCropwise(sescrpyear,wbdcode,userid);
    }
}
