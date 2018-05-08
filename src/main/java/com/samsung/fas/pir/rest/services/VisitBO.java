package com.samsung.fas.pir.rest.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTException;
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
import java.util.stream.Stream;

@Service
public class VisitBO extends BaseBO<Visit, VisitDAO, VisitDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		UserDAO			userDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private 	ChapterDAO		chapterDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private 	FormDAO			formDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private 	ResponsibleDAO	responsibleDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private	 	ChildDAO		childDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__(@Autowired))
	private 	MotherDAO		motherDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__(@Autowired))
	private		AlternativeDAO	alternativeDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__(@Autowired))
	private		QuestionDAO		questionDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private 	CommunityDAO	communityDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private 	ResponsibleBO	responsibleBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private 	ChildBO			childBO;

	@Autowired
	protected VisitBO(VisitDAO dao) {
		super(dao);
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

	@Override
	public Collection<VisitDTO> save(Collection<VisitDTO> collection, UserDetails details) {
		ArrayList<Visit> response = new ArrayList<>();

		for (VisitDTO create : collection) {
			Visit			model		= create.getModel();
			User			agent		= getUserDAO().findOne(create.getAgentUUID());
			Chapter			chapter		= getChapterDAO().findOne(create.getChapterUUID());
			Form			form		= create.getFormUUID() != null? getFormDAO().findOne(create.getFormUUID()) : null;

			model.setAgent(agent);
			model.setChapter(chapter);
			model.setForm(form);
			model.setAnswers(setupAnswers(create.getAnswers(), model));

			if (create.getChild() != null) {
				if (create.getChild().getUuid() == null) {
					Optional<Visit> visit = response.stream().filter(v -> v.getChild() != null && v.getChild().getTempID() == model.getChild().getTempID()).findAny();
					if (visit.isPresent()) {
						model.setChild(visit.get().getChild());
					} else {
						model.setChild(getChildBO().persist(create.getChild(), details));
					}
				} else {
					model.setChild(getChildBO().patch(create.getChild(), details));
				}
			}

			if (create.getResponsible() != null) {
				if (create.getResponsible().getUuid() == null) {
					Optional<Visit> visit = response.stream().filter(v -> v.getResponsible() != null && v.getResponsible().getTempID() == model.getResponsible().getTempID()).findAny();
					if (visit.isPresent()) {
						model.setResponsible(visit.get().getResponsible());
					} else {
						model.setResponsible(getResponsibleBO().persist(create.getResponsible(), details));
					}
				} else {
					model.setResponsible(getResponsibleBO().patch(create.getResponsible(), details));
				}
			}

			response.add(getDao().save(model));
		}

		return response.stream().map(visit -> new VisitDTO(visit, true)).collect(Collectors.toList());
	}

	@Override
	public Collection<VisitDTO> update(Collection<VisitDTO> collection, UserDetails details) {
		return null;
	}

	private List<Answer> setupAnswers(Collection<AnswerDTO> collection, Visit visit) {
		List<Answer>	answers		= new ArrayList<>();

		for (AnswerDTO createAnswer : collection) {
			Answer		model		= createAnswer.getModel();
			Question 	question	= createAnswer.getQuestionUUID() != null? getQuestionDAO().findOne(createAnswer.getQuestionUUID()) : null;
			Alternative alternative	= createAnswer.getAlternativeUUID() != null? getAlternativeDAO().findOne(createAnswer.getAlternativeUUID()) : null;

			model.setAlternative(alternative);
			model.setQuestion(question);
			model.setVisit(visit);
			answers.add(model);
		}

		return answers;
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
