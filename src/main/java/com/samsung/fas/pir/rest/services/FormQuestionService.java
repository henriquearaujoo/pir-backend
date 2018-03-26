package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.FormDAO;
import com.samsung.fas.pir.persistence.dao.FormQuestionDAO;
import com.samsung.fas.pir.persistence.models.Form;
import com.samsung.fas.pir.persistence.models.FormQuestion;
import com.samsung.fas.pir.rest.dto.FormQuestionDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FormQuestionService extends BService<FormQuestion, FormQuestionDTO, FormQuestionDAO, Long> {
	private	final FormDAO fdao;

	@Autowired
	public FormQuestionService(FormQuestionDAO dao, @Autowired FormDAO fdao) {
		super(dao, FormQuestion.class, FormQuestionDTO.class);
		this.fdao = fdao;
	}

	@Override
	public FormQuestionDTO save(FormQuestionDTO create, UserDetails account) {
		FormQuestion	model		= create.getModel();
		Form 			form		= fdao.findOne(IDCoder.decode(create.getFormID()));
		model.setForm(form);
		return new FormQuestionDTO(dao.save(model), true);
	}

	@Override
	public FormQuestionDTO update(FormQuestionDTO update, UserDetails account) {
		FormQuestion	model		= update.getModel();
		FormQuestion	question	= dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTException("id.missing")));
		question.setType(model.getType());
		question.setDescription(model.getDescription());
		question.setEnabled(model.isEnabled());
		return new FormQuestionDTO(dao.save(question), true);
	}
}
