package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.AnswerDAO;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.QuestionDAO;
import com.samsung.fas.pir.persistence.models.entity.Answer;
import com.samsung.fas.pir.persistence.models.entity.Question;
import com.samsung.fas.pir.rest.dto.answer.CRUAnswerDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AnswerService extends BService<Answer,CRUAnswerDTO, AnswerDAO, Long> {
	private	QuestionDAO	qdao;
	private	ChapterDAO	cdao;

	@Autowired
	public AnswerService(AnswerDAO dao, QuestionDAO qdao, ChapterDAO cdao) {
		super(dao, Answer.class, CRUAnswerDTO.class);
		this.qdao 	= qdao;
		this.cdao	= cdao;
	}

	@Override
	public Long delete(UUID id) {
		Answer		answer		= Optional.ofNullable(dao.findOne(id)).orElseThrow(() -> new RESTRuntimeException("answer.notfound"));
		Question	question	= answer.getQuestion();
		Object		deleted		= dao.delete(answer.getUuid());

		if (question.getAnswers().size() == 0) {
			cdao.invalidateOne(question.getConclusion().getChapter().getId());
		}

		return deleted != null? 0L : -1L;
	}

	@Override
	public CRUAnswerDTO save(CRUAnswerDTO create, UserDetails account) {
		Answer		model		= create.getModel();
		Question	question	= Optional.ofNullable(qdao.findOne(create.getQuestionID() != null && !create.getQuestionID().trim().isEmpty()? IDCoder.decode(create.getQuestionID()) : null)).orElseThrow(() -> new RESTRuntimeException("question.notfound"));

		model.setQuestion(question);
		question.getAnswers().add(model);
		return new CRUAnswerDTO(dao.save(model), true);
	}

	@Override
	public CRUAnswerDTO update(CRUAnswerDTO update, UserDetails account) {
		Answer		model		= update.getModel();
		Answer		answer		= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("answer.notfound"));

		answer.setDescription(model.getDescription());
		return new CRUAnswerDTO(dao.save(answer), true);
	}
}
