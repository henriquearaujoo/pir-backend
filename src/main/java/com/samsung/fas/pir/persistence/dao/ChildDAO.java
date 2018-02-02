package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.models.entity.Child;
import com.samsung.fas.pir.persistence.repository.IChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildDAO {
	@Autowired
	private		IChildRepository		repository;
	
	public boolean queryCreate(Child child) {
		return repository.save(child) != null;
	}
	
	public List<Child> queryAll() {
		return repository.findAll();
	}
}
