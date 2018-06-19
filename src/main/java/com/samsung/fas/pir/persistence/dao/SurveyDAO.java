package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QSurvey;
import com.samsung.fas.pir.persistence.models.Survey;
import com.samsung.fas.pir.persistence.repositories.ISurvey;
import org.springframework.stereotype.Service;

@Service
public class SurveyDAO extends BaseDAO<Survey, Long, ISurvey, QSurvey> {
	public SurveyDAO(ISurvey repository) {
		super(repository);
	}
}
