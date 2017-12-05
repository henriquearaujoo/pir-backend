package com.samsung.fas.pir.models.bo;

import com.samsung.fas.pir.dao.PageDAO;
import com.samsung.fas.pir.models.dto.page.RSimplePageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageService {
	private 	PageDAO 	pgdao;

	@Autowired
	public PageService(PageDAO pgdao) {
		this.pgdao = pgdao;
	}
	
	public List<RSimplePageDTO> findAll() {
		return pgdao.findAll().stream().map(RSimplePageDTO::toDTO).collect(Collectors.toList());
	}

}
