package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.*;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.rest.dto.*;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VisitBO extends BaseBO<Visit, VisitDAO, VisitDTO, Long> {
	@Getter
	@Setter
	private		ChapterDAO		chapterDAO;

	@Getter
	@Setter
	private		FormDAO			formDAO;

	@Autowired
	protected VisitBO(VisitDAO dao, ChapterDAO chapterDAO, FormDAO formDAO) {
		super(dao);
		setChapterDAO(chapterDAO);
		setFormDAO(formDAO);
	}

	public VisitFrontDTO findOneDetailed(UUID uuid, UserDetails details) {
		return new VisitFrontDTO(getDao().findOne(uuid), true);
	}

	public Collection<VisitFrontDTO> findAllDetailed(UserDetails details) {
		return getDao().findAll().stream().map(visit -> new VisitFrontDTO(visit, false)).collect(Collectors.toList());
	}

	public Collection<VisitFrontDTO> findAllDetailed(Predicate predicate, UserDetails details) {
		return getDao().findAll(predicate).stream().map(visit -> new VisitFrontDTO(visit, false)).collect(Collectors.toList());
	}

	public Page<VisitFrontDTO> findAllDetailed(Pageable pageable, UserDetails details) {
		return getDao().findAll(pageable).map(visit -> new VisitFrontDTO(visit, false));
	}

	public Page<VisitFrontDTO> findAllDetailed(Predicate predicate, Pageable pageable, UserDetails details) {
		return getDao().findAll(predicate, pageable).map(visit -> new VisitFrontDTO(visit, false));
	}

	@Override
	public VisitDTO save(VisitDTO create, UserDetails account) {
		return null;
	}

	@Override
	public VisitDTO update(VisitDTO update, UserDetails account) {
		return null;
	}

	@Override
	public Collection<VisitDTO> save(Collection<VisitDTO> collection, UserDetails account) {
		return null;
	}

	@Override
	public Collection<VisitDTO> update(Collection<VisitDTO> collection, UserDetails details) {
		return null;
	}

	// region Pregnancy
	Visit setupVisit(Visit model, Pregnancy pregnancy, User agent) {
		model.setPregnancy(pregnancy);
		model.setChapter(getChapterDAO().findOne(model.getChapter().getUuid()));
		model.setForm(getFormDAO().findOne(model.getForm().getUuid()));
		return model;
	}

	Visit setupVisit(Visit visit, Visit model, Pregnancy pregnancy) {
		return null;
	}
	// endregion

	private List<Answer> setupAnswers(Collection<AnswerDTO> collection, Visit visit) {
		return null;
	}
}
