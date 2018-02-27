package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.ConclusionDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.rest.dto.conclusion.CConclusionDTO;
import com.samsung.fas.pir.rest.dto.conclusion.RConclusionDTO;
import com.samsung.fas.pir.rest.dto.conclusion.UConclusionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConclusionService {
	private ConclusionDAO 	cdao;
	private ChapterDAO		chdao;

	@Autowired
	public ConclusionService(ConclusionDAO cdao, ChapterDAO chdao) {
		this.cdao 	= cdao;
		this.chdao 	= chdao;
	}

	public RConclusionDTO findOne(UUID id) {
		return RConclusionDTO.toDTO(cdao.findOne(id));
	}

	public List<RConclusionDTO> findAll() {
		return cdao.findAll().stream().map(RConclusionDTO::toDTO).collect(Collectors.toList());
	}

	public List<RConclusionDTO> findAll(Predicate predicate) {
		return cdao.findAll(predicate).stream().map(RConclusionDTO::toDTO).collect(Collectors.toList());
	}

	public Page<RConclusionDTO> findAll(Pageable pageable) {
		return cdao.findAll(pageable).map(RConclusionDTO::toDTO);
	}

	public Page<RConclusionDTO> findAll(Predicate predicate, Pageable pageable) {
		return cdao.findAll(predicate, pageable).map(RConclusionDTO::toDTO);
	}

	public RConclusionDTO save(CConclusionDTO dto) {
		Conclusion 	entity		= dto.getModel();
		Chapter 	chapter		= chdao.findOne(entity.getChapter().getUuid());

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.conclusion.chapterid.notfound");

		// Verify if chapter conclusion is null (consider updating instead creating)
		if (chapter.getConclusion() != null)
			throw new RESTRuntimeException("chapter.conclusion.notnull");

		// Set chapter for greetings
		entity.setChapter(chapter);
		chapter.setConclusion(entity);

		return RConclusionDTO.toDTO(chdao.save(chapter).getConclusion());
	}

	public RConclusionDTO update(UConclusionDTO dto) {
		Conclusion	model		= dto.getModel();
		Chapter		chapter		= chdao.findOne(model.getChapter().getUuid());
		Conclusion	conclusion	= cdao.findOne(model.getUuid());

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.conclusion.chapterid.notfound");

		// Verify if intervention exists
		if (chapter.getConclusion() == null)
			throw new RESTRuntimeException("chapter.conclusion.isnull");

		// Verify if informed intervention exist
		if (conclusion == null)
			throw new RESTRuntimeException("chapter.conclusion.notfound");

		// Verify if intervention chapter id is euqal to informed chapter id
		if (conclusion.getChapter().getUuid().compareTo(model.getChapter().getUuid()) != 0)
			throw new RESTRuntimeException("chapter.conclusion.id.differs");

		// Set chapter for greetings
		conclusion.setDescription(model.getDescription());
		conclusion.setChapter(chapter);
		return RConclusionDTO.toDTO(cdao.save(conclusion));
	}
}
