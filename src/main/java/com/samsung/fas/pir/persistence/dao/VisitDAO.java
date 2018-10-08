package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QVisit;
import com.samsung.fas.pir.persistence.models.Visit;
import com.samsung.fas.pir.persistence.repositories.IVisit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VisitDAO extends BaseDAO<Visit, Long, IVisit, QVisit> {
	@Autowired
	public VisitDAO(IVisit repository) {
		super(repository);
	}

	public Visit findOneByAgentAndExternalID(UUID uuid, long externalID) {
		return getRepository().findByAgentUuidAndExternalID(uuid, externalID);
	}
}