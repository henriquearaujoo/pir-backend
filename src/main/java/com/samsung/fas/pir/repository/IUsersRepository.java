package com.samsung.fas.pir.repository;

import com.samsung.fas.pir.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUsersRepository extends PagingAndSortingRepository<User, UUID> {
	User findOneByGuid(UUID id);
	@Query(value="select * from \"user\" where lower(\"login\") = lower(?1)", nativeQuery=true)
	User findByLogin(String login);
	User findByOrganizationCnpj(String cnpj);
	User findByPersonCpf(String cpf);
	User findByEmail(String email);

	List<User> findAll();
	List<User> findByPersonRg(String rg);
	List<User> findByOrganizationIe(String ie);
	List<User> findByProfileGuid(UUID id);
	List<User> findAllByActive(boolean status);
	Page<User> findAllByActive(Pageable pageable, boolean status);

}
