package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.AnswerDAO;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.QuestionDAO;
import com.samsung.fas.pir.persistence.models.entity.Answer;
import com.samsung.fas.pir.persistence.models.entity.Question;
import com.samsung.fas.pir.rest.dto.answer.CAnswerDTO;
import com.samsung.fas.pir.rest.dto.answer.RAnswerDTO;
import com.samsung.fas.pir.rest.dto.answer.UAnswerDTO;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnswerService {
	private	AnswerDAO	adao;
	private	QuestionDAO	qdao;
	private ChapterDAO 	chdao;

	@Autowired
	public AnswerService(AnswerDAO adao, QuestionDAO qdao, ChapterDAO chdao) {
		this.adao 	= adao;
		this.qdao	= qdao;
		this.chdao	= chdao;
	}

	public RAnswerDTO findOne(String id) {
		return RAnswerDTO.toDTO(adao.findOne(IDCoder.decode(id)));
	}

	public List<RAnswerDTO> findAll() {
		return adao.findAll().stream().map(RAnswerDTO::toDTO).collect(Collectors.toList());
	}

	public List<RAnswerDTO> findAll(Predicate predicate) {
		return adao.findAll(predicate).stream().map(RAnswerDTO::toDTO).collect(Collectors.toList());
	}

	public Page<RAnswerDTO> findAll(Pageable pageable) {
		return adao.findAll(pageable).map(RAnswerDTO::toDTO);
	}

	public Page<RAnswerDTO> findAll(Predicate predicate, Pageable pageable) {
		return adao.findAll(predicate, pageable).map(RAnswerDTO::toDTO);
	}

	public void delete(UUID id) {
		Answer answer = adao.findOne(id);

		if (answer != null) {
			Question question = answer.getQuestion();
			adao.delete(answer.getId());

			if (question.getAnswers().size() == 0) {
				chdao.invalidateOne(question.getConclusion().getChapter().getId());
			}
		}
	}

	public RAnswerDTO save(CAnswerDTO dto) {
		Answer		model		= dto.getModel();
		Question	qentity		= qdao.findOne(model.getQuestion().getUuid());

		// If no question with given id
		if (qentity == null)
			throw new RESTRuntimeException("answer.question.notfound");

		// If same answer exists
		if (qentity.getAnswers().stream().filter(item -> item.getDescription().equalsIgnoreCase(model.getDescription())).findAny().orElse(null) != null)
			throw new RESTRuntimeException("answer.exists");

		// If found, then add answer to question and save it
		model.setQuestion(qentity);
		qentity.getAnswers().add(model);
		return RAnswerDTO.toDTO(adao.save(model));
	}

	public RAnswerDTO update(UAnswerDTO dto) {
		Answer		model		= dto.getModel();
		Answer		answer		= adao.findOne(model.getUuid());
		Question	question	= qdao.findOne(model.getQuestion().getUuid());

		// If no entity with given id
		if (answer == null)
			throw new RESTRuntimeException("answer.notfound");

		// If no question with given id
		if (question == null)
			throw new RESTRuntimeException("answer.question.notfound");

		Answer		exists 		= question.getAnswers().stream().filter(item -> item.getDescription().equalsIgnoreCase(model.getDescription())).findAny().orElse(null);
		if (exists != null)
			if (exists.getId() != model.getId())
				throw new RESTRuntimeException("answer.exists");

		answer.setDescription(model.getDescription());
		answer.setQuestion(question);
		return RAnswerDTO.toDTO(adao.save(answer));
	}
}
