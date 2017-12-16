package com.samsung.fas.pir.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.entity.Question;
import com.samsung.fas.pir.repository.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuestionDAO {
	private IQuestionRepository repository;

	@Autowired
	public QuestionDAO(IQuestionRepository repository) {
		this.repository = repository;
	}

	public Question findOne(long id) {
		return repository.findOne(id);
	}

	public List<Question> findAll() {
		return repository.findAll();
	}

	public List<Question> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(),true).collect(Collectors.toList());
	}

	public Page<Question> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Question> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Question save(Question user) {
		return repository.save(user);
	}

	public void delete(long id) {
		repository.delete(id);
	}
}
