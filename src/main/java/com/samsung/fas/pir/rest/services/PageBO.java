package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.PageDAO;
import com.samsung.fas.pir.persistence.models.Page;
import com.samsung.fas.pir.rest.dto.PageDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PageBO extends BaseBO<Page, PageDAO, PageDTO, Long> {
	@Autowired
	public PageBO(PageDAO dao) {
		super(dao);
	}

	@Override
	public PageDTO save(PageDTO create, Device device, UserDetails account) {
		return null;
	}

	@Override
	public PageDTO update(PageDTO update, Device device, UserDetails account) {
		return null;
	}

	@Override
	public Collection<PageDTO> save(Collection<PageDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<PageDTO> update(Collection<PageDTO> update, Device device, UserDetails details) {
		return null;
	}
}
