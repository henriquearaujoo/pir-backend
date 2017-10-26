package com.samsung.fas.pir.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.entity.City;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
	List<City> findByStateId(long id);
}