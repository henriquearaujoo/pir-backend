package com.samsung.fas.pir.persistence.repository;

import com.samsung.fas.pir.persistence.models.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPageRepository extends JpaRepository<Page, UUID> {
	Page findOneByUuid(UUID id);
}
