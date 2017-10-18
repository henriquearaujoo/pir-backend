package com.samsung.fas.pir.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.City;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
	@Query(value = "select * from city where state_id_fk = :stateID", nativeQuery=true)
	List<City> findByStateID(long stateID);
}
