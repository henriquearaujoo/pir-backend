package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.dao.utils.SBPage;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.persistence.repositories.IResponsibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: Change Queries
@Service
public class ResponsibleDAO extends BaseDAO<Responsible, Long, QResponsible> {
	private	final EntityManager emanager;

	@Autowired
	public ResponsibleDAO(IResponsibleRepository repository, EntityManager emanager) {
		super(repository);
		this.emanager = emanager;
	}

	public Collection<Responsible> findAllResponsible() {
		return ((IResponsibleRepository) repository).findAllByMotherIsNull();
	}

	public Collection<Responsible> findAllResponsible(Predicate predicate) {
		final 	JPAQuery<Responsible> 	query 				= new JPAQuery<>(emanager);
		final	QResponsible 			responsible			= QResponsible.responsible;
		final	QMother 				mother				= QMother.mother;
		return query.select(responsible).from(responsible).leftJoin(mother).on(responsible.id.eq(mother.id)).where(mother.id.isNull().and(predicate)).fetch();
	}

	public Page<Responsible> findAllResponsible(Pageable pageable) {
		return ((IResponsibleRepository) repository).findAllByMotherIsNull(pageable);
	}

	public Page<?> findAllResponsible(Predicate predicate, Pageable pageable) {
		final 	JPAQuery<Responsible> 	query 		= new JPAQuery<>(emanager);
		final	QResponsible 			responsible	= QResponsible.responsible;
		final	QMother 				mother		= QMother.mother;
		final	JPAQuery<Responsible>	result		= query.select(responsible).from(responsible).leftJoin(mother).on(responsible.id.eq(mother.id)).where(mother.id.isNull().and(predicate));
		final	Query					page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Responsible.class, "responsible"));
		return SBPage.getPageList(pageable, page);
	}
}
