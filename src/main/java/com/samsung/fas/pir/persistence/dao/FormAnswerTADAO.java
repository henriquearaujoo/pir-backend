package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.FormAnswerTA;
import com.samsung.fas.pir.persistence.models.QFormAnswerTA;
import com.samsung.fas.pir.persistence.repositories.IFormAnswerTA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormAnswerTADAO extends BaseDAO<FormAnswerTA, Long, IFormAnswerTA, QFormAnswerTA> {
	@Autowired
	public FormAnswerTADAO(IFormAnswerTA repository) {
		super(repository);
	}
}
