package com.samsung.fas.pir.login.persistence.repository;

import com.samsung.fas.pir.login.persistence.models.entity.PasswordRecover;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPasswordRecoverRepository extends CrudRepository<PasswordRecover, Long> {
	PasswordRecover findByEmail(String email);
	PasswordRecover findByToken(String token);

	@Query(nativeQuery = true, value = "DELETE FROM psswd_rcvr WHERE until < current_date - (?1 || ' DAY')\\:\\:INTERVAL")
	void trim(long interval);
	//
}
