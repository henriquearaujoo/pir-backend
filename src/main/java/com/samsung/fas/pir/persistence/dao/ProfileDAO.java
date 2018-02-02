package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.persistence.repository.IProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

	public List<Profile> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(), true).collect(Collectors.toList());
	}

	public Page<Profile> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Profile> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}
	
	public Profile save(Profile entity) {
		return repository.save(entity);
	}
	
	public Profile update(Profile toUpdate, long id) {
		//Profile model = repository.findOne(id);
		toUpdate.setId(id);
		//toUpdate.setCreatedAt(model.getCreatedAt());
		return repository.save(toUpdate);
	}
}

