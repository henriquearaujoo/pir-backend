package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.persistence.repository.IConclusionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ConclusionDAO {
	private IConclusionRepository repository;

	@Autowired
	public ConclusionDAO(IConclusionRepository repository) {
		this.repository = repository;
	}

	public Conclusion findOne(long id) {
		return repository.findOne(id);
	}

	public List<Conclusion> findAll() {
		return repository.findAll();
	}

	public List<Conclusion> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(),true).collect(Collectors.toList());
	}

	public Page<Conclusion> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Conclusion> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Conclusion save(Conclusion user) {
		return repository.save(user);
	}

	public void delete(long id) {
		repository.delete(id);
	}
}
