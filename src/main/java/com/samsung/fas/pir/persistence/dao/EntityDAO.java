package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.LegalEntity;
import com.samsung.fas.pir.persistence.models.QLegalEntity;
import com.samsung.fas.pir.persistence.repositories.IEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityDAO extends BaseDAO<LegalEntity, Long, QLegalEntity> {
	@Autowired
	public EntityDAO(IEntityRepository repository) {
		super(repository);
	}
}
