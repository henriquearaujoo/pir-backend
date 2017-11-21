package com.samsung.fas.pir.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.entity.Profile;
import com.samsung.fas.pir.repository.IProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfileDAO {
	private		IProfileRepository		repository;

	@Autowired
	public ProfileDAO(IProfileRepository repository) {
		this.repository = repository;
	}
	
	public Profile findOne(UUID id) {
		return repository.findOneByGuid(id);
	}
	
	public Profile findOneByTitle(String title) {
		return repository.findByTitleIgnoreCase(title);
	}
	
	public List<Profile> findAll() {
		return repository.findAll();
	}

	public Page<Profile> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
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

