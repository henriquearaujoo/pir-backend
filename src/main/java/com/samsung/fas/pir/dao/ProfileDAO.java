package com.samsung.fas.pir.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.IProfileRepository;

@Service
public class ProfileDAO {
	private		IProfileRepository		repository;
	
	@Autowired
	public ProfileDAO(IProfileRepository repository) {
		this.repository = repository;
	}
}
