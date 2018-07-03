package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.dao.utils.SBPage;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.models.QResponsible;
import com.samsung.fas.pir.persistence.models.Responsible;
import com.samsung.fas.pir.persistence.repositories.IResponsible;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.UUID;

@Service
public class ResponsibleDAO extends BaseDAO<Responsible, Long, IResponsible, QResponsible> {
	private		final	EntityManager	manager;

	@Autowired
	public ResponsibleDAO(IResponsible repository, EntityManager emanager) {
		super(repository);
		this.manager = emanager;
	}

	public Collection<Responsible> findAllByMobileIdIn(Collection<Long> collection) {
		return getRepository().findAllByMobileIdIn(collection);
	}

	public Collection<Responsible> findAllIn(Collection<UUID> collection) {
		return getRepository().findAllByUuidIn(collection);
	}

	public Collection<Responsible> findAllMothers() {
		JPAQuery<Responsible> 	query 				= new JPAQuery<>(manager);
		QResponsible 			responsible			= QResponsible.responsible;
		return query.select(responsible).from(responsible).where(responsible.gender.eq(EGender.FEMALE).and(responsible.childrenCount.gt(Expressions.asNumber(0)))).fetch();
	}

	public Collection<Responsible> findAllMothers(Predicate predicate) {
		JPAQuery<Responsible> 	query 				= new JPAQuery<>(manager);
		QResponsible 			responsible			= QResponsible.responsible;
		return query.select(responsible).from(responsible).where(responsible.gender.eq(EGender.FEMALE).and(responsible.childrenCount.gt(Expressions.asNumber(0))).and(predicate)).fetch();
	}

	public Page<?> findAllMothers(Pageable pageable) {
		JPAQuery<Responsible> 	query 		= new JPAQuery<>(manager);
		QResponsible 			responsible	= QResponsible.responsible;
		JPAQuery<Responsible>	result		= query.select(responsible).from(responsible).where(responsible.gender.eq(EGender.FEMALE).and(responsible.childrenCount.gt(Expressions.asNumber(0))));
		Query					page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Responsible.class, "responsible"));
		return SBPage.getPageList(pageable, page, null);
	}

	public Page<?> findAllMothers(Pageable pageable, Predicate predicate) {
		JPAQuery<Responsible> 	query 		= new JPAQuery<>(manager);
		QResponsible 			responsible	= QResponsible.responsible;
		JPAQuery<Responsible>	result		= query.select(responsible).from(responsible).where(responsible.gender.eq(EGender.FEMALE).and(responsible.childrenCount.gt(Expressions.asNumber(0))).and(predicate));
		Query					page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Responsible.class, "responsible"));
		return SBPage.getPageList(pageable, page, null);
	}

	public Collection<Responsible> findAllResponsible() {
		JPAQuery<Responsible> 	query 				= new JPAQuery<>(manager);
		QResponsible 			responsible			= QResponsible.responsible;
		return query.select(responsible).from(responsible).where(responsible.gender.eq(EGender.FEMALE).and(responsible.childrenCount.eq(Expressions.asNumber(0))).or(responsible.gender.eq(EGender.MALE))).fetch();
	}

	public Collection<Responsible> findAllResponsible(Predicate predicate) {
		JPAQuery<Responsible> 	query 				= new JPAQuery<>(manager);
		QResponsible 			responsible			= QResponsible.responsible;
		return query.select(responsible).from(responsible).where(Expressions.allOf(responsible.gender.eq(EGender.FEMALE).and(responsible.childrenCount.eq(Expressions.asNumber(0))).or(responsible.gender.eq(EGender.MALE))).and(predicate)).fetch();
	}

	public Page<?> findAllResponsible(Pageable pageable) {
		JPAQuery<Responsible> 	query 		= new JPAQuery<>(manager);
		QResponsible 			responsible	= QResponsible.responsible;
		JPAQuery<Responsible>	result		= query.select(responsible).from(responsible).where(Expressions.allOf(responsible.gender.eq(EGender.FEMALE).and(responsible.childrenCount.eq(Expressions.asNumber(0))).or(responsible.gender.eq(EGender.MALE))));
		Query					page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Responsible.class, "responsible"));
		return SBPage.getPageList(pageable, page, null);
	}

	public Page<?> findAllResponsible(Predicate predicate, Pageable pageable) {
		JPAQuery<Responsible> 	query 		= new JPAQuery<>(manager);
		QResponsible 			responsible	= QResponsible.responsible;
		JPAQuery<Responsible>	result		= query.select(responsible).from(responsible).where(Expressions.allOf(responsible.gender.eq(EGender.FEMALE).and(responsible.childrenCount.eq(Expressions.asNumber(0))).or(responsible.gender.eq(EGender.MALE))).and(predicate));
		Query					page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Responsible.class, "responsible"));
		return SBPage.getPageList(pageable, page, null);
	}
}
