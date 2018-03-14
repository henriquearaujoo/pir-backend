package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.MotherDAO;
import com.samsung.fas.pir.persistence.models.entity.Mother;
import com.samsung.fas.pir.rest.dto.ResponsibleDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class MotherService extends BService<Mother, ResponsibleDTO, MotherDAO, Long> {

	@Autowired
	public MotherService(MotherDAO dao) {
		super(dao, Mother.class, ResponsibleDTO.class);
	}

	@Override
	public ResponsibleDTO save(ResponsibleDTO cruResponsibleDTO, UserDetails account) {
		return null;
	}

	@Override
	public ResponsibleDTO update(ResponsibleDTO cruResponsibleDTO, UserDetails account) {
		return null;
	}
}
