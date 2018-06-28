package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QSAlternative;
import com.samsung.fas.pir.persistence.models.SAlternative;
import com.samsung.fas.pir.persistence.repositories.ISAlternative;
import org.springframework.stereotype.Service;

@Service
public class SAlternativeDAO extends BaseDAO<SAlternative, Long, ISAlternative, QSAlternative> {
	public SAlternativeDAO(ISAlternative repository) {
		super(repository);
	}
}
