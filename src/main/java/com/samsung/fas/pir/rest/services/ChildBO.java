package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.ChildDAO;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.rest.dto.ChildDTO;
import com.samsung.fas.pir.rest.dto.ResponsibleDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChildBO extends BaseBO<Child, ChildDAO, ChildDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		ResponsibleBO		responsibleBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		CommunityBO			communityBO;

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
	public Collection<ChildDTO> save(Collection<ChildDTO> collection, UserDetails account) {
		ArrayList<Child>		response		= new ArrayList<>();

		for (ChildDTO item : collection) {
			Child		model		= item.getModel();
			Child		child		= Optional.ofNullable(getDao().findOne(item.getTempID(), ((Account) account).getUser().getId())).orElse(model.getUuid() != null? getDao().findOne(model.getUuid()) : null);
			Responsible	mother		= Optional.ofNullable(item.getMother() != null? getResponsibleDAO().findOne(item.getMother().getTempID(), ((Account) account).getUser().getId()) : null).orElse(model.getMother() != null && model.getMother().getUuid() != null? getResponsibleDAO().findOne(model.getMother().getUuid()) : null);

			if (child == null) {
				if (mother != null) {
					model.setMother(item.getMother() != null? getResponsibleBO().patch(item.getMother(), account) : null);
					model.setAgent(((Account) account).getUser());
					model.setResponsibles(getResponsibleBO().saveCollection(item.getResponsibles(), account));
					response.add(getDao().save(model));
				} else {
					model.setMother(item.getMother() != null? getResponsibleBO().create(item.getMother(), account) : null);
					model.setAgent(((Account) account).getUser());
					model.setResponsibles(getResponsibleBO().saveCollection(item.getResponsibles(), account));
					response.add(getDao().save(model));
				}
			} else {
				if (mother != null) {
					child.setAgent(((Account) account).getUser());
					child.getResponsibles().clear();
					child.getResponsibles().addAll(getResponsibleBO().saveCollection(item.getResponsibles(), account));
					response.add(getDao().save(setupChild(child, model, item.getMother() != null? getResponsibleBO().patch(item.getMother(), account) : null)));
				} else {
					child.setAgent(((Account) account).getUser());
					child.getResponsibles().clear();
					child.getResponsibles().addAll(getResponsibleBO().saveCollection(item.getResponsibles(), account));
					response.add(getDao().save(setupChild(child, model, item.getMother() != null? getResponsibleBO().create(item.getMother(), account) : null)));
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
		Collection<Responsible> rmodels			= getResponsibleBO().saveCollection(create.getResponsibles(), details);

		return model;
	}

	protected Child patch(ChildDTO update, UserDetails details) {
		Child					model			= update.getModel();
		Child					child			= getDao().findOne(model.getUuid());
		Responsible				mother			= update.getMother() != null? update.getMother().getUuid() != null? getResponsibleDAO().findOne(update.getMother().getUuid()) : update.getMother().getModel() : null;
		Collection<Responsible>	responsibles	= getResponsibleDAO().findAllIn(update.getResponsibles().stream().map(ResponsibleDTO::getUuid).collect(Collectors.toList()));
		Collection<Community>	communities		= getCommunityDAO().findAllIn(update.getResponsibles().stream().map(responsibleDTO -> responsibleDTO.getCommunity().getUuid()).collect(Collectors.toList()));
		Collection<City>		cities			= getCityDAO().findAllIn(update.getResponsibles().stream().map(responsibleDTO -> responsibleDTO.getCommunity().getCityUUID()).collect(Collectors.toList()));

		return child;
	}

	protected Child setupChild(Child child, Child model, Responsible mother) {
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

		return child;
	}
}