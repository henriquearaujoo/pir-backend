package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.entity.Mother;
import com.samsung.fas.pir.persistence.models.entity.QMother;
import com.samsung.fas.pir.persistence.models.entity.QResponsible;
import com.samsung.fas.pir.persistence.models.entity.Responsible;
import com.samsung.fas.pir.persistence.repository.IResponsibleRepository;
import com.samsung.fas.pir.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ResponsibleDAO extends BaseDAO<Responsible, Long, QResponsible> {
	private 	EntityManager			emanager;

	@Autowired
	public ResponsibleDAO(EntityManager emanager) {
		this.emanager	= emanager;
	}

	public Collection<Responsible> findAllResponsible() {
		return ((IResponsibleRepository) repository).findAllByMotherIsNull();
	}

	public Collection<Responsible> findAllResponsible(Predicate predicate) {
		JPAQuery<Responsible>	responsibleQuery 	= new JPAQuery<>(emanager);
		JPAQuery<Mother>		motherQuery			= new JPAQuery<>(emanager);
		QResponsible 			responsible			= QResponsible.responsible;
		QMother					mother				= QMother.mother;
		return responsibleQuery.from(responsible).where(responsible.id.notIn(motherQuery.from(mother).select(mother.id))).fetch();
	}

	public Page<Responsible> findAllResponsible(Pageable pageable) {
		return ((IResponsibleRepository) repository).findAllByMotherIsNull(pageable);
	}

	@SuppressWarnings("unchecked")
	public Page<Responsible> findAllResponsible(Predicate predicate, Pageable pageable) {
		JPAQuery<Responsible>		jpaquery	= new JPAQuery<>(emanager);
		JPAQuery<Mother>			motherQuery	= new JPAQuery<>(emanager);
		PathBuilder<Responsible> 	entityPath 	= new PathBuilder<>(Responsible.class, "responsible");
		QResponsible				responsible	= QResponsible.responsible;
		QMother						mother		= QMother.mother;
		JPAQuery<Responsible>		result		= jpaquery.from(responsible).where(responsible.id.notIn(motherQuery.from(mother).select(mother.id)));
		Query						query		= Tools.setupPage(result, pageable, entityPath);

		try {
			if (pageable.getPageSize() > query.getResultList().size())
				return new PageImpl<Responsible>(query.getResultList().subList(pageable.getOffset(), pageable.getOffset() + query.getResultList().size()), pageable, query.getResultList().size());
			return new PageImpl<Responsible>(query.getResultList().subList(pageable.getOffset(), pageable.getOffset() + pageable.getPageSize()), pageable, query.getResultList().size());
		} catch (Exception e) {
			return new PageImpl<>(new ArrayList<>(), pageable, query.getResultList().size());
		}
	}
}
