package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.FormDAO;
import com.samsung.fas.pir.persistence.dao.FormQuestionDAO;
import com.samsung.fas.pir.persistence.models.entity.Form;
import com.samsung.fas.pir.persistence.models.entity.FormQuestion;
import com.samsung.fas.pir.rest.dto.FormQuestionDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FormQuestionService extends BService<FormQuestion, FormQuestionDTO, FormQuestionDAO, Long> {
	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		FormDAO		formDAO;

	@Autowired
	public FormQuestionService(FormQuestionDAO dao, FormDAO fdao) {
		super(dao, FormQuestion.class, FormQuestionDTO.class);
		setFormDAO(fdao);
	}

	@Override
	public FormQuestionDTO save(FormQuestionDTO create, UserDetails account) {
		FormQuestion	model		= create.getModel();
		Form			form		= Optional.ofNullable(create.getFormID() != null && !create.getFormID().trim().isEmpty()? Optional.ofNullable(getFormDAO().findOne(IDCoder.decode(create.getFormID()))).orElseThrow(() -> new RESTRuntimeException("form.notfound")) : null).orElseThrow(() -> new RESTRuntimeException("id.missing"));
		model.setForm(form);
		return new FormQuestionDTO(dao.save(model), true);
	}

	@Override
	public FormQuestionDTO update(FormQuestionDTO update, UserDetails account) {
		FormQuestion	model		= update.getModel();
		FormQuestion	question	= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("question.notfound"));
		question.setType(model.getType());
		question.setDescription(model.getDescription());
		question.setEnabled(model.isEnabled());
		return new FormQuestionDTO(dao.save(question), true);
	}
}
