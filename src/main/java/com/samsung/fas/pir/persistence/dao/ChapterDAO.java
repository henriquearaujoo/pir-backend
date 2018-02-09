package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.QChapter;
import com.samsung.fas.pir.persistence.repository.IChapterRepository;
import com.samsung.fas.pir.utils.Tools;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChapterDAO {
	private 	IChapterRepository 		repository;
	private 	EntityManager			emanager;

	@Autowired
	public ChapterDAO(IChapterRepository repository, EntityManager emanager, DSLContext context) {
		this.repository = repository;
		this.emanager	= emanager;
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

	public Set<Chapter> findAllValid() {
		return repository.findAllByValid();
	}

	public List<Chapter> findAllValid(Predicate predicate) {
		JPAQuery<Chapter>	queryActive 	= new JPAQuery<>(emanager);
		JPAQuery<Chapter>	queryChapter 	= new JPAQuery<>(emanager);
		QChapter 			chapter			= QChapter.chapter1;
		// SELECT * FROM chapter WHERE chapter.number IN (SELECT chapter.number FROM chapter WHERE chapter.in_use = true)
		return queryChapter.from(chapter).where(chapter.chapter.in(queryActive.from(chapter).select(chapter.chapter).where(chapter.valid.eq(true)).fetch()).and(predicate)).fetch();
	}

	public Page<Chapter> findAllValid(Predicate predicate, Pageable pageable) {
		JPAQuery<Chapter>		queryActive 	= new JPAQuery<>(emanager);
		JPAQuery<Chapter>		queryChapter 	= new JPAQuery<>(emanager);
		PathBuilder<Chapter>	entityPath 		= new PathBuilder<>(Chapter.class, "chapter");
		QChapter				chapter			= QChapter.chapter1;
		JPAQuery<Chapter>		result;
		Query					query;
		// SELECT * FROM chapter WHERE chapter.number IN (SELECT chapter.number FROM chapter WHERE chapter.in_use = true)
		result	= queryChapter.from(chapter).where(chapter.chapter.in(queryActive.from(chapter).select(chapter.chapter).where(chapter.valid.eq(true)).fetch()).and(predicate));
		query 	= Tools.setupPage(result, pageable, entityPath);
		//noinspection unchecked
		try {
			return new PageImpl<Chapter>(query.getResultList().subList(pageable.getOffset(), pageable.getOffset() + pageable.getPageSize()), pageable, query.getResultList().size());
		} catch (Exception e) {
			return new PageImpl<Chapter>(new ArrayList<>(), pageable, query.getResultList().size());
		}
	}

	public Set<Chapter> findAllInvalid() {
		return repository.findAllByChapterNotIn(findAllValid().stream().map(Chapter::getChapter).collect(Collectors.toSet()));
	}

	public List<Chapter> findAllInvalid(Predicate predicate) {
		JPAQuery<Chapter>	queryActive 	= new JPAQuery<>(emanager);
		JPAQuery<Chapter>	queryChapter 	= new JPAQuery<>(emanager);
		QChapter			chapter			= QChapter.chapter1;
		// SELECT * FROM chapter WHERE chapter.number IN (SELECT chapter.number FROM chapter WHERE chapter.in_use = true)
		return queryChapter.from(chapter).where(chapter.chapter.notIn(queryActive.from(chapter).select(chapter.chapter).where(chapter.valid.eq(true)).fetch()).and(predicate)).fetch();
	}

	public Page<Chapter> findAllInvalid(Predicate predicate, Pageable pageable) {
		JPAQuery<Chapter>		queryActive 	= new JPAQuery<>(emanager);
		JPAQuery<Chapter>		queryChapter 	= new JPAQuery<>(emanager);
		PathBuilder<Chapter>	entityPath 		= new PathBuilder<>(Chapter.class, "chapter");
		QChapter				chapter			= QChapter.chapter1;
		JPAQuery<Chapter>		result			= new JPAQuery<>(emanager);
		Query					query;
		// SELECT * FROM chapter WHERE chapter.number IN (SELECT chapter.number FROM chapter WHERE chapter.in_use = true)
		result 	= queryChapter.from(chapter).where(chapter.chapter.notIn(queryActive.from(chapter).select(chapter.chapter).where(chapter.valid.eq(true)).fetch()).and(predicate));
		query 	= Tools.setupPage(result, pageable, entityPath);
		//noinspection unchecked
		try {
			return new PageImpl<Chapter>(query.getResultList().subList(pageable.getOffset(), pageable.getOffset() + pageable.getPageSize()), pageable, query.getResultList().size());
		} catch (Exception e) {
			return new PageImpl<Chapter>(new ArrayList<>(), pageable, query.getResultList().size());
		}
	}

	public Page<Chapter> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Chapter> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Page<Chapter> findAllValid(Pageable pageable) {
		return repository.findAllByValid(pageable);
	}

	public Page<Chapter> findAllInvalid(Pageable pageable) {
		return repository.findAllByChapterNotIn(findAllValid().stream().map(Chapter::getChapter).collect(Collectors.toSet()), pageable);
	}

	public Chapter save(Chapter user) {
		return repository.save(user);
	}

	public void invalidateAllChapters(long chapter) {
		repository.invalidateAll(chapter);
	}

	public void invalidateOne(long id) {
		repository.invalidateOne(id);
	}

	public void delete(long id) {
		repository.delete(id);
	}
}
