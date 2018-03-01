package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.ConclusionDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.rest.dto.conclusion.CRUConclusionDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConclusionService extends BService<Conclusion, CRUConclusionDTO, ConclusionDAO, Long> {
	private ChapterDAO cdao;

	@Autowired
	public ConclusionService(ConclusionDAO dao, ChapterDAO cdao) {
		super(dao, Conclusion.class, CRUConclusionDTO.class);
		this.cdao = cdao;
	}

	@Override
	public CRUConclusionDTO save(CRUConclusionDTO create, UserDetails account) {
		Conclusion 	model		= create.getModel();
		Chapter 	chapter		= Optional.ofNullable(cdao.findOne(IDCoder.decode(Optional.ofNullable(create.getChapterID()).orElseThrow(() -> new RESTRuntimeException("chapter.id.missing"))))).orElseThrow(() -> new RESTRuntimeException("chapter.notfound"));
		model.setChapter(chapter);
		chapter.setConclusion(model);
		return new CRUConclusionDTO(dao.save(model));
	}

	@Override
	public CRUConclusionDTO update(CRUConclusionDTO update, UserDetails account) {
		Conclusion	model		= update.getModel();
		Conclusion	conclusion	= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("conclusion.notfound"));
		conclusion.setDescription(model.getDescription());
		return new CRUConclusionDTO(dao.save(conclusion));
	}
}