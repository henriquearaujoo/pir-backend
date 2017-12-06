package com.samsung.fas.pir.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.entity.Chapter;
import com.samsung.fas.pir.repository.IChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChapterDAO {
	private 	IChapterRepository 		repository;

	@Autowired
	public ChapterDAO(IChapterRepository repository) {
		this.repository = repository;
	}

	public Chapter findOne(long id) {
		return repository.findOne(id);
	}

	public Chapter findOneByChapterAndVersion(int chapter, int version) {
		return repository.findOneByChapterAndVersion(chapter, version);
	}

	public List<Chapter> findAllByChapter(int chapter) {
		return repository.findAllByChapter(chapter);
	}

	public List<Chapter> findAll() {
		return repository.findAll();
	}

	public List<Chapter> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(),true).collect(Collectors.toList());
	}

	public Page<Chapter> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Chapter> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Chapter save(Chapter user) {
		return repository.save(user);
	}

	public void invalidateAllChapters() {
		repository.invalidateAll();
	}

	public void delete(long id) {
		repository.delete(id);
	}
}
