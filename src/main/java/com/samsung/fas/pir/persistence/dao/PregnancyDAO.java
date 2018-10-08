package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Pregnancy;
import com.samsung.fas.pir.persistence.models.QPregnancy;
import com.samsung.fas.pir.persistence.repositories.IPregnancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PregnancyDAO extends BaseDAO<Pregnancy, Long, IPregnancy, QPregnancy> {
	@Autowired
	public PregnancyDAO(IPregnancy repository) {
		super(repository);
	}

	public Pregnancy findOneByAgentAndExternalID(UUID uuid, long externalID) {
		return getRepository().findByAgentUuidAndExternalID(uuid, externalID);
	}
}
