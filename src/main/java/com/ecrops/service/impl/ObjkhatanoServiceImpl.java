package com.ecrops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.Obj_unobjEntity;
import com.ecrops.repo.ObjkhatanoRepository;
import com.ecrops.service.ObjkhatanoService;

@Service
public class ObjkhatanoServiceImpl implements ObjkhatanoService{

	@Autowired
	ObjkhatanoRepository objkhataRepository;
	
	@Override
	public List<Obj_unobjEntity> getkhatano() {
		// TODO Auto-generated method stub
		return objkhataRepository.getkhatano();
	}

	@Override
	public List<Obj_unobjEntity> getcategory(Integer code) {
		// TODO Auto-generated method stub
		return objkhataRepository.getcategory(code);
	}

}
