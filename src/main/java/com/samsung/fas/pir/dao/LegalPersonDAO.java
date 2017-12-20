package com.samsung.fas.pir.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.entity.LegalPerson;
import com.samsung.fas.pir.repository.ILegalPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LegalPersonDAO {
	private ILegalPersonRepository repository;

	@Autowired
	public LegalPersonDAO(ILegalPersonRepository repository) {
		this.repository = repository;
	}

	public LegalPerson findOne(long id) {
		return repository.findOne(id);
	}

	public List<LegalPerson> findAll() {
		return repository.findAll();
	}

	public List<LegalPerson> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(),true).collect(Collectors.toList());
	}

	public Page<LegalPerson> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<LegalPerson> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public LegalPerson save(LegalPerson entity) {
		return repository.save(entity);
	}

	public void trim() {
		repository.trim();
	}
}
