package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.ConclusionDAO;
import com.samsung.fas.pir.persistence.dao.QuestionDAO;
import com.samsung.fas.pir.persistence.models.Conclusion;
import com.samsung.fas.pir.persistence.models.Question;
import com.samsung.fas.pir.rest.dto.QuestionDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuestionService extends BService<Question, QuestionDTO, QuestionDAO, Long> {
	private	final	ConclusionDAO	cdao;
	private	final	ChapterDAO		chdao;

	@Autowired
	public QuestionService(QuestionDAO dao, ConclusionDAO cdao, ChapterDAO chdao) {
		super(dao, Question.class, QuestionDTO.class);
		this.cdao 	= cdao;
		this.chdao	= chdao;
	}

	@Override
	public Long delete(UUID id) {
		Question	question	= dao.findOne(id);
		Conclusion 	conclusion	= question.getConclusion();
		Object		deleted		= dao.delete(question.getUuid());

		if (conclusion.getQuestions().size() == 0) {
			chdao.invalidateOne(question.getConclusion().getChapter().getId());
		}

		return deleted != null? 0L : -1L;
	}

	@Override
	public QuestionDTO save(QuestionDTO create, UserDetails account) {
		Question		model			= create.getModel();
		Conclusion		conclusion		= cdao.findOne(IDCoder.decode(create.getConclusionID()));

		model.setConclusion(conclusion);
		conclusion.getQuestions().add(model);

		return new QuestionDTO(dao.save(model), true);
	}

	@Override
	public QuestionDTO update(QuestionDTO update, UserDetails account) {
		Question		model		= update.getModel();
		Question		question	= dao.findOne(model.getUuid());

		question.setDescription(model.getDescription());
		question.setType(model.getType());

		return new QuestionDTO(dao.save(question), true);
	}
}
