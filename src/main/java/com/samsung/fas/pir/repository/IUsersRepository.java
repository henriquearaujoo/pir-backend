package com.samsung.fas.pir.repository;

import com.samsung.fas.pir.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUsersRepository extends PagingAndSortingRepository<User, UUID> {
	User findOneByGuid(UUID id);
	List<User> findAll();
	List<User> findByLogin(String login);
	List<User> findByPersonCpf(String cpf);
	List<User> findByPersonRg(String rg);
	List<User> findByOrganizationCnpj(String cnpj);
	List<User> findByOrganizationIe(String ie);
	List<User> findByProfileGuid(UUID id);
	List<User> findAllByActive(boolean status);
	Page<User> findAllByActive(Pageable pageable, boolean status);

}
