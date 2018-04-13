package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.AnswerDAO;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.QuestionDAO;
import com.samsung.fas.pir.persistence.models.Answer;
import com.samsung.fas.pir.persistence.models.Question;
import com.samsung.fas.pir.rest.dto.AnswerDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AnswerService extends BService<Answer, AnswerDTO, AnswerDAO, Long> {
	private	final	QuestionDAO	qdao;
	private	final	ChapterDAO	cdao;

	@Autowired
	public AnswerService(AnswerDAO dao, QuestionDAO qdao, ChapterDAO cdao) {
		super(dao, Answer.class, AnswerDTO.class);
		this.qdao 	= qdao;
		this.cdao	= cdao;
	}

	@Override
	public Long delete(UUID id) {
		Answer		answer		= dao.findOne(id);
		Question 	question	= answer.getQuestion();
		Object		deleted		= dao.delete(answer.getUuid());

		if (question.getAnswers().size() == 0) {
			cdao.invalidateOne(question.getConclusion().getChapter().getId());
		}

		return deleted != null? 0L : -1L;
	}

	@Override
	public AnswerDTO save(AnswerDTO create, UserDetails account) {
		Answer		model		= create.getModel();
		Question	question	= qdao.findOne(IDCoder.decode(create.getQuestionID()));

		model.setQuestion(question);
		question.getAnswers().add(model);
		return new AnswerDTO(dao.save(model), true);
	}

	@Override
	public AnswerDTO update(AnswerDTO update, UserDetails account) {
		Answer		model		= update.getModel();
		Answer		answer		= dao.findOne(model.getUuid());

		answer.setDescription(model.getDescription());
		answer.setType(model.getType());

		return new AnswerDTO(dao.save(answer), true);
	}
}
