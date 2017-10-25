package com.samsung.fas.pir.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.IPageRepository;
import com.samsung.fas.pir.models.Page;

@Service
public class PageDAO {
	@Autowired
	private 	IPageRepository 		repository;
	
	public List<Page> findAll() {
		return repository.findAll();
	}
	
	public Page findOne(UUID id) {
		return repository.findOne(id);
	}
}
