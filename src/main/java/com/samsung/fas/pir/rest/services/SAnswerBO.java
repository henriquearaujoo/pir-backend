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
	private 	ResponsibleDAO 	responsibleDAO;

	@Getter
	@Setter
	private 	SAlternativeDAO sAlternativeDAO;

	@Getter
	@Setter
	private 	SQuestionDAO 	sQuestionDAO;

	@Autowired
	protected SAnswerBO(SAnswerDAO dao, ChildDAO childDAO, ResponsibleDAO responsibleDAO, SQuestionDAO sQuestionDAO, SAlternativeDAO sAlternativeDAO) {
		super(dao);
		setResponsibleDAO(responsibleDAO);
		setChildDAO(childDAO);
		setSQuestionDAO(sQuestionDAO);
		setSAlternativeDAO(sAlternativeDAO);
	}

	@Override
	public SAnswerDTO save(SAnswerDTO create, Device device, UserDetails details) {
		SAnswer			model		= create.getModel();
		SAnswer			answer		= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		Responsible		responsible	= model.getResponsible().getUuid() != null? getResponsibleDAO().findOne(model.getResponsible().getUuid()) : null;
		Child			child		= model.getChild().getUuid() != null? getChildDAO().findOne(model.getChild().getUuid()) : null;
		SQuestion		question	= getSQuestionDAO().findOne(model.getQuestion().getUuid());
		SAlternative	alternative	= model.getAlternative().getUuid() != null? getSAlternativeDAO().findOne(model.getAlternative().getUuid()) : null;

		if (answer != null) {
			answer.setDescription(model.getDescription());
			answer.setAlternative(alternative);
			answer.setAgent(answer.getAgent() == null? ((Account) details).getUser() : answer.getAgent());
			answer.setQuestion(question);
			answer.setResponsible(responsible);
			answer.setChild(child);
			return new SAnswerDTO(getDao().save(answer), device, true);
		}
		model.setAlternative(alternative);
		model.setAgent(((Account) details).getUser());
		model.setQuestion(question);
		model.setResponsible(responsible);
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
}