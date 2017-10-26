package com.samsung.fas.pir.dao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samsung.fas.pir.models.entity.Page;

@Repository
public interface IPageRepository extends JpaRepository<Page, UUID> {

}
