package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Child;
import com.samsung.fas.pir.persistence.models.QChild;
import com.samsung.fas.pir.persistence.repositories.IChild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChildDAO extends BaseDAO<Child, Long, IChild, QChild> {
	@Autowired
	public ChildDAO(IChild repository) {
		super(repository);
	}

	public Child findOneByAgentAndExternalID(UUID uuid, long externalID) {
		return getRepository().findByAgentUuidAndExternalID(uuid, externalID);
	}

	public String getSequentialCode(String prefix) {
		return prefix.concat(String.format("%06d", getRepository().countAllByCodeStartingWith(prefix) + 1L));
	}
}