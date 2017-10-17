package com.samsung.fas.pir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.ProfileDAO;

@Service
public class ProfileService {
	private ProfileDAO udao;
	
	@Autowired
	public ProfileService(ProfileDAO dao) {
		udao = dao;
	}

}
