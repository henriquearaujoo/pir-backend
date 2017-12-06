package com.samsung.fas.pir.models.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.dao.ChapterDAO;
import com.samsung.fas.pir.dao.GreetingsDAO;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.models.dto.greetings.CGreetingsDTO;
import com.samsung.fas.pir.models.dto.greetings.RGreetingsDTO;
import com.samsung.fas.pir.models.dto.greetings.UGreetingsDTO;
import com.samsung.fas.pir.models.entity.Chapter;
import com.samsung.fas.pir.models.entity.Greetings;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

	public RGreetingsDTO findOne(String id) {
		return RGreetingsDTO.toDTO(gdao.findOne(IDCoder.decodeLong(id)));
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

	public void save(CGreetingsDTO dto) {
		Chapter		chapter		= cdao.findOne(IDCoder.decodeLong(dto.getChapter()));
		Greetings	entity		= dto.getModel();

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.greetings.chapterid.notfound");

		// Verify if chapter greetings is null (consider updating instead creating)
		if (chapter.getGreetings() != null)
			throw new RESTRuntimeException("chapter.greetins.notnull");

		// Set chapter for greetings
		entity.setChapter(chapter);

		// Save greetings related to chapter id
		gdao.save(entity);
	}

	public void update(UGreetingsDTO dto) {
		Chapter		chapter		= cdao.findOne(IDCoder.decodeLong(dto.getChapter()));
		Greetings	entity		= dto.getModel();
		Greetings	persisted	= gdao.findOne(entity.getId());

		// Verify if greetings exists
		if (persisted == null)
			throw new RESTRuntimeException("chapter.greetings.id.noutfound");

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.greetings.chapterid.notfound");

		// Set chapter for greetings
		entity.setChapter(chapter);

		gdao.save(entity);
	}


}
