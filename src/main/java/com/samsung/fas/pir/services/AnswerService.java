package com.samsung.fas.pir.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.dao.AnswerDAO;
import com.samsung.fas.pir.dao.QuestionDAO;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.models.dto.answer.CAnswerDTO;
import com.samsung.fas.pir.models.dto.answer.RAnswerDTO;
import com.samsung.fas.pir.models.dto.answer.UAnswerDTO;
import com.samsung.fas.pir.models.entity.Answer;
import com.samsung.fas.pir.models.entity.Question;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {
	private	AnswerDAO	adao;
	private	QuestionDAO	qdao;

	@Autowired
	public AnswerService(AnswerDAO adao, QuestionDAO qdao) {
		this.adao 	= adao;
		this.qdao	= qdao;
	}

	public RAnswerDTO findOne(String id) {
		return RAnswerDTO.toDTO(adao.findOne(IDCoder.decodeLong(id)));
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

	public void delete(String id) {
		adao.delete(IDCoder.decodeLong(id));
	}

	public Void save(CAnswerDTO dto) {
		Answer		model		= dto.getModel();
		Question	qentity		= qdao.findOne(model.getQuestion().getId());

		// If no question with given id
		if (qentity == null)
			throw new RESTRuntimeException("answer.question.notfound");

		// If same answer exists
		if (qentity.getAnswers().stream().filter(item -> item.getDescription().equalsIgnoreCase(model.getDescription())).findAny().orElse(null) != null)
			throw new RESTRuntimeException("answer.exists");

		// If found, then add answer to question and save it
		model.setQuestion(qentity);
		qentity.getAnswers().add(model);
//		adao.save(model);
		qdao.save(qentity);
		return null;
	}

	public Void update(UAnswerDTO dto) {
		Answer		model		= dto.getModel();
		Answer		aentity		= adao.findOne(model.getId());
		Answer		exists		= null;
		Question	qentity		= qdao.findOne(model.getQuestion().getId());

		// If no entity with given id
		if (aentity == null)
			throw new RESTRuntimeException("answer.notfound");

		// If no question with given id
		if (qentity == null)
			throw new RESTRuntimeException("answer.question.notfound");

		exists = qentity.getAnswers().stream().filter(item -> item.getDescription().equalsIgnoreCase(model.getDescription())).findAny().orElse(null);
		if (exists != null)
			if (exists.getId() != model.getId())
				throw new RESTRuntimeException("answer.exists");

		model.setQuestion(qentity);
		adao.save(model);
		return null;
	}
}
