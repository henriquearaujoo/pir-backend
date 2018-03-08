package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChildDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.entity.Child;
import com.samsung.fas.pir.persistence.models.entity.Responsible;
import com.samsung.fas.pir.rest.dto.child.CRUChildDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChildService extends BService<Child, CRUChildDTO, ChildDAO, Long> {
	private	ResponsibleDAO	rdao;

	@Autowired
	public ChildService(ChildDAO dao, ResponsibleDAO rdao) {
		super(dao, Child.class, CRUChildDTO.class);
		this.rdao	= rdao;
	}

	@Override
	public CRUChildDTO save(CRUChildDTO create, UserDetails account) {
		Child		model		= create.getModel();
		Responsible	responsible	= Optional.ofNullable(rdao.findOne(IDCoder.decode(create.getResponsibleID()))).orElseThrow(() -> new RESTRuntimeException("responsible.notfound"));
		Responsible	mother		= Optional.ofNullable(rdao.findOne(IDCoder.decode(create.getMotherID()))).orElseThrow(() -> new RESTRuntimeException("mother.notfound"));

		if (mother.getMother() == null)
			throw new RESTRuntimeException("responsible.not.mother");

		model.setMother(mother);
		model.setResponsible(responsible);

		return new CRUChildDTO(dao.save(model), true);
	}

	@Override
	public CRUChildDTO update(CRUChildDTO update, UserDetails account) {
		Child		model		= update.getModel();
		Child		child		= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("child.id.missing")))).orElseThrow(() -> new RESTRuntimeException("child.notfound"));
		Responsible	responsible	= Optional.ofNullable(rdao.findOne(IDCoder.decode(update.getResponsibleID()))).orElseThrow(() -> new RESTRuntimeException("responsible.notfound"));
		Responsible	mother		= Optional.ofNullable(rdao.findOne(IDCoder.decode(update.getMotherID()))).orElseThrow(() -> new RESTRuntimeException("responsible.notfound"));

		if (mother.getMother() == null)
			throw new RESTRuntimeException("responsible.not.mother");

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

		return new CRUChildDTO(dao.save(child), true);
	}
}
