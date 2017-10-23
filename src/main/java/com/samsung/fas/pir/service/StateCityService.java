package com.samsung.fas.pir.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.CityDAO;
import com.samsung.fas.pir.dao.StateDAO;
import com.samsung.fas.pir.dto.CityDTO;
import com.samsung.fas.pir.dto.StateDTO;

@Service
public class StateCityService {
	private		CityDAO 	cdao;
	private 	StateDAO 	sdao;
	
	@Autowired
	public StateCityService(StateDAO sdao, CityDAO cdao) {
		this.cdao			= cdao;
		this.sdao			= sdao;
	}
	
	public List<StateDTO> findAllStates() {
		return sdao.findAll().stream().map(m -> StateDTO.toDTO(m)).collect(Collectors.toList());
	}
	
	public List<CityDTO> findAllCitiesByState(long id) {
		return cdao.findCitiesByStateID(id).stream().map(m -> CityDTO.toDTO(m)).collect(Collectors.toList());
	}
}
