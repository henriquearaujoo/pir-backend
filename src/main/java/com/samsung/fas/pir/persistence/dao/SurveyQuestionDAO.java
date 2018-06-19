package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QSAlternative;
import com.samsung.fas.pir.persistence.models.QSurveyQuestion;
import com.samsung.fas.pir.persistence.models.SAlternative;
import com.samsung.fas.pir.persistence.models.SurveyQuestion;
import com.samsung.fas.pir.persistence.repositories.ISAlternative;
import com.samsung.fas.pir.persistence.repositories.ISurveyQuestion;
import org.springframework.stereotype.Service;

@Service
public class SurveyQuestionDAO extends BaseDAO<SurveyQuestion, Long, ISurveyQuestion, QSurveyQuestion> {
	public SurveyQuestionDAO(ISurveyQuestion repository) {
		super(repository);
	}
}
