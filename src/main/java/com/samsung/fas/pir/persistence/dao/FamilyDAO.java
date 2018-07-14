package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.dao.utils.SBPage;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.models.Family;
import com.samsung.fas.pir.persistence.models.QFamily;
import com.samsung.fas.pir.persistence.repositories.IFamily;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FamilyDAO extends BaseDAO<Family, Long, IFamily, QFamily> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	EntityManager		manager;

	@Autowired
	public FamilyDAO(IFamily repository, EntityManager manager) {
		super(repository);
		setManager(manager);
	}

	public Collection<Family> findAllIn(Collection<UUID> collection) {
		return getRepository().findAllByUuidIn(collection);
	}

	@Override
	public Family save(Family model) {
		model.setCode(model.getCommunity().getUnity().getAbbreviation().toUpperCase().concat("F" + String.format("%06d", ((BigInteger) getManager().createNativeQuery("select count(*) from family where family.code like ?1").setParameter(1,  model.getCommunity().getUnity().getAbbreviation().toUpperCase().concat("F").concat("%")).getSingleResult()).longValue() + 1L)));
		return super.save(model);
	}

	@Override
	public Collection<Family> save(Iterable<Family> models) {
		return super.save(StreamSupport.stream(models.spliterator(), false).peek(item -> item.setCode(item.getCommunity().getUnity().getAbbreviation().toUpperCase().concat("F" + String.format("%06d", ((BigInteger) getManager().createNativeQuery("select count(*) from family where family.code like ?1").setParameter(1,  item.getCommunity().getUnity().getAbbreviation().toUpperCase().concat("F").concat("%")).getSingleResult()).longValue() + ((List) models).indexOf(item) + 1L)))).collect(Collectors.toList()));
	}
}
