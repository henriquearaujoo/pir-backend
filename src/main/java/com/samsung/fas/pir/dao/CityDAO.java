package com.samsung.fas.pir.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.ICityRepository;
import com.samsung.fas.pir.models.City;

@Service
public class CityDAO {
	@Autowired
	private 	ICityRepository 		repository;
	
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
