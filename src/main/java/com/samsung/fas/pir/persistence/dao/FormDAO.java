package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Form;
import com.samsung.fas.pir.persistence.models.QForm;
import com.samsung.fas.pir.persistence.repositories.IFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FormDAO extends BaseDAO<Form, Long, QForm> {
	@Autowired
	public FormDAO(IFormRepository repository) {
		super(repository);
	}

	public Collection<Form> findAll(int zone) {
		return ((IFormRepository) repository).findAllByAgeZone(zone);
	}

	public void invalidate(int zone) {
		((IFormRepository) repository).invalidateAll(zone);
	}
}
