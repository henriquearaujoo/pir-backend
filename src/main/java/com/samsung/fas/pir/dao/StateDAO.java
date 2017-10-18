package com.samsung.fas.pir.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.IStateRepository;
import com.samsung.fas.pir.models.State;

@Service
public class StateDAO {
	private IStateRepository repository;
	
	@Autowired
	public StateDAO(IStateRepository repo) {
		this.repository = repo;
	}
	
	public boolean save(State state) {
		return repository.save(state) != null;
	}
	
	public List<State> findAll() {
		return repository.findAll();
	}

}
