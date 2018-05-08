package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.PageDAO;
import com.samsung.fas.pir.persistence.models.Page;
import com.samsung.fas.pir.rest.dto.PageDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
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
	public PageDTO save(PageDTO create, UserDetails account) {
		return null;
	}

	@Override
	public PageDTO update(PageDTO update, UserDetails account) {
		return null;
	}

	@Override
	public Collection<PageDTO> save(Collection<PageDTO> create, UserDetails details) {
		return null;
	}

	@Override
	public Collection<PageDTO> update(Collection<PageDTO> update, UserDetails details) {
		return null;
	}
}
