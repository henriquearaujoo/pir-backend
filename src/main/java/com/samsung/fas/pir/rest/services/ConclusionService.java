package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.ConclusionDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.Conclusion;
import com.samsung.fas.pir.rest.dto.ConclusionDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ConclusionService extends BService<Conclusion, ConclusionDTO, ConclusionDAO, Long> {
	private	final ChapterDAO cdao;

	@Autowired
	public ConclusionService(ConclusionDAO dao, @Autowired ChapterDAO cdao) {
		super(dao, Conclusion.class, ConclusionDTO.class);
		this.cdao = cdao;
	}

	@Override
	public ConclusionDTO save(ConclusionDTO create, UserDetails account) {
		Conclusion 	model		= create.getModel();
		Chapter 	chapter		= cdao.findOne(IDCoder.decode(create.getChapterID()));
		model.setChapter(chapter);
		chapter.setConclusion(model);
		return new ConclusionDTO(dao.save(model), true);
	}

	@Override
	public ConclusionDTO update(ConclusionDTO update, UserDetails account) {
		Conclusion	model		= update.getModel();
		Conclusion	conclusion	= dao.findOne(model.getUuid());
		conclusion.setDescription(model.getDescription());
		return new ConclusionDTO(dao.save(conclusion), true);
	}
}