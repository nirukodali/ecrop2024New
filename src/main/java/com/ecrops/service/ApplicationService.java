package com.ecrops.service;

import java.util.List;

import com.ecrops.projection.VroRejectDropdownProjection;

public interface ApplicationService {
	
	public List<VroRejectDropdownProjection> getActiveSeason();

}
