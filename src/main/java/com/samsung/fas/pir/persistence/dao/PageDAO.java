package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Page;
import com.samsung.fas.pir.persistence.models.QPage;
import com.samsung.fas.pir.persistence.repositories.IPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageDAO extends BaseDAO<Page, Long, QPage> {
	@Autowired
	public PageDAO(IPageRepository repository) {
		super(repository);
	}
}
