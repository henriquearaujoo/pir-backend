package com.samsung.fas.pir.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.entity.Address;
import com.samsung.fas.pir.repository.IAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AddressDAO {
	private IAddressRepository repository;

	@Autowired
	public AddressDAO(IAddressRepository repository) {
		this.repository = repository;
	}

	public Address findOne(long id) {
		return repository.findOne(id);
	}

	public List<Address> findAll() {
		return repository.findAll();
	}

	public List<Address> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(),true).collect(Collectors.toList());
	}

	public Page<Address> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Address> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Address save(Address entity) {
		return repository.save(entity);
	}

	public void delete(Long id) {
		repository.delete(id);
	}
}
