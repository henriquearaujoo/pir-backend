package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.SAlternativeDAO;
import com.samsung.fas.pir.persistence.dao.SAnswerDAO;
import com.samsung.fas.pir.persistence.models.SAlternative;
import com.samsung.fas.pir.persistence.models.SAnswer;
import com.samsung.fas.pir.rest.dto.SAlternativeDTO;
import com.samsung.fas.pir.rest.dto.SAnswerDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SAlternativeBO extends BaseBO<SAlternative, SAlternativeDAO, SAlternativeDTO, Long> {
	@Autowired
	protected SAlternativeBO(SAlternativeDAO dao) {
		super(dao);
	}

	@Override
	public SAlternativeDTO save(SAlternativeDTO create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public SAlternativeDTO update(SAlternativeDTO update, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<SAlternativeDTO> save(Collection<SAlternativeDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<SAlternativeDTO> update(Collection<SAlternativeDTO> update, Device device, UserDetails details) {
		return null;
	}
}