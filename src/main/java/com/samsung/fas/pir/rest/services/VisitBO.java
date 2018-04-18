package com.samsung.fas.pir.rest.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.samsung.fas.pir.persistence.dao.*;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.rest.dto.VisitDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class VisitBO extends BaseBO<Visit, VisitDAO, VisitDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private		UserDAO		agentDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private 	MotherDAO	motherDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private	 	ChildDAO	childDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private 	ChapterDAO	chapterDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private 	FormDAO		formDAO;

	@Autowired
	protected VisitBO(VisitDAO dao) {
		super(dao);
	}

	@Override
	public VisitDTO save(VisitDTO create, UserDetails account) {
		Visit		model		= create.getModel();
		User		agent		= getAgentDAO().findOne(create.getAgentUUID());
		Chapter		chapter		= getChapterDAO().findOne(create.getChapterUUID());
		Form		form		= getFormDAO().findOne(create.getFormUUID());
		Mother		mother		= create.getMotherUUID() != null? getMotherDAO().findOne(create.getMotherUUID()) : null;
		Child		child		= create.getChildUUID() != null? getChildDAO().findOne(create.getChildUUID()) : null;

		model.setForm(form);
		model.setMother(mother);
		model.setChild(child);
		model.setChapter(chapter);
		model.setAgent(agent);

		return new VisitDTO(getDao().save(model), true);
	}

	@Override
	public VisitDTO update(VisitDTO update, UserDetails account) {
		Visit		model		= update.getModel();
		Visit		visit		= getDao().findOne(model.getUuid());

		visit.setNumber(model.getNumber());
		visit.setFamilyRating(model.getFamilyRating());
		visit.setAgentRating(model.getAgentRating());
		visit.setDoneAt(model.getDoneAt());
		visit.setDuration(model.getDuration());

		return new VisitDTO(getDao().save(visit), true);
	}
}
