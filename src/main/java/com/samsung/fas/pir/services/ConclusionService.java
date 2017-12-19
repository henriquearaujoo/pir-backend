package com.samsung.fas.pir.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.dao.ChapterDAO;
import com.samsung.fas.pir.dao.ConclusionDAO;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.models.dto.conclusion.CConclusionDTO;
import com.samsung.fas.pir.models.dto.conclusion.RConclusionDTO;
import com.samsung.fas.pir.models.dto.conclusion.UConclusionDTO;
import com.samsung.fas.pir.models.entity.Chapter;
import com.samsung.fas.pir.models.entity.Conclusion;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

	public RConclusionDTO findOne(String id) {
		return RConclusionDTO.toDTO(cdao.findOne(IDCoder.decodeLong(id)));
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
		Chapter 	chapter		= chdao.findOne(entity.getChapter().getId());

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
		Conclusion	entity		= dto.getModel();
		Chapter		chapter		= chdao.findOne(entity.getChapter().getId());
		Conclusion	persisted	= cdao.findOne(entity.getId());

		// Verify if conclusion exists
		if (persisted == null)
			throw new RESTRuntimeException("chapter.conclusion.id.noutfound");

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.conclusion.chapterid.notfound");

		// Verify if this conclusion is in informed chapter id
		if (chapter.getId() != entity.getChapter().getId())
			throw new RESTRuntimeException("chapter.conclusion.id.differs");

		// Set chapter for greetings
		entity.setChapter(chapter);
		chapter.setConclusion(entity);
		return RConclusionDTO.toDTO(chdao.save(chapter).getConclusion());
	}
}
