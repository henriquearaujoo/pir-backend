package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Family;
import com.samsung.fas.pir.persistence.models.QFamily;
import com.samsung.fas.pir.persistence.repositories.IFamily;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.UUID;

@Service
public class FamilyDAO extends BaseDAO<Family, Long, IFamily, QFamily> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	EntityManager		manager;

	@Autowired
	public FamilyDAO(IFamily repository, EntityManager manager) {
		super(repository);
		setManager(manager);
	}

	public String getSequentialCode(String prefix) {
		return prefix.concat(String.format("%06d", getRepository().countAllByCodeStartingWith(prefix) + 1L));
	}

	public Family findOneByAgentAndExternalID(UUID uuid, long externalID) {
		return getRepository().findByAgentUuidAndExternalID(uuid, externalID);
	}

	public Collection<Family> findAllByAgent(UUID uuid) {
		return getRepository().findAllByAgentUuid(uuid);
	}
}
