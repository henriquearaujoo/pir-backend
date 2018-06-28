package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QState;
import com.samsung.fas.pir.persistence.models.State;
import com.samsung.fas.pir.persistence.repositories.IState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateDAO extends BaseDAO<State, Long, IState, QState> {
	@Autowired
	public StateDAO(IState repository) {
		super(repository);
	}

	public State findByUF(String uf) {
		return getRepository().findByAbbreviation(uf).orElseThrow(() -> new RESTException("not.found"));
	}
}
