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
import java.util.stream.Collectors;

@Service
public class ChildBO extends BaseBO<Child, ChildDAO, ChildDTO, Long> {
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
		ArrayList<Child>		models		= new ArrayList<>();

		for (ChildDTO item : collection) {
			if (item.getUuid() == null) {
				models.add(persist(item, details));
			} else {
				models.add(patch(item, details));
			}
		}

		return getDao().save(models).stream().map(child -> new ChildDTO(child, false)).collect(Collectors.toList());
	}

	@Override
	public Collection<ChildDTO> update(Collection<ChildDTO> collection, UserDetails details) {
		return null;
	}

	protected Child persist(ChildDTO create, UserDetails details) {
		Child					model			= create.getModel();
		Responsible				mother			= create.getMother() != null? create.getMother().getUuid() != null? getResponsibleDAO().findOne(create.getMother().getUuid()) : create.getMother().getModel() : null;
		Collection<Responsible>	responsibles	= getResponsibleDAO().findAllIn(create.getResponsibles().stream().map(ResponsibleDTO::getUuid).collect(Collectors.toList()));
		Collection<Community>	communities		= getCommunityDAO().findAllIn(create.getResponsibles().stream().map(responsibleDTO -> responsibleDTO.getCommunity().getUuid()).collect(Collectors.toList()));
		Collection<City>		cities			= getCityDAO().findAllIn(create.getResponsibles().stream().map(responsibleDTO -> responsibleDTO.getCommunity().getCityUUID()).collect(Collectors.toList()));

		if (mother != null && mother.getMother() == null)
			throw new RESTException("not.mother");

		if (mother != null && mother.getId() != 0)
			model.getMother().setId(mother.getId());

		model.getResponsibles().forEach(responsibleModel -> {
			responsibleModel.setId(responsibles.stream().filter(community -> community.getUuid().compareTo(responsibleModel.getUuid()) == 0).findAny().orElse(responsibleModel).getId());
			responsibleModel.getCommunity().setId(communities.stream().filter(community -> community.getUuid().compareTo(responsibleModel.getCommunity().getUuid()) == 0).findAny().orElse(responsibleModel.getCommunity()).getId());
			responsibleModel.getCommunity().setCity(cities.stream().filter(city -> city.getUuid().compareTo(create.getResponsibles().stream().filter(responsibleDTO -> responsibleDTO.getCommunity().getCityUUID().compareTo(city.getUuid()) == 0).findAny().orElseThrow(() -> new RESTException("notfound")).getUuid()) == 0).findAny().orElseThrow(() -> new RESTException("notfound")));
		});

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

		model.getResponsibles().forEach(responsibleModel -> {
			responsibleModel.setId(responsibles.stream().filter(community -> community.getUuid().compareTo(responsibleModel.getUuid()) == 0).findAny().orElse(responsibleModel).getId());
			responsibleModel.getCommunity().setId(communities.stream().filter(community -> community.getUuid().compareTo(responsibleModel.getCommunity().getUuid()) == 0).findAny().orElse(responsibleModel.getCommunity()).getId());
			responsibleModel.getCommunity().setCity(cities.stream().filter(city -> city.getUuid().compareTo(update.getResponsibles().stream().filter(responsibleDTO -> responsibleDTO.getCommunity().getCityUUID().compareTo(city.getUuid()) == 0).findAny().orElseThrow(() -> new RESTException("notfound")).getUuid()) == 0).findAny().orElseThrow(() -> new RESTException("notfound")));
		});

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

		return child;
	}
}