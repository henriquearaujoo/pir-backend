package com.samsung.fas.pir.dao;

import com.samsung.fas.pir.models.entity.Profile;
import com.samsung.fas.pir.repository.IProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfileDAO {
	@Autowired
	private		IProfileRepository		repository;
	
	public Profile findOne(UUID id) {
		return repository.findOneByGuid(id);
	}
	
	public Profile findOneByTitle(String title) {
		return repository.findOneByTitleIgnoreCase(title);
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
	
	public Profile update(Profile toUpdate, UUID id) {
		//Profile model = repository.findOne(id);
		toUpdate.setId(id);
		//toUpdate.setCreatedAt(model.getCreatedAt());
		return repository.save(toUpdate);
	}
}

