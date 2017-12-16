package com.samsung.fas.pir.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.entity.Login;
import com.samsung.fas.pir.repository.ILoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LoginDAO {
	private ILoginRepository repository;

	@Autowired
	public LoginDAO(ILoginRepository repository) {
		this.repository = repository;
	}

	public Login findOne(long id) {
		return repository.findOne(id);
	}

	public List<Login> findAll() {
		return repository.findAll();
	}

	public List<Login> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(),true).collect(Collectors.toList());
	}

	public Page<Login> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Login> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Login save(Login entity) {
		return repository.save(entity);
	}

	public void delete(Long id) {
		repository.delete(id);
	}
}
