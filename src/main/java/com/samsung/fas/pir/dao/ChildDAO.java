package com.samsung.fas.pir.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.IChildRepository;
import com.samsung.fas.pir.models.entity.Child;

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
