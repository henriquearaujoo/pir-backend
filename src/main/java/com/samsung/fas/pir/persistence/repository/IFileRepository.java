package com.samsung.fas.pir.persistence.repository;

import com.samsung.fas.pir.persistence.models.entity.MDataFile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileRepository extends PagingAndSortingRepository<MDataFile, Long> {
	//
}