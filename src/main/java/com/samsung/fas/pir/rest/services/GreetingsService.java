package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.GreetingsDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.Greetings;
import com.samsung.fas.pir.rest.dto.greetings.CGreetingsDTO;
import com.samsung.fas.pir.rest.dto.greetings.RGreetingsDTO;
import com.samsung.fas.pir.rest.dto.greetings.UGreetingsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GreetingsService {
	private		GreetingsDAO	gdao;
	private		ChapterDAO		cdao;

	@Autowired
	public GreetingsService(GreetingsDAO gdao, ChapterDAO cdao) {
		this.gdao 	= gdao;
		this.cdao	= cdao;
	}

	public RGreetingsDTO findOne(UUID id) {
		return RGreetingsDTO.toDTO(gdao.findOne(id));
	}

	public List<RGreetingsDTO> findAll() {
		return gdao.findAll().stream().map(RGreetingsDTO::toDTO).collect(Collectors.toList());
	}

	public List<RGreetingsDTO> findAll(Predicate predicate) {
		return gdao.findAll(predicate).stream().map(RGreetingsDTO::toDTO).collect(Collectors.toList());
	}

	public Page<RGreetingsDTO> findAll(Pageable pageable) {
		return gdao.findAll(pageable).map(RGreetingsDTO::toDTO);
	}

	public Page<RGreetingsDTO> findAll(Predicate predicate, Pageable pageable) {
		return gdao.findAll(predicate, pageable).map(RGreetingsDTO::toDTO);
	}

	public RGreetingsDTO save(CGreetingsDTO dto) {
		Greetings	model		= dto.getModel();
		Chapter		chapter		= cdao.findOne(model.getChapter().getUuid());

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.greetings.chapterid.notfound");

		// Verify if chapter greetings is null (consider updating instead creating)
		if (chapter.getGreetings() != null)
			throw new RESTRuntimeException("chapter.greetings.notnull");

		model.setChapter(chapter);
		return RGreetingsDTO.toDTO(gdao.save(model));
	}

	public RGreetingsDTO update(UGreetingsDTO dto) {
		Greetings	model		= dto.getModel();
		Chapter		chapter		= cdao.findOne(model.getChapter().getId());
		Greetings	greetings	= gdao.findOne(model.getId());

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.greetings.chapterid.notfound");

		// Verify if greetings exists
		if (chapter.getGreetings() == null)
			throw new RESTRuntimeException("chapter.greetings.isnull");

		// Verify if informed greetings exist
		if (greetings == null)
			throw new RESTRuntimeException("chapter.greetings.notfound");

		// Verify if greetings chapter id is euqal to informed chapter id
		if (greetings.getChapter().getId() != model.getChapter().getId())
			throw new RESTRuntimeException("chapter.greetings.id.differs");

		// Set chapter for greetings
		greetings.setDescription(model.getDescription());
		greetings.setChapter(chapter);
		greetings.setEletronics(model.isEletronics());
		greetings.setStove(model.isStove());
		greetings.setSit(model.isSit());
		greetings.setGoback(model.isGoback());
		return RGreetingsDTO.toDTO(gdao.save(greetings));
	}
}
