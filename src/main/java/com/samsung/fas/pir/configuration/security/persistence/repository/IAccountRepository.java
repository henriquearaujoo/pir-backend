package com.samsung.fas.pir.configuration.security.persistence.repository;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends CrudRepository<Account, Long> {
	Account findByUsername(String username);
	Account findByUserEmail(String email);
}
