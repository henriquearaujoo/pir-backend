package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.ConclusionDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.Conclusion;
import com.samsung.fas.pir.rest.dto.ConclusionDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ConclusionBO extends BaseBO<Conclusion, ConclusionDAO, ConclusionDTO, Long> {
	private	final ChapterDAO cdao;

	@Autowired
	public ConclusionBO(ConclusionDAO dao, @Autowired ChapterDAO cdao) {
		super(dao);
		this.cdao = cdao;
	}

	@Override
	public ConclusionDTO save(ConclusionDTO create, Device device, UserDetails account) {
		Conclusion 	model		= create.getModel();
		Chapter 	chapter		= cdao.findOne(create.getChapterUUID());

		model.setChapter(chapter);
		chapter.setConclusion(model);

		return new ConclusionDTO(getDao().save(model), device, true);
	}

	@Override
	public ConclusionDTO update(ConclusionDTO update, Device device, UserDetails account) {
		Conclusion	model		= update.getModel();
		Conclusion	conclusion	= getDao().findOne(model.getUuid());

		conclusion.setDescription(model.getDescription());
		conclusion.setObservations(model.getObservations());

		return new ConclusionDTO(getDao().save(conclusion), device, true);
	}

	@Override
	public Collection<ConclusionDTO> save(Collection<ConclusionDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<ConclusionDTO> update(Collection<ConclusionDTO> update, Device device, UserDetails details) {
		return null;
	}
}