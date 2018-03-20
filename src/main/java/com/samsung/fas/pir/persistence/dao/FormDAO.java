package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.entity.Form;
import com.samsung.fas.pir.persistence.models.entity.QForm;
import com.samsung.fas.pir.persistence.repository.IFormRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FormDAO extends BaseDAO<Form, Long, QForm> {
	public Collection<Form> findAll(int zone) {
		return ((IFormRepository) repository).findAllByAgeZone(zone);
	}

	public void invalidate(int zone) {
		((IFormRepository) repository).invalidateAll(zone);
	}
}
