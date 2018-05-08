package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Child;
import com.samsung.fas.pir.persistence.models.QChild;
import com.samsung.fas.pir.persistence.repositories.IChild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildDAO extends BaseDAO<Child, Long, IChild, QChild> {
	public Child findOneByMobileId(long id) {
		return getRepository().findByMobileId(id);
	}

	@Autowired
	public ChildDAO(IChild repository) {
		super(repository);
	}
}