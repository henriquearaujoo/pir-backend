package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.FormDAO;
import com.samsung.fas.pir.persistence.models.entity.Form;
import com.samsung.fas.pir.rest.dto.FormDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FormService extends BService<Form, FormDTO, FormDAO, Long> {

	@Autowired
	public FormService(FormDAO dao) {
		super(dao, Form.class, FormDTO.class);
	}

	@Override
	public FormDTO save(FormDTO create, UserDetails account) {
		Form		model		= create.getModel();

		if (model.getFromValue() > model.getToValue())
			throw new RESTRuntimeException("invalid.indicator");

		return new FormDTO(dao.save(model), true);
	}

	@Override
	public FormDTO update(FormDTO update, UserDetails account) {
		Form		model		= update.getModel();
		Form		form		= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("form.notfound"));

		if (model.getFromValue() > model.getToValue())
			throw new RESTRuntimeException("invalid.indicator");

		form.setFromValue(model.getFromValue());
		form.setToValue(model.getToValue());
		form.setRange(model.getRange());

		return new FormDTO(dao.save(form), true);
	}
}
