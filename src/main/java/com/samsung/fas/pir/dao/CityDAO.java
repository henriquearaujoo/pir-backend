package com.samsung.fas.pir.dao;

import com.samsung.fas.pir.models.entity.City;
import com.samsung.fas.pir.repository.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
