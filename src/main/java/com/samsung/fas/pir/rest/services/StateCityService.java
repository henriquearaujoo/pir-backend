package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.StateDAO;
import com.samsung.fas.pir.persistence.models.entity.State;
import com.samsung.fas.pir.rest.dto.address.CRUStateDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StateCityService extends BService<State, CRUStateDTO, StateDAO, Long> {
	@Autowired
	public StateCityService(StateDAO dao) {
		super(dao, State.class, CRUStateDTO.class);
	}

	@Override
	public CRUStateDTO save(CRUStateDTO cruStateDTO, UserDetails account) {
		return null;
	}

	@Override
	public CRUStateDTO update(CRUStateDTO cruStateDTO, UserDetails account) {
		return null;
	}
}