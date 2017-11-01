package com.samsung.fas.pir.dao.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.entity.Profile;

import java.lang.String;

@Repository
@Transactional
public interface IProfileRepository extends JpaRepository<Profile, UUID> {
	@Query(value="select * from profile where lower(title) = lower(?1)", nativeQuery=true)
	Profile findOneByTitleIgnoreCase(String title);
	List<Profile> findByActiveTrue();
}
