package com.samsung.fas.pir.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.IStateRepository;
import com.samsung.fas.pir.models.entity.State;

@Service
public class StateDAO {
	@Autowired
	private IStateRepository repository;
	
	public boolean save(State state) {
		return repository.save(state) != null;
	}
	
	public List<State> findAll() {
		return repository.findAll();
	}

}
