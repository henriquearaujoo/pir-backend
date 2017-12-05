package com.samsung.fas.pir.repository;

import com.samsung.fas.pir.models.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
	List<City> findByStateId(long id);
}