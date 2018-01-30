package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.entity.IndividualPerson;
import com.samsung.fas.pir.persistence.repository.IIndividualPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class IndividualPersonDAO {
	private IIndividualPersonRepository repository;

	@Autowired
	public IndividualPersonDAO(IIndividualPersonRepository repository) {
		this.repository = repository;
	}

	public IndividualPerson findOne(long id) {
		return repository.findOne(id);
	}

	public List<IndividualPerson> findAll() {
		return repository.findAll();
	}

	public List<IndividualPerson> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(),true).collect(Collectors.toList());
	}

	public Page<IndividualPerson> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<IndividualPerson> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public IndividualPerson save(IndividualPerson entity) {
		return repository.save(entity);
	}

	public void trim() {
		repository.trim();
	}
}
