package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.dao.*;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.rest.dto.SAnswerDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SAnswerBO extends BaseBO<SAnswer, SAnswerDAO, SAnswerDTO, Long> {
	@Getter
	@Setter
	private	 	ChildDAO 		childDAO;

	@Getter
	@Setter
	private 	PregnancyDAO 	pregnancyDAO;

	@Getter
	@Setter
	private 	SAlternativeDAO sAlternativeDAO;

	@Getter
	@Setter
	private 	SQuestionDAO 	sQuestionDAO;

	@Autowired
	protected SAnswerBO(SAnswerDAO dao, ChildDAO childDAO, PregnancyDAO pregnancyDAO, SQuestionDAO sQuestionDAO, SAlternativeDAO sAlternativeDAO) {
		super(dao);
		setPregnancyDAO(pregnancyDAO);
		setChildDAO(childDAO);
		setSQuestionDAO(sQuestionDAO);
		setSAlternativeDAO(sAlternativeDAO);
	}

	@Override
	public SAnswerDTO save(SAnswerDTO create, Device device, UserDetails details) {
		SAnswer			model		= create.getModel();
		SAnswer			answer		= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		Pregnancy		pregnancy	= model.getPregnancy().getUuid() != null? getPregnancyDAO().findOne(model.getPregnancy().getUuid()) : null;
		Child			child		= model.getChild().getUuid() != null? getChildDAO().findOne(model.getChild().getUuid()) : null;
		SQuestion		question	= getSQuestionDAO().findOne(model.getQuestion().getUuid());
		SAlternative	alternative	= model.getAlternative().getUuid() != null? getSAlternativeDAO().findOne(model.getAlternative().getUuid()) : null;

		if (answer != null) {
			answer.setDescription(model.getDescription());
			answer.setAlternative(alternative);
			answer.setAgent(answer.getAgent() == null? ((Account) details).getUser() : answer.getAgent());
			answer.setQuestion(question);
			answer.setPregnancy(pregnancy);
			answer.setChild(child);
			return new SAnswerDTO(getDao().save(answer), device, true);
		}
		model.setAlternative(alternative);
		model.setAgent(((Account) details).getUser());
		model.setQuestion(question);
		model.setPregnancy(pregnancy);
		model.setChild(child);

		return new SAnswerDTO(getDao().save(model), device, true);
	}

	@Override
	public SAnswerDTO update(SAnswerDTO update, Device device, UserDetails details) {
		return save(update, device, details);
	}

	@Override
	public Collection<SAnswerDTO> save(Collection<SAnswerDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<SAnswerDTO> update(Collection<SAnswerDTO> update, Device device, UserDetails details) {
		return null;
	}


	// region Pregnancy
	@SuppressWarnings("Duplicates")
	SAnswer setupAnswer(SAnswer answer, SAnswer model, Pregnancy pregnancy) {
		SQuestion		question	= getSQuestionDAO().findOne(model.getQuestion().getUuid());
		SAlternative	alternative	= model.getAlternative().getUuid() != null? getSAlternativeDAO().findOne(model.getAlternative().getUuid()) : null;

		answer.setDescription(model.getDescription());
		answer.setAlternative(alternative);
//		answer.setAgent(answer.getAgent() == null? agent : answer.getAgent());
		answer.setQuestion(question);
		answer.setPregnancy(pregnancy);
		answer.setChild(null);
		return answer;
	}

	@SuppressWarnings("Duplicates")
	SAnswer setupAnswer(SAnswer model, Pregnancy pregnancy) {
		SQuestion		question	= getSQuestionDAO().findOne(model.getQuestion().getUuid());
		SAlternative	alternative	= model.getAlternative().getUuid() != null? getSAlternativeDAO().findOne(model.getAlternative().getUuid()) : null;

		model.setAlternative(alternative);
//		model.setAgent(agent);
		model.setQuestion(question);
		model.setPregnancy(pregnancy);
		model.setChild(null);
		return model;
	}
	// endregion

	// region Child
	@SuppressWarnings("Duplicates")
	SAnswer setupAnswer(SAnswer answer, SAnswer model, Child child) {
		SQuestion		question	= getSQuestionDAO().findOne(model.getQuestion().getUuid());
		SAlternative	alternative	= model.getAlternative().getUuid() != null? getSAlternativeDAO().findOne(model.getAlternative().getUuid()) : null;

		answer.setDescription(model.getDescription());
		answer.setAlternative(alternative);
//		answer.setAgent(answer.getAgent() == null? agent : answer.getAgent());
		answer.setQuestion(question);
		answer.setPregnancy(null);
		answer.setChild(child);
		return answer;
	}

	@SuppressWarnings("Duplicates")
	SAnswer setupAnswer(SAnswer model, Child child) {
		SQuestion		question	= getSQuestionDAO().findOne(model.getQuestion().getUuid());
		SAlternative	alternative	= model.getAlternative().getUuid() != null? getSAlternativeDAO().findOne(model.getAlternative().getUuid()) : null;

		model.setAlternative(alternative);
//		model.setAgent(agent);
		model.setQuestion(question);
		model.setPregnancy(null);
		model.setChild(child);
		return model;
	}
	// endregion
}