package com.samsung.fas.pir.models.services;

import com.samsung.fas.pir.dao.ChapterDAO;
import com.samsung.fas.pir.dao.InterventionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterventionService {
	private		InterventionDAO	idao;
	private		ChapterDAO		cdao;

	@Autowired
	public InterventionService(InterventionDAO idao, ChapterDAO cdao) {
		this.idao 	= idao;
		this.cdao	= cdao;
	}

//	public RGreetingsDTO findOne(String id) {
//		return RGreetingsDTO.toDTO(gdao.findOne(IDCoder.decodeLong(id)));
//	}
//
//	public List<RGreetingsDTO> findAll() {
//		return gdao.findAll().stream().map(RGreetingsDTO::toDTO).collect(Collectors.toList());
//	}
//
//	public List<RGreetingsDTO> findAll(Predicate predicate) {
//		return gdao.findAll(predicate).stream().map(RGreetingsDTO::toDTO).collect(Collectors.toList());
//	}
//
//	public Page<RGreetingsDTO> findAll(Pageable pageable) {
//		return gdao.findAll(pageable).map(RGreetingsDTO::toDTO);
//	}
//
//	public Page<RGreetingsDTO> findAll(Predicate predicate, Pageable pageable) {
//		return gdao.findAll(predicate, pageable).map(RGreetingsDTO::toDTO);
//	}
}
