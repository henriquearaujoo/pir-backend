package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.InterventionDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.Intervention;
import com.samsung.fas.pir.rest.dto.intervention.CRUInterventionDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class InterventionService extends BService<Intervention, CRUInterventionDTO, InterventionDAO, Long> {
	private		ChapterDAO		cdao;

	@Autowired
	public InterventionService(InterventionDAO dao, ChapterDAO cdao) {
		super(dao, Intervention.class, CRUInterventionDTO.class);
		this.cdao = cdao;
	}

	@Override
	public CRUInterventionDTO save(CRUInterventionDTO create, UserDetails account) {
		Intervention	model		= create.getModel();
		UUID			chapterID	= create.getChapterdID() != null && !create.getChapterdID().trim().isEmpty()? IDCoder.decode(create.getChapterdID()) : null;
		Chapter			chapter		= Optional.ofNullable(cdao.findOne(Optional.ofNullable(chapterID).orElseThrow(() -> new RESTRuntimeException("chapter.id.missing")))).orElseThrow(() -> new RESTRuntimeException("chapter.notfound"));
		model.setChapter(chapter);
		return new CRUInterventionDTO(dao.save(model));
	}

	@Override
	public CRUInterventionDTO update(CRUInterventionDTO update, UserDetails account) {
		Intervention	model			= update.getModel();
		Intervention	intervention	= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("intervention.notfound"));

		// Verify if intervention chapter id is euqal to informed chapter id
		if (intervention.getChapter().getId() != model.getChapter().getId())
			throw new RESTRuntimeException("chapter.intervention.id.differs");

		intervention.setDescription(model.getDescription());
		intervention.setActivity(model.getActivity());
		return new CRUInterventionDTO(dao.save(intervention));
	}
}
