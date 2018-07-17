package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Pregnancy;
import com.samsung.fas.pir.persistence.models.Pregnant;
import com.samsung.fas.pir.persistence.models.QPregnancy;
import com.samsung.fas.pir.persistence.models.QPregnant;
import com.samsung.fas.pir.persistence.repositories.IPregnancy;
import com.samsung.fas.pir.persistence.repositories.IPregnant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PregnantDAO extends BaseDAO<Pregnant, Long, IPregnant, QPregnant> {
	@Autowired
	public PregnantDAO(IPregnant repository) {
		super(repository);
	}

	public String getSequentialCode(String prefix) {
		return prefix.concat(String.format("%06d", getRepository().countAllByCodeStartingWith(prefix) + 1L));
	}
}