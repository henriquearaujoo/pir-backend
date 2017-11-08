package com.samsung.fas.pir.dao.repository;

import com.samsung.fas.pir.models.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface IRulesRepository extends JpaRepository<Rule, UUID> {
	List<Rule> findByProfileGuid(UUID id);
	Rule findByProfileIdAndPageId(UUID profile, UUID page);
	Rule findOneByGuid(UUID id);
	@Transactional
	void deleteByGuid(UUID id);

}