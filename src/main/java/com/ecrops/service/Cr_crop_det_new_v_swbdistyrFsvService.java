package com.ecrops.service;

import java.util.List;
import com.ecrops.entity.Cr_crop_det_new_v_swbdistyear;

public interface Cr_crop_det_new_v_swbdistyrFsvService {
    public List<Cr_crop_det_new_v_swbdistyear> getCropwise(String sescrpyear,String wbdcode,String userid);
}
