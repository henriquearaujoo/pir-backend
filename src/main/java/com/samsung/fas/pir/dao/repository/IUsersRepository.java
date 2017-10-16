package com.samsung.fas.pir.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.User;
import java.util.UUID;
import java.util.List;

@Repository
public interface IUsersRepository extends JpaRepository<User, Long> {
	List<User> findById(UUID id);
}
