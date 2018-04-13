package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QState;
import com.samsung.fas.pir.persistence.models.State;
import com.samsung.fas.pir.persistence.repositories.IStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateDAO extends BaseDAO<State, Long, QState> {
	@Autowired
	public StateDAO(IStateRepository repository) {
		super(repository);
	}
}
