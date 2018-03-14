package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.PageDAO;
import com.samsung.fas.pir.persistence.models.entity.Page;
import com.samsung.fas.pir.rest.dto.PageDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PageService extends BService<Page, PageDTO, PageDAO, Long> {
	public PageService(PageDAO dao) {
		super(dao, Page.class, PageDTO.class);
	}

	@Override
	public PageDTO save(PageDTO create, UserDetails account) {
		return null;
	}

	@Override
	public PageDTO update(PageDTO update, UserDetails account) {
		return null;
	}
}
