package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QQuestion;
import com.samsung.fas.pir.persistence.models.Question;
import com.samsung.fas.pir.persistence.repositories.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDAO extends BaseDAO<Question, Long, QQuestion> {
	@Autowired
	public QuestionDAO(IQuestionRepository repository) {
		super(repository);
	}
}
