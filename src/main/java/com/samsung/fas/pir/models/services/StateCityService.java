package com.samsung.fas.pir.models.services;

import com.samsung.fas.pir.dao.CityDAO;
import com.samsung.fas.pir.dao.StateDAO;
import com.samsung.fas.pir.models.dto.address.CityDTO;
import com.samsung.fas.pir.models.dto.address.StateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
		return sdao.findAll().stream().map(StateDTO::toDTO).collect(Collectors.toList());
	}

	public StateDTO findOneState(long id) {
		return StateDTO.toDTO(sdao.findOne(id));
	}

	public CityDTO findOneCity(long id) {
		return CityDTO.toDTO(cdao.findCityByID(id));
	}
	
	public List<CityDTO> findAllCitiesByState(long id) {
		return cdao.findCitiesByStateID(id).stream().map(CityDTO::toDTO).collect(Collectors.toList());
	}
}
