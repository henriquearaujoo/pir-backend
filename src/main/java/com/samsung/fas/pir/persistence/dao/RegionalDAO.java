package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QRegional;
import com.samsung.fas.pir.persistence.models.Regional;
import com.samsung.fas.pir.persistence.repositories.IRegional;
import org.springframework.stereotype.Service;

@Service
public class RegionalDAO extends BaseDAO<Regional, Long, IRegional, QRegional> {
	public RegionalDAO(IRegional repository) {
		super(repository);
	}
}