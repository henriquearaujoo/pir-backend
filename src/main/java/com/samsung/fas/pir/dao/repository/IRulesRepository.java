package com.samsung.fas.pir.dao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.Rule;

@Repository
public interface IRulesRepository extends JpaRepository<Rule, UUID> {
	@Query(value="select * from profile_page_rules where profile_id_fk = ?1 and page_id_fk = ?2", nativeQuery=true)
	Rule findByProfileAndPageIDs(UUID profile, UUID page);
}
