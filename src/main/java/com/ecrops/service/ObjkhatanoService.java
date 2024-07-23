package com.ecrops.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecrops.entity.Obj_unobjEntity;

@Service
public interface ObjkhatanoService {

	public List<Obj_unobjEntity> getkhatano();
	
	public  List<Obj_unobjEntity> getcategory(Integer code);
	
}
