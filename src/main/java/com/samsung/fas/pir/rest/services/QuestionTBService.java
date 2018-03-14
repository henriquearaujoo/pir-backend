package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.FormDAO;
import com.samsung.fas.pir.persistence.dao.QuestionTBDAO;
import com.samsung.fas.pir.persistence.models.entity.Form;
import com.samsung.fas.pir.persistence.models.entity.QuestionTB;
import com.samsung.fas.pir.rest.dto.FormQuestionTBDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionTBService extends BService<QuestionTB, FormQuestionTBDTO, QuestionTBDAO, Long> {
	private FormDAO fdao;

	@Autowired
	public QuestionTBService(QuestionTBDAO dao, FormDAO fdao) {
		super(dao, QuestionTB.class, FormQuestionTBDTO.class);
		this.fdao = fdao;
	}

	@Override
	public FormQuestionTBDTO save(FormQuestionTBDTO create, UserDetails account) {
		QuestionTB		model		= create.getModel();
		Form 			form		= Optional.ofNullable(create.getFormID() != null && !create.getFormID().trim().isEmpty()? Optional.ofNullable(fdao.findOne(IDCoder.decode(create.getFormID()))).orElseThrow(() -> new RESTRuntimeException("form.notfound")) : null).orElseThrow(() -> new RESTRuntimeException("id.missing"));
		model.setForm(form);
		return new FormQuestionTBDTO(dao.save(model), true);
	}

	@Override
	public FormQuestionTBDTO update(FormQuestionTBDTO update, UserDetails account) {
		QuestionTB		model		= update.getModel();
		QuestionTB		question	= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("question.notfound"));
		question.setDescription(model.getDescription());
		return new FormQuestionTBDTO(dao.save(question), true);
	}
}