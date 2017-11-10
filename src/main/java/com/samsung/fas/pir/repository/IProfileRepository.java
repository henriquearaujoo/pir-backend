package com.samsung.fas.pir.repository;

import com.samsung.fas.pir.models.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface IProfileRepository extends JpaRepository<Profile, UUID> {
	@Query(value="select * from profile where lower(title) = lower(?1)", nativeQuery=true)
	Profile findOneByTitleIgnoreCase(String title);
	Profile findOneByGuid(UUID guid);
	List<Profile> findByActiveTrue();
}
