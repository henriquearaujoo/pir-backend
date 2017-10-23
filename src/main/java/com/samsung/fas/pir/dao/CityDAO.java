package com.samsung.fas.pir.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.ICityRepository;
import com.samsung.fas.pir.models.City;

@Service
public class CityDAO {
	private 	ICityRepository 		repository;
	
	@Autowired
	public CityDAO(ICityRepository repo) {
		repository = repo;
	}
	
	public boolean save(City city) {
		return repository.save(city) != null;
	}
	
	public List<City> findCitiesByStateID(long stateID) {
		return repository.findByStateId(stateID);
	}
	
	public City findCityByID(long cityID) {
		return repository.findOne(cityID);
	}
	
	public void deleteByID(long cityID) {
		repository.delete(cityID);
	}
}
