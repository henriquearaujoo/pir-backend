package com.samsung.fas.pir.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.entity.User;

import java.util.UUID;
import java.util.List;
import java.lang.String;
import com.samsung.fas.pir.models.entity.Profile;

@Repository
public interface IUsersRepository extends PagingAndSortingRepository<User, UUID> {
	List<User> findAll();
	List<User> findByLogin(String login);
	List<User> findByPersonCpf(String cpf);
	List<User> findByPersonRg(String rg);
	List<User> findByOrganizationCnpj(String cnpj);
	List<User> findByOrganizationIe(String ie);
	List<User> findByProfileId(UUID id);
}
