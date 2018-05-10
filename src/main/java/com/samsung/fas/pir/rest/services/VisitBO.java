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
import sun.management.Agent;

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
	public Collection<VisitDTO> save(Collection<VisitDTO> collection, UserDetails account) {
		ArrayList<Visit> response = new ArrayList<>();

		for (VisitDTO item : collection) {
			Visit			model		= item.getModel();
			User			agent		= getUserDAO().findOne(item.getAgentUUID());
			Chapter			chapter		= getChapterDAO().findOne(item.getChapterUUID());
			Form			form		= item.getFormUUID() != null? getFormDAO().findOne(item.getFormUUID()) : null;
//			Responsible		responsible	= model.getResponsible() != null? Optional.ofNullable(getResponsibleDAO().findOne(model.getResponsible().getMobileId(), ((User) account).getId())).orElse(model.getResponsible().getUuid() != null? getResponsibleDAO().findOne(model.getResponsible().getUuid()) : null) : null;
//			Child			child		= model.getChild() != null? Optional.ofNullable(getChildDAO().findOne(model.getChild().getMobileId(), ((User) account).getId())).orElse(model.getChild().getUuid() != null? getChildDAO().findOne(model.getChild().getUuid()) : null) : null;

			model.setAgent(agent);
			model.setChapter(chapter);
			model.setForm(form);
			model.setAnswers(setupAnswers(item.getAnswers(), model));
			model.setResponsible(item.getResponsible() != null? getResponsibleBO().saveCollection(Collections.singletonList(item.getResponsible()), account).iterator().next() : null);
			model.setChild(item.getChild() != null? getChildBO().saveCollection(Collections.singletonList(item.getChild()), account).iterator().next() : null);


//			if (responsible != null)
//				model.setResponsible(responsible);
//
//			if (child != null)
//				model.setChild(child);
//
//			if (responsible == null && child == null) {
//				if (create.getChild() != null) {
//					create.getChild().getResponsibles().stream().filter(r -> r.getTempID() == create.getChild().getMother().getTempID()).findAny().ifPresent(remove -> create.getChild().getResponsibles().remove(remove));
//
//					if (create.getChild().getUuid() == null) {
//						Optional<Visit> visit = response.stream().filter(v -> v.getChild() != null && v.getChild().getMobileId() == model.getChild().getMobileId()).findAny();
//						if (visit.isPresent()) {
//							model.setChild(visit.get().getChild());
//						} else {
//							model.setChild(getChildBO().persist(create.getChild(), details));
//						}
//					} else {
//						model.setChild(getChildBO().patch(create.getChild(), details));
//					}
//
//					response.stream().filter(v -> (v.getChild() != null && v.getChild().getMother() != null) && v.getChild().getMother().getMobileId() == create.getChild().getMother().getTempID()).findAny().ifPresent(item -> model.getChild().setMother(item.getResponsible()));
//				}
//
//				if (create.getResponsible() != null) {
//					if (create.getResponsible().getUuid() == null) {
//						Optional<Visit> visit = response.stream().filter(v -> v.getResponsible() != null && v.getResponsible().getMobileId() == model.getResponsible().getMobileId()).findAny();
//						if (visit.isPresent()) {
//							model.setResponsible(visit.get().getResponsible());
//						} else {
//							model.setResponsible(getResponsibleBO().create(create.getResponsible(), details));
//						}
//					} else {
//						model.setResponsible(getResponsibleBO().patch(create.getResponsible(), details));
//					}
//
//					response.stream().filter(v -> (v.getChild() != null && v.getChild().getMother() != null) && v.getChild().getMother().getMobileId() == create.getResponsible().getTempID()).findAny().ifPresent(item -> model.setResponsible(item.getChild().getMother()));
//				}
//			}
//
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
				return getResponsibleBO().create(create, details);
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
