package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.StateDAO;
import com.samsung.fas.pir.persistence.models.State;
import com.samsung.fas.pir.rest.dto.StateDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StateCityBO extends BaseBO<State, StateDAO, StateDTO, Long> {
	@Autowired
	public StateCityBO(StateDAO dao) {
		super(dao);
	}

	public StateDTO findByUF(String uf) {
		return new StateDTO(getDao().findByUF(uf), true);
	}

	@Override
	public StateDTO save(StateDTO cruStateDTO, UserDetails account) {
		return null;
	}

	@Override
	public StateDTO update(StateDTO cruStateDTO, UserDetails account) {
		return null;
	}

	@Override
	public Collection<StateDTO> save(Collection<StateDTO> create, UserDetails details) {
		return null;
	}

	@Override
	public Collection<StateDTO> update(Collection<StateDTO> update, UserDetails details) {
		return null;
	}
}