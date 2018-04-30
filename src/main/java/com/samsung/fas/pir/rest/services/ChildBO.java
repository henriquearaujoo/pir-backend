package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.ChildDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.Child;
import com.samsung.fas.pir.persistence.models.Responsible;
import com.samsung.fas.pir.rest.dto.ChildDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ChildBO extends BaseBO<Child, ChildDAO, ChildDTO, Long> {
	private	final ResponsibleDAO rdao;

	@Autowired
	public ChildBO(ChildDAO dao, ResponsibleDAO rdao) {
		super(dao);
		this.rdao	= rdao;
	}

	@Override
	public ChildDTO save(ChildDTO create, UserDetails account) {
		return new ChildDTO(getDao().save(persist(create, account)), true);
	}

	@Override
	public ChildDTO update(ChildDTO update, UserDetails account) {
		Child		model		= update.getModel();
		Child		child		= getDao().findOne(model.getUuid());
		Responsible responsible	= update.getResponsible() != null? update.getResponsible().getUuid() != null? rdao.findOne(update.getResponsible().getUuid()) : update.getResponsible().getModel() : child.getResponsible() != null? child.getResponsible() : rdao.findOne(update.getResponsibleUUID());
		Responsible	mother		= update.getMother() != null? update.getMother().getUuid() != null? rdao.findOne(update.getMother().getUuid()) : update.getMother().getModel() : child.getMother() != null? child.getMother() : rdao.findOne(update.getMotherUUID());

		if (mother.getMother() == null)
			throw new RESTException("not.mother");

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
		child.setResponsible(responsible);
		child.setMother(mother);

		return new ChildDTO(getDao().save(child), true);
	}

	protected Child persist(ChildDTO create, UserDetails details) {
		Child		model		= create.getModel();
		Responsible responsible	= create.getResponsible() != null? create.getResponsible().getUuid() != null? rdao.findOne(create.getResponsible().getUuid()) : create.getResponsible().getModel() : rdao.findOne(create.getResponsibleUUID());
		Responsible	mother		= create.getMother() != null? create.getMother().getUuid() != null? rdao.findOne(create.getMother().getUuid()) : create.getMother().getModel() : rdao.findOne(create.getMotherUUID());

		if (mother != null && mother.getMother() == null)
			throw new RESTException("not.mother");

		model.setMother(mother);
		model.setResponsible(responsible);

		return getDao().save(model);
	}
}