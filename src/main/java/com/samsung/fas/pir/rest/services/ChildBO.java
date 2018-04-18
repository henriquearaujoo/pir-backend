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
		Child		model		= create.getModel();
		Responsible responsible	= rdao.findOne(create.getResponsibleUUID());
		Responsible	mother		= rdao.findOne(create.getMotherUUID());

		if (mother.getMother() == null)
			throw new RESTException("not.mother");

		model.setMother(mother);
		model.setResponsible(responsible);

		return new ChildDTO(getDao().save(model), true);
	}

	@Override
	public ChildDTO update(ChildDTO update, UserDetails account) {
		Child		model		= update.getModel();
		Child		child		= getDao().findOne(model.getUuid());
		Responsible	responsible	= rdao.findOne(update.getResponsibleUUID());
		Responsible	mother		= rdao.findOne(update.getMotherUUID());

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
}
