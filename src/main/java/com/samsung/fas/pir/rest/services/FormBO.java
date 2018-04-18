package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.FormDAO;
import com.samsung.fas.pir.persistence.models.Form;
import com.samsung.fas.pir.rest.dto.FormDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
public class FormBO extends BaseBO<Form, FormDAO, FormDTO, Long> {
	@Autowired
	public FormBO(FormDAO dao) {
		super(dao);
	}

	@Override
	public FormDTO save(FormDTO create, UserDetails account) {
		Form				model		= create.getModel();
		Collection<Form> 	versions	= new ArrayList<>(getDao().findAll(model.getAgeZone()));

		if (model.getFromValue() > model.getToValue())
			throw new RESTException("invalid.indicator");

		if (versions.size() > 0) {
			((ArrayList<Form>) versions).sort(Comparator.comparingInt(Form::getVersion).reversed());
			model.setVersion(((ArrayList<Form>) versions).get(0).getVersion() + 1);
			model.setEnabled(false);
		}

		return new FormDTO(getDao().save(model), true);
	}

	@Override
	public FormDTO update(FormDTO update, UserDetails account) {
		Form				model		= update.getModel();
		Collection<Form>	forms		= getDao().findAll();
		Form				form		= forms.stream().filter(item -> item.getUuid().compareTo(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTException("id.missing"))) == 0).findAny().orElseThrow(() -> new RESTException("not.found"));

		if (model.getFromValue() > model.getToValue())
			throw new RESTException("invalid.indicator");

		getDao().invalidate(model.getAgeZone());

		form.setFromValue(model.getFromValue());
		form.setToValue(model.getToValue());
		form.setAgeZone(model.getAgeZone());
		form.setEnabled(model.isEnabled());

		return new FormDTO(getDao().save(form), true);
	}
}