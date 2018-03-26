package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.ChildDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.Child;
import com.samsung.fas.pir.persistence.models.Responsible;
import com.samsung.fas.pir.rest.dto.ChildDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ChildService extends BService<Child, ChildDTO, ChildDAO, Long> {
	private	final ResponsibleDAO rdao;

	@Autowired
	public ChildService(ChildDAO dao, ResponsibleDAO rdao) {
		super(dao, Child.class, ChildDTO.class);
		this.rdao	= rdao;
	}

	@Override
	public ChildDTO save(ChildDTO create, UserDetails account) {
		Child		model		= create.getModel();
		Responsible responsible	= rdao.findOne(IDCoder.decode(create.getResponsibleID()));
		Responsible	mother		= rdao.findOne(IDCoder.decode(create.getMotherID()));

		if (mother.getMother() == null)
			throw new RESTException("not.mother");

		model.setMother(mother);
		model.setResponsible(responsible);

		return new ChildDTO(dao.save(model), true);
	}

	@Override
	public ChildDTO update(ChildDTO update, UserDetails account) {
		Child		model		= update.getModel();
		Child		child		= dao.findOne(model.getUuid());
		Responsible	responsible	= rdao.findOne(IDCoder.decode(update.getResponsibleID()));
		Responsible	mother		= rdao.findOne(IDCoder.decode(update.getMotherID()));

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

		return new ChildDTO(dao.save(child), true);
	}
}
