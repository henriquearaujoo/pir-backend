package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.entity.QResponsible;
import com.samsung.fas.pir.persistence.models.entity.Responsible;
import com.samsung.fas.pir.persistence.repository.IResponsibleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ResponsibleDAO extends BaseDAO<Responsible, Long, QResponsible> {
	public Collection<Responsible> findAllResponsible() {
		return ((IResponsibleRepository) repository).findAllByMotherIsNull();
	}

	public Collection<Responsible> findAllResponsible(Predicate predicate) {
		return ((IResponsibleRepository) repository).findAllByMotherIsNull(predicate);
	}

	public Page<Responsible> findAllResponsible(Pageable pageable) {
		return ((IResponsibleRepository) repository).findAllByMotherIsNull(pageable);
	}

	public Page<Responsible> findAllResponsible(Predicate predicate, Pageable pageable) {
		return ((IResponsibleRepository) repository).findAllByMotherIsNull(predicate, pageable);
	}
}
