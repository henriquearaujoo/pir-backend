package com.samsung.fas.pir.login.persistence.repository;

import com.samsung.fas.pir.login.persistence.models.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends CrudRepository<Account, Long> {
	Account findByUsername(String username);
	Account findByUserEmail(String email);
}
