package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.MotherDAO;
import com.samsung.fas.pir.persistence.models.entity.Mother;
import com.samsung.fas.pir.rest.dto.responsible.CRUResponsibleDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class MotherService extends BService<Mother, CRUResponsibleDTO, MotherDAO, Long> {

	@Autowired
	public MotherService(MotherDAO dao) {
		super(dao, Mother.class, CRUResponsibleDTO.class);
	}

	@Override
	public CRUResponsibleDTO save(CRUResponsibleDTO cruResponsibleDTO, UserDetails account) {
		return null;
	}

	@Override
	public CRUResponsibleDTO update(CRUResponsibleDTO cruResponsibleDTO, UserDetails account) {
		return null;
	}
}
