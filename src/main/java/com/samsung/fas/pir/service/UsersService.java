package com.samsung.fas.pir.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.UsersDAO;
import com.samsung.fas.pir.models.User;

@Service
public class UsersService {
	private UsersDAO udao;
	
	@Autowired
	public UsersService(UsersDAO dao) {
		udao = dao;
	}
	
	public boolean save(User agent) {
		return udao.save(agent);
	}
	
	public List<User> findAll() {
		return udao.findAll();
	}
	
	public User findByID(UUID id) {
		return udao.findByID(id);
	}
}
