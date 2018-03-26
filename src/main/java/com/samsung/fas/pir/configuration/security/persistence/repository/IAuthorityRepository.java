package com.samsung.fas.pir.configuration.security.persistence.repository;

import com.samsung.fas.pir.configuration.security.persistence.models.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorityRepository extends CrudRepository<Authority, Long> {
	Authority findByAuthority(String authority);
	void deleteByAuthority(String authority);
}
