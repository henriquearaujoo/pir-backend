package com.samsung.fas.pir.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.User;
import java.util.UUID;
import java.util.List;
import java.lang.String;
import java.util.Date;
import com.samsung.fas.pir.models.user.Address;

@Repository
public interface IUsersRepository extends JpaRepository<User, UUID> {
	List<User> findById(UUID id);
	List<User> findByLogin(String login);
}
