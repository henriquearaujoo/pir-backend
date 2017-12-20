package com.samsung.fas.pir.services;

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

	public RGreetingsDTO save(CGreetingsDTO dto) {
		Greetings	entity		= dto.getModel();
		Chapter		chapter		= cdao.findOne(entity.getChapter().getId());

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.greetings.chapterid.notfound");

		// Verify if chapter greetings is null (consider updating instead creating)
		if (chapter.getGreetings() != null)
			throw new RESTRuntimeException("chapter.greetings.notnull");

		entity.setChapter(chapter);
		chapter.setGreetings(entity);
		return RGreetingsDTO.toDTO(cdao.save(chapter).getGreetings());
	}

	public RGreetingsDTO update(UGreetingsDTO dto) {
		Greetings	entity		= dto.getModel();
		Chapter		chapter		= cdao.findOne(entity.getChapter().getId());
		Greetings	persisted	= gdao.findOne(entity.getId());

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.greetings.chapterid.notfound");

		// Verify if greetings exists
		if (chapter.getGreetings() == null)
			throw new RESTRuntimeException("chapter.greetings.isnull");

		// Verify if informed greetings exist
		if (persisted == null)
			throw new RESTRuntimeException("chapter.greetings.notfound");

		// Verify if greetings chapter id is euqal to informed chapter id
		if (persisted.getChapter().getId() != entity.getChapter().getId())
			throw new RESTRuntimeException("chapter.greetings.id.differs");

		// Set chapter for greetings
		entity.setChapter(chapter);
		chapter.setGreetings(entity);
		return RGreetingsDTO.toDTO(cdao.save(chapter).getGreetings());
	}
}
