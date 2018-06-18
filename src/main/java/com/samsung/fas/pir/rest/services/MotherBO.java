package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.MotherDAO;
import com.samsung.fas.pir.persistence.models.Mother;
import com.samsung.fas.pir.rest.dto.ResponsibleDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MotherBO extends BaseBO<Mother, MotherDAO, ResponsibleDTO, Long> {
	@Autowired
	public MotherBO(MotherDAO dao) {
		super(dao);
	}

	@Override
	public ResponsibleDTO save(ResponsibleDTO cruResponsibleDTO, Device device, UserDetails account) {
		return null;
	}

	@Override
	public ResponsibleDTO update(ResponsibleDTO cruResponsibleDTO, Device device, UserDetails account) {
		return null;
	}

	@Override
	public Collection<ResponsibleDTO> save(Collection<ResponsibleDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<ResponsibleDTO> update(Collection<ResponsibleDTO> update, Device device, UserDetails details) {
		return null;
	}
}
