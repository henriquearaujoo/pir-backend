package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.ConservationUnity;
import com.samsung.fas.pir.persistence.models.QConservationUnity;
import com.samsung.fas.pir.persistence.repositories.IConservationUnity;
import org.springframework.stereotype.Service;

@Service
public class ConservationUnityDAO extends BaseDAO<ConservationUnity, Long, IConservationUnity, QConservationUnity> {
	public ConservationUnityDAO(IConservationUnity repository) {
		super(repository);
	}
}