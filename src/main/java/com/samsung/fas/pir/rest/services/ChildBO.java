package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.ChildDAO;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.Child;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.persistence.models.Responsible;
import com.samsung.fas.pir.rest.dto.ChildDTO;
import com.samsung.fas.pir.rest.dto.ResponsibleDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChildBO extends BaseBO<Child, ChildDAO, ChildDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		ResponsibleBO		responsibleBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		ResponsibleDAO		responsibleDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		CommunityDAO		communityDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		CityDAO				cityDAO;

	@Autowired
	public ChildBO(ChildDAO dao) {
		super(dao);
	}

	@Override
	public ChildDTO save(ChildDTO create, UserDetails account) {
		return new ChildDTO(getDao().save(persist(create, account)), true);
	}

	@Override
	public ChildDTO update(ChildDTO update, UserDetails account) {
		return new ChildDTO(getDao().save(patch(update, account)), true);
	}

	@Override
	public Collection<ChildDTO> save(Collection<ChildDTO> collection, UserDetails details) {
		ArrayList<Child>		response		= new ArrayList<>();

		for (ChildDTO item : collection) {
			if (item.getUuid() == null) {
				try {
					response.add(getDao().save(persist(item, details)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					response.add(getDao().save(patch(item, details)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return response.stream().map(child -> new ChildDTO(child, true)).collect(Collectors.toList());
	}

	@Override
	public Collection<ChildDTO> update(Collection<ChildDTO> collection, UserDetails details) {
		return null;
	}

	protected Child persist(ChildDTO create, UserDetails details) {
		Child					model			= create.getModel();
		Responsible				mother			= create.getMother() != null? create.getMother().getUuid() != null? getResponsibleDAO().findOne(create.getMother().getUuid()) : create.getMother().getModel() : null;
		City					city			= mother != null && create.getMother().getCommunity() != null? getCityDAO().findOne(create.getMother().getCommunity().getCityUUID()) : null;
		Community				community		= city != null && create.getMother().getCommunity() != null? create.getMother().getCommunity().getUuid() != null? getCommunityDAO().findOne(create.getMother().getCommunity().getUuid()) : create.getMother().getCommunity().getModel() : null;
		Collection<Responsible>	responsibles	= getResponsibleDAO().findAllByMobileIdIn(create.getResponsibles().stream().map(ResponsibleDTO::getTempID).collect(Collectors.toList()));
		Collection<Responsible> rmodels			= getResponsibleBO().internalSaving(create.getResponsibles(), details);

		if (mother != null && mother.getMother() == null)
			throw new RESTException("not.mother");

		if (mother != null && mother.getId() != 0)
			model.getMother().setId(mother.getId());

		model.setMother(mother);
		model.setResponsibles(responsibles);

		responsibles.forEach(responsible -> {
			rmodels.stream().filter(dto -> dto.getMobileId() == responsible.getMobileId()).findAny().ifPresent(rmodels::remove) ;
		});

		model.getResponsibles().addAll(rmodels);
		model.getResponsibles().forEach(responsible -> {
			if (responsible.getChildren() == null)
				responsible.setChildren(new ArrayList<>());
			responsible.getChildren().add(model);
		});

		if (community != null)
			model.getMother().setCommunity(community);

		if (city != null)
			model.getMother().getCommunity().setCity(city);

		return model;
	}

	protected Child patch(ChildDTO update, UserDetails details) {
		Child					model			= update.getModel();
		Child					child			= getDao().findOne(model.getUuid());
		Responsible				mother			= update.getMother() != null? update.getMother().getUuid() != null? getResponsibleDAO().findOne(update.getMother().getUuid()) : update.getMother().getModel() : null;
		Collection<Responsible>	responsibles	= getResponsibleDAO().findAllIn(update.getResponsibles().stream().map(ResponsibleDTO::getUuid).collect(Collectors.toList()));
		Collection<Community>	communities		= getCommunityDAO().findAllIn(update.getResponsibles().stream().map(responsibleDTO -> responsibleDTO.getCommunity().getUuid()).collect(Collectors.toList()));
		Collection<City>		cities			= getCityDAO().findAllIn(update.getResponsibles().stream().map(responsibleDTO -> responsibleDTO.getCommunity().getCityUUID()).collect(Collectors.toList()));

		if (mother != null && mother.getId() != 0)
			model.getMother().setId(mother.getId());

		update.getResponsibles().forEach(rcreate -> {
			Responsible responsible = getResponsibleDAO().findOneByMobileId(rcreate.getTempID());
			if (responsible == null) {
				model.getResponsibles().add(getResponsibleBO().persist(rcreate, details));
			}
		});

		responsibles.forEach(responsible -> {
			model.getResponsibles().stream().filter(dto -> dto.getMobileId() == responsible.getMobileId()).findAny().ifPresent(model.getResponsibles()::remove);
		});

		child.setMobileId(model.getMobileId());
		child.setName(model.getName());
		child.setFatherName(model.getFatherName());
		child.setGender(model.getGender());
		child.setHasCivilRegistration(model.isHasCivilRegistration());
		child.setCivilRegistrationJustificative(model.getCivilRegistrationJustificative());
		child.setHasEducationDifficulty(model.isHasEducationDifficulty());
		child.setEducationDifficultySpecification(model.getEducationDifficultySpecification());
		child.setPrematureBorn(model.isPrematureBorn());
		child.setBornWeek(model.getBornWeek());
		child.setWhoTakeCare(model.getWhoTakeCare());
		child.setPlaysWithWho(model.getPlaysWithWho());
		child.setMensalWeight(model.isMensalWeight());
		child.setSocialEducationalPrograms(model.isSocialEducationalPrograms());
		child.setVacinationUpToDate(model.isVacinationUpToDate());
		child.setRelationDifficulties(model.isHasEducationDifficulty());
		child.setMother(mother);

		if (child.getResponsibles() == null)
			child.setResponsibles(new ArrayList<>());

		child.getResponsibles().clear();
		child.getResponsibles().addAll(model.getResponsibles());
		child.getResponsibles().addAll(responsibles);

		return child;
	}
}