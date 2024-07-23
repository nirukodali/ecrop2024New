package com.ecrops.service;
import java.util.List;
import com.ecrops.entity.Rep_vaa_draft_listModel;

public interface Rep_vaa_draft_listService {

	List<Rep_vaa_draft_listModel> getvaadraftlistdet(String supercheckupd,String tab1,String userid,Integer cropyear1,String season,Integer vcode1);
	
	
}
