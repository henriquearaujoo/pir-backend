package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.entity.Greetings;
import com.samsung.fas.pir.persistence.repository.IGreetingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GreetingsDAO {
	private IGreetingsRepository repository;

	@Autowired
	public GreetingsDAO(IGreetingsRepository repository) {
		this.repository = repository;
	}

	public Greetings findOne(long id) {
		return repository.findOne(id);
	}

	public Greetings findOne(UUID id) {
		return repository.findByUuid(id);
	}

	public List<Greetings> findAll() {
		return repository.findAll();
	}

	public List<Greetings> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(),true).collect(Collectors.toList());
	}

	public Page<Greetings> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Greetings> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Greetings save(Greetings user) {
		return repository.save(user);
	}

	public void delete(long id) {
		repository.delete(id);
	}
}
