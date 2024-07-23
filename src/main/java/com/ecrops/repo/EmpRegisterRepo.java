package com.ecrops.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecrops.entity.EmployeeRegister;

public interface EmpRegisterRepo extends JpaRepository<EmployeeRegister, String> {

	public EmployeeRegister findByaadhaarId(String aadhaar_id);

	public EmployeeRegister findByempCode (Integer emp_code);
}
