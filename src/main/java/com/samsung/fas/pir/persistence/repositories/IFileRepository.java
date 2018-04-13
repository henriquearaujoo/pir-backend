package com.samsung.fas.pir.persistence.repositories;

import com.samsung.fas.pir.persistence.models.MDataFile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileRepository extends PagingAndSortingRepository<MDataFile, Long> {
	//
}