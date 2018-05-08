package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Alternative;
import com.samsung.fas.pir.persistence.models.QAlternative;
import com.samsung.fas.pir.persistence.repositories.IAlternative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlternativeDAO extends BaseDAO<Alternative, Long, IAlternative, QAlternative> {
	@Autowired
	public AlternativeDAO(IAlternative repository) {
		super(repository);
	}
}
