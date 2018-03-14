package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.ConclusionDAO;
import com.samsung.fas.pir.persistence.dao.QuestionDAO;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.persistence.models.entity.Question;
import com.samsung.fas.pir.rest.dto.QuestionDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService extends BService<Question, QuestionDTO, QuestionDAO, Long> {
	private ConclusionDAO	cdao;
	private ChapterDAO		chdao;

	@Autowired
	public QuestionService(QuestionDAO dao, ConclusionDAO cdao, ChapterDAO chdao) {
		super(dao, Question.class, QuestionDTO.class);
		this.cdao 	= cdao;
		this.chdao	= chdao;
	}

	@Override
	public Long delete(UUID id) {
		Question	question	= Optional.ofNullable(dao.findOne(id)).orElseThrow(() -> new RESTRuntimeException("answer.notfound"));
		Conclusion	conclusion	= question.getConclusion();
		Object		deleted		= dao.delete(question.getUuid());

		if (conclusion.getQuestions().size() == 0) {
			chdao.invalidateOne(question.getConclusion().getChapter().getId());
		}

		return deleted != null? 0L : -1L;
	}

	@Override
	public QuestionDTO save(QuestionDTO create, UserDetails account) {
		Question		model			= create.getModel();
		UUID			conclusionID	= Optional.ofNullable(create.getConclusionID() != null && !create.getConclusionID().trim().isEmpty()? IDCoder.decode(create.getConclusionID()) : null).orElseThrow(() -> new RESTRuntimeException("conclusion.id.missing"));
		Conclusion		conclusion		= Optional.ofNullable(cdao.findOne(conclusionID)).orElseThrow(() -> new RESTRuntimeException("conclusion.notfound"));

		model.setConclusion(conclusion);
		conclusion.getQuestions().add(model);

		return new QuestionDTO(dao.save(model), true);
	}

	@Override
	public QuestionDTO update(QuestionDTO update, UserDetails account) {
		Question		model		= update.getModel();
		Question		question	= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("question.notfound"));

		question.setDescription(model.getDescription());
		question.setType(model.getType());

		return new QuestionDTO(dao.save(question), true);
	}
}
