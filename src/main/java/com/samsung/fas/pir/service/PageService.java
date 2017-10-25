package com.samsung.fas.pir.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.PageDAO;
import com.samsung.fas.pir.dto.PageDTO;

@Service
public class PageService {
	@Autowired
	private 	PageDAO 	pgdao;
	
	public List<PageDTO> findAll() {
		return pgdao.findAll().stream().map(m -> PageDTO.toDTO(m)).collect(Collectors.toList());
	}

}
