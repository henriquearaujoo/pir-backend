package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Child;
import com.samsung.fas.pir.persistence.models.QChild;
import com.samsung.fas.pir.persistence.repositories.IChild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class ChildDAO extends BaseDAO<Child, Long, IChild, QChild> {
//	public Child findOne(long mobile, long agent) {
//		return getRepository().findByMobileIdAndAgentId(mobile, agent);
//	}

	public Collection<Child> findAllIn(Collection<UUID> collection) {
		return getRepository().findAllByUuidIn(collection);
	}

	@Autowired
	public ChildDAO(IChild repository) {
		super(repository);
	}
}