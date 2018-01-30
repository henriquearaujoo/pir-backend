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

	public RInterventionDTO findOne(String id) {
		return RInterventionDTO.toDTO(idao.findOne(IDCoder.decodeLong(id)));
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
		Intervention	entity		= dto.getModel();
		Chapter			chapter		= cdao.findOne(IDCoder.decodeLong(dto.getChapterdID()));

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.id.notfound");

		// Verify if chapter's intervention exists
		if (chapter.getIntervention() != null)
			throw new RESTRuntimeException("chapter.intervention.exists");

		// If chapters exists exists and intervention not, then create intervention
		entity.setChapter(chapter);
		chapter.setIntervention(entity);
		return RInterventionDTO.toDTO(cdao.save(chapter).getIntervention());
	}

	public RInterventionDTO update(UInterventionDTO dto) {
		Intervention	entity		= dto.getModel();
		Intervention	persisted	= idao.findOne(entity.getId());
		Chapter			chapter		= cdao.findOne(entity.getChapter().getId());

		// Verify if chapter exists
		if (chapter == null)
			throw new RESTRuntimeException("chapter.intervention.chapterid.notfound");

		// Verify if intervention exists
		if (chapter.getIntervention() == null)
			throw new RESTRuntimeException("chapter.intervention.isnull");

		// Verify if informed intervention exist
		if (persisted == null)
			throw new RESTRuntimeException("chapter.intervention.notfound");

		// Verify if intervention chapter id is euqal to informed chapter id
		if (persisted.getChapter().getId() != entity.getChapter().getId())
			throw new RESTRuntimeException("chapter.intervention.id.differs");

		// Set chapter for intervention
		entity.setChapter(chapter);
		chapter.setIntervention(entity);
		return RInterventionDTO.toDTO(cdao.save(chapter).getIntervention());
	}
}
