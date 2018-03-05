package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.QChapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface IChapterRepository extends JpaRepository<Chapter, Long>, PagingAndSortingRepository<Chapter, Long>, QueryDslPredicateExecutor<Chapter>, QuerydslBinderCustomizer<QChapter> {
	Chapter findByUuid(UUID uuid);

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

	@Query(nativeQuery = true, value = "SELECT * FROM chapter as t JOIN (SELECT *  FROM chapter WHERE chapter.in_use = true) as t1 ON t.number = t1.number ORDER BY ?#{#pageable}",
		   countQuery = "SELECT count(*) FROM chapter as t JOIN (SELECT *  FROM chapter WHERE chapter.in_use = true) as t1 ON t.number = t1.number")
	Page<Chapter> findAllByValid(Pageable pageable);

	Set<Chapter> findAllByChapterNotIn(Set<Integer> chapters);
	Page<Chapter> findAllByChapterNotIn(Set<Integer> chapters, Pageable pageable);

	Chapter findOneByChapterAndVersion(int chapter, int version);
	List<Chapter> findAllByChapter(int chapter);

	@Override
	default void customize(QuerydslBindings bindings, QChapter root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
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
