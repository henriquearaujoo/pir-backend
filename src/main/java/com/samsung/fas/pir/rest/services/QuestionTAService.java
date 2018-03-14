package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.FormDAO;
import com.samsung.fas.pir.persistence.dao.QuestionTADAO;
import com.samsung.fas.pir.persistence.models.entity.Form;
import com.samsung.fas.pir.persistence.models.entity.QuestionTA;
import com.samsung.fas.pir.rest.dto.FormQuestionTADTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionTAService extends BService<QuestionTA, FormQuestionTADTO, QuestionTADAO, Long> {
	private FormDAO fdao;

	@Autowired
	public QuestionTAService(QuestionTADAO dao, FormDAO fdao) {
		super(dao, QuestionTA.class, FormQuestionTADTO.class);
		this.fdao = fdao;
	}

	@Override
	public FormQuestionTADTO save(FormQuestionTADTO create, UserDetails account) {
		QuestionTA		model		= create.getModel();
		Form			form		= Optional.ofNullable(create.getFormID() != null && !create.getFormID().trim().isEmpty()? Optional.ofNullable(fdao.findOne(IDCoder.decode(create.getFormID()))).orElseThrow(() -> new RESTRuntimeException("form.notfound")) : null).orElseThrow(() -> new RESTRuntimeException("id.missing"));
		model.setForm(form);
		return new FormQuestionTADTO(dao.save(model), true);
	}

	@Override
	public FormQuestionTADTO update(FormQuestionTADTO update, UserDetails account) {
		QuestionTA		model		= update.getModel();
		QuestionTA		question	= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("question.notfound"));
		question.setDimension(model.getDimension());
		question.setDescription(model.getDescription());
		return new FormQuestionTADTO(dao.save(question), true);
	}
}
