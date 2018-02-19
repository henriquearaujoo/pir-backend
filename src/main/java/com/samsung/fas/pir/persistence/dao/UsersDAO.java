package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.entity.IndividualPerson;
import com.samsung.fas.pir.persistence.models.entity.LegalPerson;
import com.samsung.fas.pir.persistence.models.entity.User;
import com.samsung.fas.pir.persistence.repository.IIndividualPersonRepository;
import com.samsung.fas.pir.persistence.repository.ILegalPersonRepository;
import com.samsung.fas.pir.persistence.repository.IUserRepository;
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
	private	IUserRepository 			repository;
	private	IIndividualPersonRepository prepository;
	private	ILegalPersonRepository 		lrepository;

	@Autowired
	public UsersDAO(IUserRepository repository, IIndividualPersonRepository prepository, ILegalPersonRepository lrepository) {
		this.repository 	= repository;
		this.prepository	= prepository;
		this.lrepository	= lrepository;
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
	
	public User findOne(UUID id) {
		return repository.findOneByUuid(id);
	}
	
	public User findOneByLogin(String login) {
		return repository.findByAccountUsernameIgnoreCase(login);
	}
	
	public IndividualPerson findOneByCpf(String cpf) {
		return prepository.findByCpf(cpf);
	}

	public User findOneByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	public List<IndividualPerson> findByRg(String rg) {
		return prepository.findByRg(rg).stream().map(item -> item).collect(Collectors.toList());
	}
	
	public LegalPerson findOneByCnpj(String cnpj) {
		return lrepository.findByCnpj(cnpj);
	}
	
	public List<LegalPerson> findByIe(String ie) {
		return lrepository.findByIe(ie).stream().map(item -> item).collect(Collectors.toList());
	}
}
