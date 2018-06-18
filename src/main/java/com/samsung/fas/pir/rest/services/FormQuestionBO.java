package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.FormDAO;
import com.samsung.fas.pir.persistence.dao.FormQuestionDAO;
import com.samsung.fas.pir.persistence.models.Form;
import com.samsung.fas.pir.persistence.models.FormQuestion;
import com.samsung.fas.pir.rest.dto.FormQuestionDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class FormQuestionBO extends BaseBO<FormQuestion, FormQuestionDAO, FormQuestionDTO, Long> {
	private	final FormDAO fdao;

	@Autowired
	public FormQuestionBO(FormQuestionDAO dao, @Autowired FormDAO fdao) {
		super(dao);
		this.fdao = fdao;
	}

	@Override
	public FormQuestionDTO save(FormQuestionDTO create, Device device, UserDetails account) {
		FormQuestion	model		= create.getModel();
		Form 			form		= fdao.findOne(create.getFormUUID());
		model.setForm(form);
		return new FormQuestionDTO(getDao().save(model), true);
	}

	@Override
	public FormQuestionDTO update(FormQuestionDTO update, Device device, UserDetails account) {
		FormQuestion	model		= update.getModel();
		FormQuestion	question	= getDao().findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTException("id.missing")));
		question.setType(model.getType());
		question.setDescription(model.getDescription());
		question.setEnabled(model.isEnabled());
		return new FormQuestionDTO(getDao().save(question), true);
	}

	@Override
	public Collection<FormQuestionDTO> save(Collection<FormQuestionDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<FormQuestionDTO> update(Collection<FormQuestionDTO> update, Device device, UserDetails details) {
		return null;
	}
}
