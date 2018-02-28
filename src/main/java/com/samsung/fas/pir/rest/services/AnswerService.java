package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.AnswerDAO;
import com.samsung.fas.pir.persistence.dao.QuestionDAO;
import com.samsung.fas.pir.persistence.models.entity.Answer;
import com.samsung.fas.pir.persistence.models.entity.Question;
import com.samsung.fas.pir.rest.dto.answer.CRUAnswerDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService extends BService<Answer,CRUAnswerDTO, AnswerDAO, Long> {
	private	QuestionDAO	qdao;

	@Autowired
	public AnswerService(AnswerDAO dao, QuestionDAO qdao) {
		super(dao, Answer.class, CRUAnswerDTO.class);
		this.qdao = qdao;
	}

	@Override
	public CRUAnswerDTO save(CRUAnswerDTO create, Account account) {
		Answer		model		= create.getModel();
		Question	question	= Optional.ofNullable(qdao.findOne(model.getQuestion().getId())).orElseThrow(() -> new RESTRuntimeException("question.notfound"));

		model.setQuestion(question);
		question.getAnswers().add(model);
		return new CRUAnswerDTO(dao.save(model));
	}

	@Override
	public CRUAnswerDTO update(CRUAnswerDTO update, Account account) {
		Answer		model		= update.getModel();
		Answer		answer		= Optional.ofNullable(dao.findOne(model.getId())).orElseThrow(() -> new RESTRuntimeException("answer.notfound"));

		answer.setDescription(model.getDescription());
		return new CRUAnswerDTO(dao.save(answer));
	}
}
