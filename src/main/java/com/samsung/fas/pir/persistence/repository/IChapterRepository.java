package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.QChapter;
import com.samsung.fas.pir.persistence.repository.base.BRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

@Repository
public interface IChapterRepository extends BRepository<Chapter, Long, QChapter> {
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update chapter set in_use = false where pirdb.public.chapter.number = ?1", nativeQuery = true)
	void invalidateAll(long number);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update chapter set in_use = false where pirdb.public.chapter.id = ?1", nativeQuery = true)
	void invalidateOne(long id);

	@Query(nativeQuery = true, value = "SELECT * FROM chapter as t JOIN (SELECT *  FROM chapter WHERE chapter.in_use = true) as t1 ON t.number = t1.number ORDER BY t.number, t.version")
	Set<Chapter> findAllByValid();

	@Query(nativeQuery = true, value = "SELECT * FROM chapter as t JOIN (SELECT *  FROM chapter WHERE chapter.in_use = true) as t1 ON t.number = t1.number ORDER BY ?#{#pageable}", countQuery = "SELECT count(*) FROM chapter as t JOIN (SELECT *  FROM chapter WHERE chapter.in_use = true) as t1 ON t.number = t1.number")
	Page<Chapter> findAllByValid(Pageable pageable);

	Collection<Chapter> findAllByChapterNotIn(Collection<Integer> chapters);
	Collection<Chapter> findAllByChapter(int chapter);
	Page<Chapter> findAllByChapterNotIn(Collection<Integer> chapters, Pageable pageable);

	@Override
	default void customize(QuerydslBindings bindings, QChapter root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.chapter).as("number").withDefaultBinding();
		bindings.bind(root.estimatedTime).as("estimated_time").withDefaultBinding();
		bindings.bind(root.timeUntilNext).as("time_next_visit").withDefaultBinding();
		bindings.bind(root.valid).as("status").withDefaultBinding();
		bindings.excluding(
				root.id,
				root.description,
				root.content,
				root.purpose,
				root.familyTasks,
				root.conclusion,
				root.intervention,
				root.greetings,
				root.subtitle
		);
	}
}
