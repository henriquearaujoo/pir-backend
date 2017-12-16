package com.samsung.fas.pir.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.entity.Answer;
import com.samsung.fas.pir.repository.IAnswerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AnswerDAO {
	private IAnswerRepository repository;

	public AnswerDAO(IAnswerRepository repository) {
		this.repository = repository;
	}

	public Answer findOne(long id) {
		return repository.findOne(id);
	}

	public List<Answer> findAll() {
		return repository.findAll();
	}

	public List<Answer> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(),true).collect(Collectors.toList());
	}

	public Page<Answer> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Answer> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Answer save(Answer user) {
		return repository.save(user);
	}

	public void delete(long id) {
		repository.delete(id);
	}
}
