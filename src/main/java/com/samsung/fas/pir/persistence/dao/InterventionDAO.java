package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Intervention;
import com.samsung.fas.pir.persistence.models.QIntervention;
import com.samsung.fas.pir.persistence.repositories.IInterventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterventionDAO extends BaseDAO<Intervention, Long, QIntervention> {
	@Autowired
	public InterventionDAO(IInterventionRepository repository) {
		super(repository);
	}
}
