package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.FormAnswerTB;
import com.samsung.fas.pir.persistence.models.QFormAnswerTB;
import com.samsung.fas.pir.persistence.repositories.IFormAnswerTB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormAnswerTBDAO extends BaseDAO<FormAnswerTB, Long, IFormAnswerTB, QFormAnswerTB> {
	@Autowired
	public FormAnswerTBDAO(IFormAnswerTB repository) {
		super(repository);
	}
}
