package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.AnswerDAO;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.FormDAO;
import com.samsung.fas.pir.persistence.dao.VisitDAO;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.VisitDTO;
import com.samsung.fas.pir.rest.dto.VisitFrontDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VisitBO extends BaseBO<Visit, VisitDAO, VisitDTO, Long> {
	@Getter
	@Setter
	private		ChapterDAO		chapterDAO;

	@Getter
	@Setter
	private		FormDAO			formDAO;

	@Getter
	@Setter
	private		AnswerDAO		answerDAO;

	@Getter
	@Setter
	private 	AnswerBO		answerBO;

	@Autowired
	protected VisitBO(VisitDAO dao, ChapterDAO chapterDAO, FormDAO formDAO, AnswerDAO answerDAO, AnswerBO answerBO) {
		super(dao);
		setChapterDAO(chapterDAO);
		setFormDAO(formDAO);
		setAnswerDAO(answerDAO);
		setAnswerBO(answerBO);
	}

	public VisitFrontDTO findOneDetailed(UUID uuid, Device device, UserDetails details) {
		return new VisitFrontDTO(getDao().findOne(uuid), device, true);
	}

	public Collection<VisitFrontDTO> findAllDetailed(Device device, UserDetails details) {
		return getDao().findAll().stream().map(visit -> new VisitFrontDTO(visit, device, false)).collect(Collectors.toList());
	}

	public Collection<VisitFrontDTO> findAllDetailed(Predicate predicate, Device device, UserDetails details) {
		return getDao().findAll(predicate).stream().map(visit -> new VisitFrontDTO(visit, device, false)).collect(Collectors.toList());
	}

	public Page<VisitFrontDTO> findAllDetailed(Pageable pageable, Device device, UserDetails details) {
		return getDao().findAll(pageable).map(visit -> new VisitFrontDTO(visit, device, false));
	}

	public Page<VisitFrontDTO> findAllDetailed(Predicate predicate, Pageable pageable, Device device, UserDetails details) {
		return getDao().findAll(predicate, pageable).map(visit -> new VisitFrontDTO(visit, device, false));
	}

	@Override
	public VisitDTO save(VisitDTO create, Device device, UserDetails account) {
		return null;
	}

	@Override
	public VisitDTO update(VisitDTO update, Device device, UserDetails account) {
		return null;
	}

	@Override
	public Collection<VisitDTO> save(Collection<VisitDTO> collection, Device device, UserDetails account) {
		return null;
	}

	@Override
	public Collection<VisitDTO> update(Collection<VisitDTO> collection, Device device, UserDetails details) {
		return null;
	}

	// region Pregnancy
	Visit setupVisit(Visit model, Pregnancy pregnancy, User agent) {
		model.setPregnancy(pregnancy);
		model.setChapter(getChapterDAO().findOne(model.getChapter().getUuid()));
		model.setForm(getFormDAO().findOne(model.getForm().getUuid()));
		model.setAgent(agent);
		if (model.getAnswers() != null) {
			model.setAnswers(setupAnswers(model, model.getAnswers()));
		}
		return model;
	}

	Visit setupVisit(Visit visit, Visit model, Pregnancy pregnancy, User agent) {
		visit.setForm(getFormDAO().findOne(model.getForm().getUuid()));
		visit.setChapter(getChapterDAO().findOne(model.getChapter().getUuid()));
		visit.setPregnancy(pregnancy);
		visit.setDuration(model.getDuration());
		visit.setNumber(model.getNumber());
		visit.setDoneAt(model.getDoneAt());
		visit.setErased(model.isErased());
		visit.setAgent(agent);
		if (model.getAnswers() != null) {
			visit.setAnswers(setupAnswers(visit, model.getAnswers()));
		}
		return visit;
	}
	// endregion

	// region Child
	Visit setupVisit(Visit model, Child child, User agent) {
		model.setChild(child);
		model.setChapter(getChapterDAO().findOne(model.getChapter().getUuid()));
		model.setForm(model.getForm().getUuid() != null? getFormDAO().findOne(model.getForm().getUuid()) : null);
		model.setAgent(agent);
		if (model.getAnswers() != null) {
			model.setAnswers(setupAnswers(model, model.getAnswers()));
		}
		return model;
	}

	Visit setupVisit(Visit visit, Visit model, Child child, User agent) {
		visit.setForm(model.getForm().getUuid() != null? getFormDAO().findOne(model.getForm().getUuid()) : null);
		visit.setChapter(getChapterDAO().findOne(model.getChapter().getUuid()));
		visit.setChild(child);
		visit.setDuration(model.getDuration());
		visit.setNumber(model.getNumber());
		visit.setDoneAt(model.getDoneAt());
		visit.setErased(model.isErased());
		visit.setAgent(agent);
		if (model.getAnswers() != null) {
			visit.setAnswers(setupAnswers(visit, model.getAnswers()));
		}
		return visit;
	}
	// endregion

	private Collection<Answer> setupAnswers(Visit visit, Collection<Answer> collection) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Answer		answer		= uuid != null? getAnswerDAO().findOne(uuid) : null;
			if (answer != null) {
				return getAnswerBO().setupAnswer(answer, item);
			} else {
				return getAnswerBO().setupAnswer(item, visit);
			}
		}).collect(Collectors.toList());
	}
}
