package com.samsung.fas.pir.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.Profile;

@Repository
public interface IProfileRepository extends JpaRepository<Profile, Long> {

}
