package com.samsung.fas.pir.login.persistence.repository;

import com.samsung.fas.pir.login.persistence.models.entity.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorityRepository extends CrudRepository<Authority, Long> {
	Authority findByAuthority(String authority);
	void deleteByAuthority(String authority);
}
