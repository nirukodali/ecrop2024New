package com.ecrops.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.EmbeddedVillcoords;
import com.ecrops.entity.Villcoords;
import com.ecrops.repo.JioreferenceRepository;
import com.ecrops.repo.UpdateJioRepository;
import com.ecrops.service.JioInsertService;


@Service
public class JiosaveServiceImpl implements JioInsertService {

	@Autowired 
	public JioreferenceRepository jioreferencerepository;
	
	@Autowired
	public UpdateJioRepository updatejiorepository;

	@Override
	public void savel(String dcode, String mcode, String vcode) {
		// TODO Auto-generated method stub
		EmbeddedVillcoords embeddedVillcoords = new EmbeddedVillcoords();
		
		
		embeddedVillcoords.setWbdcode(Integer.parseInt(dcode));
		embeddedVillcoords.setWbmcode(Integer.parseInt(mcode));
		embeddedVillcoords.setWbvcode(Integer.parseInt(vcode));
		
		Villcoords villcoords = new Villcoords();
		villcoords.setEmbeddedvillcoords(embeddedVillcoords);

		jioreferencerepository.save(villcoords);
	}

	@Override
	public int updatejio(int dcode, int mcode, int vcode) {
		// TODO Auto-generated method stub
		return updatejiorepository.updatejio(dcode,mcode,vcode);
	}

}
