package com.ecrops.service;
import java.util.List;
import com.ecrops.entity.Rep_VsPerennialModel;

public interface Rep_VsPerennialService {

	public List<Rep_VsPerennialModel> getVsPerennialdet(String tab1,String dcode,Integer dcode1, 
  Integer wbmcode,String vcode,Integer cropyear,String season,
  Integer crpid,Integer vcode1);
	
}
