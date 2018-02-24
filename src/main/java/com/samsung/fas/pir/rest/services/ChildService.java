package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.ChildDAO;
import com.samsung.fas.pir.persistence.dao.MotherDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.entity.Child;
import com.samsung.fas.pir.rest.dto.child.CRUChildDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService extends BService<Child, CRUChildDTO, CRUChildDTO, CRUChildDTO, ChildDAO, Long> {
	private	MotherDAO		mdao;
	private	ResponsibleDAO	rdao;

	@Autowired
	public ChildService(ChildDAO dao, MotherDAO mdao, ResponsibleDAO rdao) {
		super(dao, Child.class, CRUChildDTO.class);
		this.mdao	= mdao;
		this.rdao	= rdao;
	}

	@Override
	public CRUChildDTO save(CRUChildDTO cruChildDTO, Account account) {
		return null;
	}

	@Override
	public CRUChildDTO update(CRUChildDTO cruChildDTO, Account account) {
		return null;
	}
}
