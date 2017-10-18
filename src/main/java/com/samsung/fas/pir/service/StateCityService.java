package com.samsung.fas.pir.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.CityDAO;
import com.samsung.fas.pir.dao.StateDAO;
import com.samsung.fas.pir.models.City;
import com.samsung.fas.pir.models.State;

@Service
public class StateCityService {
	private		CityDAO 	citydao;
	private 	StateDAO 	statedao;
	
	@Autowired
	public StateCityService(StateDAO sdao, CityDAO cdao) {
		this.citydao			= cdao;
		this.statedao			= sdao;
	}
	
	public List<City> findCitiesByStateID(long stateID) {
		return citydao.findCitiesByStateID(stateID);
	}
	
	public boolean saveCity(City city) {
		return citydao.save(city);
	}
	
	public void deleteCityByID(long id) {
		citydao.deleteByID(id);
	}
	
	public List<State> findAllStates() {
		return statedao.findAll();
	}
	
	public boolean saveState(State state) {
		return statedao.save(state);
	}
}
