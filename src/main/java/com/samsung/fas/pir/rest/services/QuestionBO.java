package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.ConclusionDAO;
import com.samsung.fas.pir.persistence.dao.QuestionDAO;
import com.samsung.fas.pir.persistence.models.Conclusion;
import com.samsung.fas.pir.persistence.models.Question;
import com.samsung.fas.pir.rest.dto.QuestionDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class QuestionBO extends BaseBO<Question, QuestionDAO, QuestionDTO, Long> {
	private	final	ConclusionDAO	cdao;
	private	final	ChapterDAO		chdao;

	@Autowired
	public QuestionBO(QuestionDAO dao, ConclusionDAO cdao, ChapterDAO chdao) {
		super(dao);
		this.cdao 	= cdao;
		this.chdao	= chdao;
	}

	@Override
	public Long delete(UUID id) {
		Question	question	= getDao().findOne(id);
		Conclusion 	conclusion	= question.getConclusion();
		Object		deleted		= getDao().delete(question.getUuid());

		if (conclusion.getQuestions().size() == 0) {
			chdao.invalidateOne(question.getConclusion().getChapter().getId());
		}

		return deleted != null? 0L : -1L;
	}

	@Override
	public QuestionDTO save(QuestionDTO create, Device device, UserDetails account) {
		Question		model			= create.getModel();
		Conclusion		conclusion		= cdao.findOne(create.getConclusionUUID());

		model.setConclusion(conclusion);
		conclusion.getQuestions().add(model);

		return new QuestionDTO(getDao().save(model), true);
	}

	@Override
	public QuestionDTO update(QuestionDTO update, Device device, UserDetails account) {
		Question		model		= update.getModel();
		Question		question	= getDao().findOne(model.getUuid());

		question.setDescription(model.getDescription());
		question.setType(model.getType());

		return new QuestionDTO(getDao().save(question), true);
	}

	@Override
	public Collection<QuestionDTO> save(Collection<QuestionDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<QuestionDTO> update(Collection<QuestionDTO> update, Device device, UserDetails details) {
		return null;
	}
}
