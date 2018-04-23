package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Agent;
import com.samsung.fas.pir.persistence.models.QAgent;
import com.samsung.fas.pir.persistence.models.QUser;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.persistence.repositories.IAgent;
import com.samsung.fas.pir.persistence.repositories.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentDAO extends BaseDAO<Agent, Long, IAgent, QAgent> {
	@Autowired
	public AgentDAO(IAgent repository) {
		super(repository);
	}
}
