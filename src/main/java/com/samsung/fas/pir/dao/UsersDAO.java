package com.samsung.fas.pir.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.entity.User;
import com.samsung.fas.pir.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersDAO {
	private		IUsersRepository		repository;

	@Autowired
	public UsersDAO(IUsersRepository repository) {
		this.repository = repository;
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public List<User> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(),true).collect(Collectors.toList());
	}

	public Page<User> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<User> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}


	
	public User save(User user) {
		return repository.save(user);
	}
	
	public List<User> findByProfileID(UUID id) {
		return repository.findByProfileGuid(id);
	}
	
	public User findOne(UUID id) {
		return repository.findOneByGuid(id);
	}
	
	public User findOneByLogin(String login) {
		return repository.findByLoginIgnoreCase(login);
	}
	
	public User findOneByCpf(String cpf) {
		return repository.findByPersonCpf(cpf);
	}

	public User findOneByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	public List<User> findByRg(String rg) {
		return repository.findByPersonRg(rg);
	}
	
	public User findOneByCnpj(String cnpj) {
		return repository.findByOrganizationCnpj(cnpj);
	}
	
	public List<User> findByIe(String ie) {
		return repository.findByOrganizationIe(ie);
	}

	public User update(User user, UUID id) {
		user.setId(id);
		return repository.save(user);
	}
}
