package com.samsung.fas.pir.rest.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.*;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.rest.dto.ChildDTO;
import com.samsung.fas.pir.rest.dto.ResponsibleDTO;
import com.samsung.fas.pir.rest.dto.VisitDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VisitBO extends BaseBO<Visit, VisitDAO, VisitDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private		UserDAO			userDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private 	ChapterDAO		chapterDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private 	FormDAO			formDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private 	ResponsibleDAO	responsibleDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private	 	ChildDAO		childDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private 	CommunityDAO	communityDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private 	ResponsibleBO	responsibleBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@JsonIgnore}))
	private 	ChildBO			childBO;

	@Autowired
	protected VisitBO(VisitDAO dao) {
		super(dao);
	}

	@Override
	public VisitDTO save(VisitDTO create, UserDetails account) {
		Visit		model		= create.getModel();
		User		agent		= getUserDAO().findOne(create.getAgentUUID());
		Chapter		chapter		= getChapterDAO().findOne(create.getChapterUUID());
		Form		form		= getFormDAO().findOne(create.getFormUUID());
		Child		child		= setup(create.getChild(), account);
		Responsible	responsible	= setup(create.getResponsible(), account);

		model.setChild(child);
		model.setResponsible(responsible);
		model.setForm(form);
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

	private Responsible setup(ResponsibleDTO create, UserDetails details) {
		if (create != null) {
			if (create.getUuid() == null) {
				return getResponsibleBO().persist(create, details);
			}
			return getResponsibleDAO().findOne(create.getUuid());
		}
		return null;
	}

	private Child setup(ChildDTO create, UserDetails details) {
		if (create != null) {
			if (create.getUuid() == null) {
				return getChildBO().persist(create, details);
			}
			return getChildDAO().findOne(create.getUuid());
		}
		return null;
	}
}
