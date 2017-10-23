package com.samsung.fas.pir.dao;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.IUsersRepository;
import com.samsung.fas.pir.models.User;

@Service
public class UsersDAO {
	private		IUsersRepository		repository;
	
	@Autowired
	public UsersDAO(IUsersRepository repository) {
		this.repository = repository;
	}
	
	public User save(User user) {
		return repository.save(user);
	}
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findByID(UUID id) {
		return repository.findOne(id);
	}
	
	public User findOneByLogin(String login) {
		List<User> users = repository.findByLogin(login);
		// ID is a primary unique key, so we going to have a list with only one element
		return users.isEmpty()? null : users.get(0);
	}
	
	public User findOneByCpf(String cpf) {
		List<User> users = repository.findByPersonCpf(cpf);
		// ID is a primary unique key, so we going to have a list with only one element
		return users.isEmpty()? null : users.get(0);
	}
	
	public List<User> findByRg(String rg) {
		return repository.findByPersonRg(rg);
	}
	
	public User findOneByCnpj(String cnpj) {
		List<User> users = repository.findByOrganizationCnpj(cnpj);
		// ID is a primary unique key, so we going to have a list with only one element
		return users.isEmpty()? null : users.get(0);
	}
	
	public List<User> findByIe(String ie) {
		return repository.findByOrganizationIe(ie);
	}

	@Transactional
	public User updateUser(User user, UUID id) {
		User toUpdate = repository.findOne(id);
		// ID is a primary unique key, so we going to have a list with only one element
		if (toUpdate != null) {
			toUpdate.copyFrom(user);
			return repository.save(toUpdate);
		}
		return null;
	}
}
