package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.ConclusionDAO;
import com.samsung.fas.pir.persistence.dao.QuestionDAO;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.persistence.models.entity.Question;
import com.samsung.fas.pir.rest.dto.question.CQuestionDTO;
import com.samsung.fas.pir.rest.dto.question.RQuestionDTO;
import com.samsung.fas.pir.rest.dto.question.UQuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {
	private QuestionDAO 	qdao;
	private ConclusionDAO	cdao;
	private ChapterDAO		chdao;

	@Autowired
	public QuestionService(QuestionDAO qdao, ConclusionDAO cdao, ChapterDAO chdao) {
		this.qdao 	= qdao;
		this.cdao	= cdao;
		this.chdao	= chdao;
	}

	public RQuestionDTO findOne(UUID id) {
		return RQuestionDTO.toDTO(qdao.findOne(id));
	}

	public List<RQuestionDTO> findAll() {
		return qdao.findAll().stream().map(RQuestionDTO::toDTO).collect(Collectors.toList());
	}

	public List<RQuestionDTO> findAll(Predicate predicate) {
		return qdao.findAll(predicate).stream().map(RQuestionDTO::toDTO).collect(Collectors.toList());
	}

	public Page<RQuestionDTO> findAll(Pageable pageable) {
		return qdao.findAll(pageable).map(RQuestionDTO::toDTO);
	}

	public Page<RQuestionDTO> findAll(Predicate predicate, Pageable pageable) {
		return qdao.findAll(predicate, pageable).map(RQuestionDTO::toDTO);
	}

	public void delete(UUID id) {
		Question question = qdao.findOne(id);

		if (question != null) {
			Conclusion conclusion = question.getConclusion();
			qdao.delete(question.getId());

			if (conclusion.getQuestions().size() == 0) {
				chdao.invalidateOne(conclusion.getChapter().getId());
			}
		}
	}

	public RQuestionDTO save(CQuestionDTO dto) {
		Question		model		= dto.getModel();
		Conclusion		centity		= cdao.findOne(model.getConclusion().getUuid());

		// If there's no conclusion with given id
		if (centity == null)
			throw new RESTRuntimeException("question.conclusion.notfound");

		// If same question exists
		if (centity.getQuestions().stream().filter(item -> item.getDescription().equalsIgnoreCase(model.getDescription())).findAny().orElse(null) != null)
			throw new RESTRuntimeException("question.exists");

		model.setConclusion(centity);
		centity.getQuestions().add(model);
		return RQuestionDTO.toDTO(qdao.save(model));
	}

	public RQuestionDTO update(UQuestionDTO dto) {
		Question		model		= dto.getModel();
		Question		question	= qdao.findOne(model.getId());
		Conclusion		conclusion	= cdao.findOne(model.getConclusion().getId());

		if (question == null)
			throw new RESTRuntimeException("question.notfound");

		if (conclusion == null)
			throw new RESTRuntimeException("question.conclusion.notfound");

		Question		exists		= conclusion.getQuestions().stream().filter(item -> item.getDescription().equalsIgnoreCase(model.getDescription())).findAny().orElse(null);
		if (exists != null)
			if (exists.getId() != model.getId())
				throw new RESTRuntimeException("question.exists");

		question.setConclusion(conclusion);
		question.setDescription(model.getDescription());
		question.setType(model.getType());
		return RQuestionDTO.toDTO(qdao.save(question));
	}
}
