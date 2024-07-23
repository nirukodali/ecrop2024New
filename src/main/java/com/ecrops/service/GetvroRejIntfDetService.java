package com.ecrops.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.ecrops.entity.VroRejdetModel;

public interface GetvroRejIntfDetService {
	
public List<VroRejdetModel> getVroRejdets(String t1,Integer vill,String season,Integer cropyear1);


}
