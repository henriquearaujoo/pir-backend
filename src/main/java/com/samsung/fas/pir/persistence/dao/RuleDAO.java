package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QRule;
import com.samsung.fas.pir.persistence.models.Rule;
import com.samsung.fas.pir.persistence.repositories.IRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleDAO extends BaseDAO<Rule, Long, IRules, QRule> {
	@Autowired
	public RuleDAO(IRules repository) {
		super(repository);
	}
}
