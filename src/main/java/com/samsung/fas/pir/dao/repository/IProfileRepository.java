package com.samsung.fas.pir.dao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.Profile;
import java.lang.String;

@Repository
public interface IProfileRepository extends JpaRepository<Profile, UUID> {
	@Query(value="select * from profile where title = ?1",nativeQuery=true)
	Profile findOneByTitle(String title);
}
