package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.StateDAO;
import com.samsung.fas.pir.persistence.models.entity.State;
import com.samsung.fas.pir.rest.dto.StateDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StateCityService extends BService<State, StateDTO, StateDAO, Long> {
	@Autowired
	public StateCityService(StateDAO dao) {
		super(dao, State.class, StateDTO.class);
	}

	@Override
	public StateDTO save(StateDTO cruStateDTO, UserDetails account) {
		return null;
	}

	@Override
	public StateDTO update(StateDTO cruStateDTO, UserDetails account) {
		return null;
	}
}