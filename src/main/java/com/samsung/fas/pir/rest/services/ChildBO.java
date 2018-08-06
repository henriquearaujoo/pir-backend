package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChildDAO;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.ChildDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service
public class ChildBO extends BaseBO<Child, ChildDAO, ChildDTO, Long> {
	@Getter
	@Setter
	private 	FamilyBO		familyBO;

	@Getter
	@Setter
	private 	UserBO 			userBO;

	@Getter
	@Setter
	private 	SAnswerBO		sAnswerBO;

	@Getter
	@Setter
	private		VisitBO			visitBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	ModelMapper		mapper;

	@Autowired
	public ChildBO(ChildDAO dao, @Lazy FamilyBO familyBO, UserBO userBO, VisitBO visitBO, SAnswerBO sAnswerBO, ModelMapper mapper) {
		super(dao);
		setVisitBO(visitBO);
		setVisitBO(visitBO);
		setFamilyBO(familyBO);
		setUserBO(userBO);
		setSAnswerBO(sAnswerBO);
		setMapper(mapper);
	}

	@Override
	public ChildDTO save(ChildDTO create, Device device, UserDetails account) {
		Child				model		= create.getModel();
		Child				child		= create.getUuid() != null? getDao().findOne(create.getUuid()) : null;
		Family				family		= create.getFamilyUUID() != null? getFamilyBO().getDao().findOne(create.getFamilyUUID()) : null;
		return child == null? new ChildDTO(getDao().save(setupChild(model, family, null)), device, true) : new ChildDTO(getDao().save(setupChild(child, model, family, null)), device, true);
	}

	@Override
	public ChildDTO update(ChildDTO update, Device device, UserDetails account) {
		return save(update, device, account);
	}

	@Override
	public Collection<ChildDTO> save(Collection<ChildDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> save(item, device, details)).collect(Collectors.toList());
	}

	@Override
	public Collection<ChildDTO> update(Collection<ChildDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> update(item, device, details)).collect(Collectors.toList());
	}

	Child setupChild(Child model, Family family, Agent agent) {
		model.setCode(getDao().getSequentialCode(family.getCommunity().getUnity().getAbbreviation().toUpperCase().concat("C")));
		model.setFamily(family);
		model.setAgent(null);
		model.setVisits(setupVisit(model, model.getVisits(), agent));
		model.setAnswers(setupAnswer(model, model.getAnswers()));
		return model;
	}

	Child setupChild(Child child, Child model, Family family, Agent agent) {
		getMapper().map(model, child);
		child.setFamily(family);
		child.setAgent(null);
		child.setVisits(setupVisit(child, model.getVisits(), agent));
		child.setAnswers(setupAnswer(child, model.getAnswers()));
		return child;
	}

	private Collection<SAnswer> setupAnswer(Child child, Collection<SAnswer> collection) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			SAnswer		sAnswer		= uuid != null? getSAnswerBO().getDao().findOne(uuid) : null;
			if (sAnswer != null) {
				return getSAnswerBO().setupAnswer(sAnswer, item, child);
			} else {
				return getSAnswerBO().setupAnswer(item, child);
			}
		}).collect(Collectors.toList());
	}

	private Collection<Visit> setupVisit(Child child, Collection<Visit> collection, Agent agent) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Visit		visit		= uuid != null? getVisitBO().getDao().findOne(uuid) : null;
			if (visit != null) {
				return getVisitBO().setupVisit(visit, item, child, agent);
			} else {
				return getVisitBO().setupVisit(item, child, agent);
			}
		}).collect(Collectors.toList());
	}
}