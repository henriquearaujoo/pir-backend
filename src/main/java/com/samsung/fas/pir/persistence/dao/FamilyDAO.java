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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.UUID;

@Service
public class FamilyDAO extends BaseDAO<Family, Long, IFamily, QFamily> {
	@Autowired
	public FamilyDAO(IFamily repository) {
		super(repository);
	}

	public Collection<Family> findAllIn(Collection<UUID> collection) {
		return getRepository().findAllByUuidIn(collection);
	}
}
