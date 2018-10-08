package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.AlternativeDAO;
import com.samsung.fas.pir.persistence.dao.AnswerDAO;
import com.samsung.fas.pir.persistence.dao.QuestionDAO;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.rest.dto.AnswerDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AnswerBO extends BaseBO<Answer, AnswerDAO, AnswerDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		AlternativeDAO	alternativeDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		QuestionDAO		questionDAO;

	@Autowired
	protected AnswerBO(AnswerDAO dao, AlternativeDAO alternativeDAO, QuestionDAO questionDAO) {
		super(dao);
		setAlternativeDAO(alternativeDAO);
		setQuestionDAO(questionDAO);
	}

	@Override
	public AnswerDTO save(AnswerDTO create, Device device, UserDetails account) {
		Answer		model		= create.getModel();
		Question 	question	= create.getQuestionUUID() != null? getQuestionDAO().findOne(create.getQuestionUUID()) : null;
		Alternative alternative	= create.getAlternativeUUID() != null? getAlternativeDAO().findOne(create.getAlternativeUUID()) : null;

		model.setAlternative(alternative);
		model.setQuestion(question);

		return new AnswerDTO(getDao().save(model), device,true);
	}

	@Override
	public AnswerDTO update(AnswerDTO update, Device device, UserDetails account) {
		Answer		model		= update.getModel();
		Answer		answer		= getDao().findOne(model.getUuid());
		Question 	question	= update.getQuestionUUID() != null? getQuestionDAO().findOne(update.getQuestionUUID()) : null;
		Alternative alternative	= update.getAlternativeUUID() != null? getAlternativeDAO().findOne(update.getAlternativeUUID()) : null;

		answer.setQuestion(question);
		answer.setAlternative(alternative);
		answer.setDescription(model.getDescription());

		return new AnswerDTO(getDao().save(answer), device,true);
	}

	@Override
	public Collection<AnswerDTO> save(Collection<AnswerDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<AnswerDTO> update(Collection<AnswerDTO> update, Device device, UserDetails details) {
		return null;
	}

	Answer setupAnswer(Answer model, Visit visit, Agent agent) {
		model.setAlternative(model.getAlternative() != null && model.getAlternative().getUuid() != null? getAlternativeDAO().findOne(model.getAlternative().getUuid()) : null);
		model.setQuestion(getQuestionDAO().findOne(model.getQuestion().getUuid()));
		model.setAgent(agent);
		model.setVisit(visit);
		return model;
	}

	Answer setupAnswer(Answer answer, Answer model, Agent agent) {
		answer.setQuestion(getQuestionDAO().findOne(model.getQuestion().getUuid()));
		answer.setAlternative(model.getAlternative().getUuid() != null? getAlternativeDAO().findOne(model.getAlternative().getUuid()) : null);
		answer.setAgent(agent);
		answer.setDescription(model.getDescription());
		return answer;
	}
}