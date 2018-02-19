package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.InterventionDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.Intervention;
import com.samsung.fas.pir.rest.dto.intervention.CInterventionDTO;
import com.samsung.fas.pir.rest.dto.intervention.RInterventionDTO;
import com.samsung.fas.pir.rest.dto.intervention.UInterventionDTO;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InterventionService {
	private		InterventionDAO	idao;
	private		ChapterDAO		cdao;

	@Autowired
	public InterventionService(InterventionDAO idao, ChapterDAO cdao) {
		this.idao 	= idao;
		this.cdao	= cdao;
	}

	public RInterventionDTO findOne(UUID id) {
		return RInterventionDTO.toDTO(idao.findOne(id));
	}

	public List<RInterventionDTO> findAll() {
		return idao.findAll().stream().map(RInterventionDTO::toDTO).collect(Collectors.toList());
	}

	public List<RInterventionDTO> findAll(Predicate predicate) {
		return idao.findAll(predicate).stream().map(RInterventionDTO::toDTO).collect(Collectors.toList());
	}

	public Page<RInterventionDTO> findAll(Pageable pageable) {
		return idao.findAll(pageable).map(RInterventionDTO::toDTO);
	}

	public Page<RInterventionDTO> findAll(Predicate predicate, Pageable pageable) {
		return idao.findAll(predicate, pageable).map(RInterventionDTO::toDTO);
	}

	public RInterventionDTO save(CInterventionDTO dto) {
		Intervention	model		= dto.getModel();
		Chapter			chapter		= cdao.findOne(IDCoder.decode(dto.getChapterdID()));

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.id.notfound");

		// Verify if chapter's intervention exists
		if (chapter.getIntervention() != null)
			throw new RESTRuntimeException("chapter.intervention.exists");

		// If chapters exists exists and intervention not, then create intervention
		model.setChapter(chapter);
		chapter.setIntervention(model);

		return RInterventionDTO.toDTO(idao.save(model));
	}

	public RInterventionDTO update(UInterventionDTO dto) {
		Intervention	model			= dto.getModel();
		Intervention	intervention	= idao.findOne(model.getId());
		Chapter			chapter			= cdao.findOne(model.getChapter().getId());

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.intervention.chapterid.notfound");

		// Verify if intervention exists
		if (chapter.getIntervention() == null)
			throw new RESTRuntimeException("chapter.intervention.isnull");

		// Verify if informed intervention exist
		if (intervention == null)
			throw new RESTRuntimeException("chapter.intervention.notfound");

		// Verify if intervention chapter id is euqal to informed chapter id
		if (intervention.getChapter().getId() != model.getChapter().getId())
			throw new RESTRuntimeException("chapter.intervention.id.differs");

		// Set chapter for intervention
		intervention.setChapter(chapter);
		intervention.setDescription(model.getDescription());
		intervention.setActivity(model.getActivity());
		return RInterventionDTO.toDTO(idao.save(intervention));
	}
}
