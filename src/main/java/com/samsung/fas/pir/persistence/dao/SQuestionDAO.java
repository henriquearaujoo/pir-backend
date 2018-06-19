package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QSAnswer;
import com.samsung.fas.pir.persistence.models.QSQuestion;
import com.samsung.fas.pir.persistence.models.SAnswer;
import com.samsung.fas.pir.persistence.models.SQuestion;
import com.samsung.fas.pir.persistence.repositories.ISAnswer;
import com.samsung.fas.pir.persistence.repositories.ISQuestion;
import org.springframework.stereotype.Service;

@Service
public class SQuestionDAO extends BaseDAO<SQuestion, Long, ISQuestion, QSQuestion> {
	public SQuestionDAO(ISQuestion repository) {
		super(repository);
	}
}
