package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.Greetings;
import com.samsung.fas.pir.persistence.models.entity.QGreetings;
import com.samsung.fas.pir.persistence.repository.base.BRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface IGreetingsRepository extends BRepository<Greetings, Long, QGreetings> {
	@Override
	default void customize(QuerydslBindings bindings, QGreetings root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.chapter.chapter).as("chapter").withDefaultBinding();
		bindings.bind(root.chapter.version).as("chapter_version").withDefaultBinding();
		bindings.bind(root.chapter.valid).as("chapter_status").withDefaultBinding();
		bindings.bind(root.chapter.title).as("chapter_title").withDefaultBinding();
		bindings.bind(root.chapter.id).as("chapter_id").withDefaultBinding();
		bindings.excluding(
				root.id,
				root.description,
				root.chapter.greetings,
				root.chapter.intervention,
				root.chapter.conclusion,
				root.chapter.familyTasks,
				root.chapter.purpose,
				root.chapter.content,
				root.chapter.estimatedTime,
				root.chapter.description,
				root.chapter.timeUntilNext,
				root.chapter.subtitle
		);
	}
}
