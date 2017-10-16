package com.samsung.fas.pir.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.IChildRepository;
import com.samsung.fas.pir.models.Child;

@Service
public class ChildDAO {
	private		IChildRepository		repository;
	
	@Autowired
	public ChildDAO(IChildRepository repository) {
		this.repository = repository;
	}
	
	public boolean queryCreate(Child child) {
		return repository.save(child) != null;
	}
	
	public List<Child> queryAll() {
		return repository.findAll();
	}
}
