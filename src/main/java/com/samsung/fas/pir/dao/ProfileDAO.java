package com.samsung.fas.pir.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.IProfileRepository;
import com.samsung.fas.pir.models.entity.Profile;

@Service
public class ProfileDAO {
	@Autowired
	private		IProfileRepository		repository;
	
	public Profile findOne(UUID id) {
		return repository.findOne(id);
	}
	
	public Profile findOneByTitle(String title) {
		return repository.findOneByTitle(title);
	}
	
	public List<Profile> findAllActive() {
		return repository.findByActiveTrue();
	}
	
	public List<Profile> findAll() {
		return repository.findAll();
	}
	
	public Profile save(Profile entity) {
		return repository.save(entity);
	}
	
	public Profile update(Profile entity, UUID id) {
		entity.setId(id);
		return repository.save(entity);
	}
}

