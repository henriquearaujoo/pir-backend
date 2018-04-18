package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Conclusion;
import com.samsung.fas.pir.persistence.models.QConclusion;
import com.samsung.fas.pir.persistence.repositories.IConclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConclusionDAO extends BaseDAO<Conclusion, Long, IConclusion, QConclusion> {
	@Autowired
	public ConclusionDAO(IConclusion repository) {
		super(repository);
	}
}
