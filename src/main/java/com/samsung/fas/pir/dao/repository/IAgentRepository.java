package com.samsung.fas.pir.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.Agent;
import java.lang.String;
import java.util.List;

@Repository
public interface IAgentRepository extends JpaRepository<Agent, Long> {
	List<Agent> findByName(String name);
}
