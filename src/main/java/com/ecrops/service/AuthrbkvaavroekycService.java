package com.ecrops.service;

import java.util.List;
import com.ecrops.entity.AuthrbkvaavroekycModel;

public interface AuthrbkvaavroekycService {

		List<AuthrbkvaavroekycModel> getAuthrbkvaavroekycdet(String tab, Integer vscode, Integer cropyear1, String season);

}
