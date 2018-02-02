package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.models.entity.State;
import com.samsung.fas.pir.persistence.repository.IStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateDAO {
	private IStateRepository repository;

	@Autowired
	public StateDAO(IStateRepository repository) {
		this.repository = repository;
	}
	
	public boolean save(State state) {
		return repository.save(state) != null;
	}
	
	public List<State> findAll() {
		return repository.findAll();
	}

	public State findOne(long id) {
		return repository.findOne(id);
	}

}
