package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Mother;
import com.samsung.fas.pir.persistence.models.QMother;
import com.samsung.fas.pir.persistence.repositories.IMotherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotherDAO extends BaseDAO<Mother, Long, IMotherRepository, QMother> {
	@Autowired
	public MotherDAO(IMotherRepository repository) {
		super(repository);
	}
}