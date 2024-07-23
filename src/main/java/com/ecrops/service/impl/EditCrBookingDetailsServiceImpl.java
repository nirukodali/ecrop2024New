package com.ecrops.service.impl;

import com.ecrops.model.EditCrBookingDtlsEntity; 
import com.ecrops.model.EditCropBookingDetailsModel;
import com.ecrops.repo.EditCrBookingDetailsRepository;
import com.ecrops.repo.UtilityRepository;
import com.ecrops.service.EditCrBookingDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EditCrBookingDetailsServiceImpl implements EditCrBookingDetailsService {

    EditCrBookingDetailsRepository editCrBookingDetailsRepository;

    private final UtilityRepository utilityRepository;

    public EditCrBookingDetailsServiceImpl(EditCrBookingDetailsRepository editCrBookingDetailsRepository, UtilityRepository utilityRepository) {
        this.editCrBookingDetailsRepository = editCrBookingDetailsRepository;
        this.utilityRepository = utilityRepository;
    }

    public List<EditCrBookingDtlsEntity> geEditCrBookingDetails(EditCropBookingDetailsModel ecbd,String partKey) {
        List<EditCrBookingDtlsEntity> ecbdList = editCrBookingDetailsRepository.getEditCrBookingDetails(ecbd,partKey);
        if(5 == ecbd.getCorrectionType()) {
            ecbdList = ecbdList.stream().map(e -> {
                e.setVarietyList(utilityRepository.getVarietDetails(e.getCr_crop()));
                e.setWaterResourcesList(utilityRepository.getWaterResources());
                e.setCropIrrigMethodList(utilityRepository.getCropIrrigationMethodDetails());
                e.setCropSeedList(utilityRepository.getCropSeedDetails());
                return e;
            }).collect(Collectors.toList());
        }
        return ecbdList;
    }

    public void updateCrAadharNo(EditCrBookingDtlsEntity ecbd) {
        editCrBookingDetailsRepository.updateCrAadharNo(ecbd);
    }

    public void updateCcrcCultivatorDetails(EditCrBookingDtlsEntity ecbd) {
        editCrBookingDetailsRepository.updateCcrcCultivatorDetails(ecbd);
    }

    public void updateCropDetails(EditCrBookingDtlsEntity ecbd) {
        editCrBookingDetailsRepository.updateCultivatorCropDetails(ecbd);
    }

    public void updateAadharNoOfPattaOrEnjoyer(EditCrBookingDtlsEntity ecbd) {
        editCrBookingDetailsRepository.updateAadharNoOfPattaOrEnjoyer(ecbd);
    }

    public void updateOthersNameChange(EditCrBookingDtlsEntity ecbd) {
        editCrBookingDetailsRepository.updateOthersNameChange(ecbd);
    }

}
