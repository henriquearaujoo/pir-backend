package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QSAnswer;
import com.samsung.fas.pir.persistence.models.QSurvey;
import com.samsung.fas.pir.persistence.models.SAnswer;
import com.samsung.fas.pir.persistence.models.Survey;
import com.samsung.fas.pir.persistence.repositories.ISAnswer;
import com.samsung.fas.pir.persistence.repositories.ISurvey;
import org.springframework.stereotype.Service;

@Service
public class SAnswerDAO extends BaseDAO<SAnswer, Long, ISAnswer, QSAnswer> {
	public SAnswerDAO(ISAnswer repository) {
		super(repository);
	}
}
