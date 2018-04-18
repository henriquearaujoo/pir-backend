package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.*;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.rest.dto.AnswerDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AnswerBO extends BaseBO<Answer, AnswerDAO, AnswerDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__(@Autowired))
	private		ChildDAO		childDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__(@Autowired))
	private 	MotherDAO		motherDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__(@Autowired))
	private		AlternativeDAO	alternativeDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__(@Autowired))
	private		QuestionDAO		questionDAO;

	@Autowired
	protected AnswerBO(AnswerDAO dao) {
		super(dao);
	}

	@Override
	public AnswerDTO save(AnswerDTO create, UserDetails account) {
		Answer		model		= create.getModel();
		Mother		mother		= create.getMotherUUID() != null? getMotherDAO().findOne(create.getMotherUUID()) : null;
		Child 		child		= create.getChildUUID() != null? getChildDAO().findOne(create.getChildUUID()) : null;
		Question 	question	= create.getQuestionUUID() != null? getQuestionDAO().findOne(create.getQuestionUUID()) : null;
		Alternative alternative	= create.getAlternativeUUID() != null? getAlternativeDAO().findOne(create.getAlternativeUUID()) : null;

		model.setAlternative(alternative);
		model.setChild(child);
		model.setQuestion(question);
		model.setMother(mother);

		return new AnswerDTO(getDao().save(model), true);
	}

	@Override
	public AnswerDTO update(AnswerDTO update, UserDetails account) {
		Answer		model		= update.getModel();
		Answer		answer		= getDao().findOne(model.getUuid());
		Question 	question	= update.getQuestionUUID() != null? getQuestionDAO().findOne(update.getQuestionUUID()) : null;
		Alternative alternative	= update.getAlternativeUUID() != null? getAlternativeDAO().findOne(update.getAlternativeUUID()) : null;

		answer.setQuestion(question);
		answer.setAlternative(alternative);
		answer.setDescription(model.getDescription());

		return new AnswerDTO(getDao().save(answer), true);
	}
}