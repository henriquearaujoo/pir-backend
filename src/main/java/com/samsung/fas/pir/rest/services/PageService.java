package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.PageDAO;
import com.samsung.fas.pir.persistence.models.entity.Page;
import com.samsung.fas.pir.rest.dto.page.CRUPageDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.stereotype.Service;

@Service
public class PageService extends BService<Page, CRUPageDTO, PageDAO, Long> {
	public PageService(PageDAO dao) {
		super(dao, Page.class, CRUPageDTO.class);
	}

	@Override
	public CRUPageDTO save(CRUPageDTO create, Account account) {
		return null;
	}

	@Override
	public CRUPageDTO update(CRUPageDTO update, Account account) {
		return null;
	}
}
