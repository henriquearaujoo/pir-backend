package com.samsung.fas.pir.persistence.repositories;

import com.samsung.fas.pir.persistence.models.FileData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IFile extends PagingAndSortingRepository<FileData, Long> {
	Collection<FileData> findAllByResource(boolean isResource);
	Collection<FileData> findAllBySong(boolean isSong);
}