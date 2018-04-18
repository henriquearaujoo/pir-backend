package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.ConclusionDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.Conclusion;
import com.samsung.fas.pir.rest.dto.ConclusionDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ConclusionBO extends BaseBO<Conclusion, ConclusionDAO, ConclusionDTO, Long> {
	private	final ChapterDAO cdao;

	@Autowired
	public ConclusionBO(ConclusionDAO dao, @Autowired ChapterDAO cdao) {
		super(dao);
		this.cdao = cdao;
	}

	@Override
	public ConclusionDTO save(ConclusionDTO create, UserDetails account) {
		Conclusion 	model		= create.getModel();
		Chapter 	chapter		= cdao.findOne(create.getChapterUUID());

		model.setChapter(chapter);
		chapter.setConclusion(model);

		return new ConclusionDTO(getDao().save(model), true);
	}

	@Override
	public ConclusionDTO update(ConclusionDTO update, UserDetails account) {
		Conclusion	model		= update.getModel();
		Conclusion	conclusion	= getDao().findOne(model.getUuid());

		conclusion.setDescription(model.getDescription());

		return new ConclusionDTO(getDao().save(conclusion), true);
	}
}