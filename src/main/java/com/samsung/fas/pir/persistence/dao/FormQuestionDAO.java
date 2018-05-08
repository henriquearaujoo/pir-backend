package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.FormQuestion;
import com.samsung.fas.pir.persistence.models.QFormQuestion;
import com.samsung.fas.pir.persistence.repositories.IFormQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormQuestionDAO extends BaseDAO<FormQuestion, Long, IFormQuestion, QFormQuestion> {
	@Autowired
	public FormQuestionDAO(IFormQuestion repository) {
		super(repository);
	}
}
