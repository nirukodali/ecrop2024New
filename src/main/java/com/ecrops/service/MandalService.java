package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.HoMandal;

public interface MandalService {
	public List<HoMandal> findAll(int dcode);

}
