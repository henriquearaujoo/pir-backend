package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Address;
import com.samsung.fas.pir.persistence.models.QAddress;
import com.samsung.fas.pir.persistence.repositories.IAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressDAO extends BaseDAO<Address, Long, IAddress, QAddress> {
	@Autowired
	public AddressDAO(IAddress repository) {
		super(repository);
	}
}
