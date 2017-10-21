package com.samsung.fas.pir.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.User;

import java.util.UUID;
import java.util.List;
import java.lang.String;

@Repository
public interface IUsersRepository extends JpaRepository<User, UUID> {
	List<User> findById(UUID id);
	List<User> findByLogin(String login);
	List<User> findByPersonCpf(String cpf);
	List<User> findByPersonRg(String rg);
	List<User> findByOrganizationCnpj(String cnpj);
	List<User> findByOrganizationIe(String ie);
}
