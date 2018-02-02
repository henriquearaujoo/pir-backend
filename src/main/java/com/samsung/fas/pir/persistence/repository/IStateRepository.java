package com.samsung.fas.pir.persistence.repository;

import com.samsung.fas.pir.persistence.models.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStateRepository extends JpaRepository<State, Long>{

}
