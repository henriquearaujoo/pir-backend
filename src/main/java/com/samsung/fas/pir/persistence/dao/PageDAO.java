package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.models.entity.Page;
import com.samsung.fas.pir.persistence.repository.IPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PageDAO {
	private 	IPageRepository 		repository;

	@Autowired
	public PageDAO(IPageRepository repository) {
		this.repository = repository;
	}

	public List<Page> findAll() {
		return repository.findAll();
	}

	public Page findOne(UUID id) {
		return repository.findOneByGuid(id);
	}
}
