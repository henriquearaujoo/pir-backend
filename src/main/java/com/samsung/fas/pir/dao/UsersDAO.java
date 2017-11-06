package com.samsung.fas.pir.dao;

import com.samsung.fas.pir.dao.repository.IUsersRepository;
import com.samsung.fas.pir.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
	
	public Page<User> findAllByPage(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	public List<User> findByProfileID(UUID id) {
		return repository.findByProfileId(id);
	}
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findOne(UUID id) {
		return repository.findOneByGuid(id);
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

	public User update(User user, UUID id) {
		user.setId(id);
		return repository.save(user);
	}
}
