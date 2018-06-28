package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.SQuestionDAO;
import com.samsung.fas.pir.persistence.models.SQuestion;
import com.samsung.fas.pir.rest.dto.SQuestionDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SQuestionBO extends BaseBO<SQuestion, SQuestionDAO, SQuestionDTO, Long> {
	@Autowired
	protected SQuestionBO(SQuestionDAO dao) {
		super(dao);
	}

	@Override
	public SQuestionDTO save(SQuestionDTO create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public SQuestionDTO update(SQuestionDTO update, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<SQuestionDTO> save(Collection<SQuestionDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<SQuestionDTO> update(Collection<SQuestionDTO> update, Device device, UserDetails details) {
		return null;
	}
}