package com.samsung.fas.pir.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.User;

@Repository
public interface IUsersRepository extends JpaRepository<User, Long> {
	
}
