package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.Cr_crop_det_new_v_swbdistyear;
import com.ecrops.repo.Cr_crop_det_new_v_swbdistyrFsvRepository;
import com.ecrops.service.Cr_crop_det_new_v_swbdistyrFsvService;

@Service
public class Cr_crop_det_new_v_swbdistyrFsvServiceImpl implements Cr_crop_det_new_v_swbdistyrFsvService {
    @Autowired
    Cr_crop_det_new_v_swbdistyrFsvRepository cr_crop_det_new_v_swbdistyrFsvRepository;
    
    @Override
    public List<Cr_crop_det_new_v_swbdistyear> getCropwise(String sescrpyear,String wbdcode,String userid) {
        return cr_crop_det_new_v_swbdistyrFsvRepository.getCropwise(sescrpyear,wbdcode,userid);
    }
}
