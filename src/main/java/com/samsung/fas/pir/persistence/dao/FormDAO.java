package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Form;
import com.samsung.fas.pir.persistence.models.QForm;
import com.samsung.fas.pir.persistence.repositories.IForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FormDAO extends BaseDAO<Form, Long, IForm, QForm> {
	@Autowired
	public FormDAO(IForm repository) {
		super(repository);
	}

	public Collection<Form> findAll(int zone) {
		return getRepository().findAllByAgeZone(zone);
	}

	public void invalidate(int zone) {
		getRepository().invalidateAll(zone);
	}
}
