package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QRule;
import com.samsung.fas.pir.persistence.models.Rule;
import com.samsung.fas.pir.persistence.repositories.IRulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleDAO extends BaseDAO<Rule, Long, QRule> {
	@Autowired
	public RuleDAO(IRulesRepository repository) {
		super(repository);
	}
}
