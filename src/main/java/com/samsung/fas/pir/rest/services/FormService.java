package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.FormDAO;
import com.samsung.fas.pir.persistence.models.entity.Form;
import com.samsung.fas.pir.rest.dto.FormDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
public class FormService extends BService<Form, FormDTO, FormDAO, Long> {
	private Comparator<Form> comparator = (formA, formB) -> {
		if (formB.getFromValue() < formA.getFromValue() && formB.getToValue() > formA.getFromValue())
			return 0;	// Intersection
		else if (formB.getFromValue() < formA.getToValue() && formB.getToValue() > formA.getToValue())
			return 0;	// Another Intersection
		else if (formB.getFromValue() >= formA.getFromValue() && formB.getToValue() <= formA.getToValue())
			return 0;	// Form 2 is totally inside Form 1
		return formB.getFromValue() - formB.getToValue()/2 - formA.getFromValue() - formA.getToValue()/2;
	};

	@Autowired
	public FormService(FormDAO dao) {
		super(dao, Form.class, FormDTO.class);
	}

	@Override
	public FormDTO save(FormDTO create, UserDetails account) {
		Form				model		= create.getModel();
		Collection<Form> 	versions	= new ArrayList<>(dao.findAll(model.getAgeZone()));

		if (model.getFromValue() > model.getToValue())
			throw new RESTRuntimeException("invalid.indicator");

		if (versions.size() > 0) {
			((ArrayList<Form>) versions).sort(Comparator.comparingInt(Form::getVersion).reversed());
			model.setVersion(((ArrayList<Form>) versions).get(0).getVersion() + 1);
			model.setEnabled(false);
		}

		return new FormDTO(dao.save(model), true);
	}

	@Override
	public FormDTO update(FormDTO update, UserDetails account) {
		Form				model		= update.getModel();
		Collection<Form>	forms		= dao.findAll();
		Form				form		= forms.stream().filter(item -> item.getUuid().compareTo(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing"))) == 0).findAny().orElseThrow(() -> new RESTRuntimeException("form.notfound"));

		if (model.getFromValue() > model.getToValue())
			throw new RESTRuntimeException("invalid.indicator");

		dao.invalidate(model.getAgeZone());

		form.setFromValue(model.getFromValue());
		form.setToValue(model.getToValue());
		form.setAgeZone(model.getAgeZone());
		form.setEnabled(model.isEnabled());

		return new FormDTO(dao.save(form), true);
	}
}