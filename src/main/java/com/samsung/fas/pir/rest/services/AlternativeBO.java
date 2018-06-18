package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.AlternativeDAO;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.QuestionDAO;
import com.samsung.fas.pir.persistence.models.Alternative;
import com.samsung.fas.pir.persistence.models.Question;
import com.samsung.fas.pir.rest.dto.AlternativeDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class AlternativeBO extends BaseBO<Alternative, AlternativeDAO, AlternativeDTO, Long> {
	private	final	QuestionDAO	qdao;
	private	final	ChapterDAO	cdao;

	@Autowired
	public AlternativeBO(AlternativeDAO dao, QuestionDAO qdao, ChapterDAO cdao) {
		super(dao);
		this.qdao 	= qdao;
		this.cdao	= cdao;
	}

	@Override
	public Long delete(UUID id) {
		Alternative	alternative	= getDao().findOne(id);
		Question 	question	= alternative.getQuestion();
		Object		deleted		= getDao().delete(alternative.getUuid());

		if (question.getAlternatives().size() == 0) {
			cdao.invalidateOne(question.getConclusion().getChapter().getId());
		}

		return deleted != null? 0L : -1L;
	}

	@Override
	public AlternativeDTO save(AlternativeDTO create, Device device, UserDetails account) {
		Alternative model		= create.getModel();
		Question	question	= qdao.findOne(create.getQuestionUUID());

		model.setQuestion(question);
		question.getAlternatives().add(model);
		return new AlternativeDTO(getDao().save(model), true);
	}

	@Override
	public AlternativeDTO update(AlternativeDTO update, Device device, UserDetails account) {
		Alternative	model		= update.getModel();
		Alternative	alternative = getDao().findOne(model.getUuid());

		alternative.setDescription(model.getDescription());
		alternative.setType(model.getType());

		return new AlternativeDTO(getDao().save(alternative), true);
	}

	@Override
	public Collection<AlternativeDTO> save(Collection<AlternativeDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<AlternativeDTO> update(Collection<AlternativeDTO> update, Device device, UserDetails details) {
		return null;
	}
}
