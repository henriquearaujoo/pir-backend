package com.samsung.fas.pir.dao;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.IUsersRepository;
import com.samsung.fas.pir.models.User;

@Service
public class UsersDAO {
	private		IUsersRepository		repository;
	
	@Autowired
	public UsersDAO(IUsersRepository repository) {
		this.repository = repository;
	}
	
	public boolean save(User agent) {
		return repository.save(agent) != null;
	}
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findByID(UUID id) {
		// ID is a primary unique key, so we going to have a list with only one element
		return repository.findById(id).get(0);
	}
	
	@Transactional
	public boolean updateUser(User user, UUID id) {
		// ID is a primary unique key, so we going to have a list with only one element
		User toUpdate = repository.findById(id).get(0);
		toUpdate.copyFrom(user);
		System.out.println(toUpdate.getPassword());
		return repository.save(toUpdate) != null;
	}

}
