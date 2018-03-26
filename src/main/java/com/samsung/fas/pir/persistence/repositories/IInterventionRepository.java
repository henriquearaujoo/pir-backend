package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.Intervention;
import com.samsung.fas.pir.persistence.models.QIntervention;
import com.samsung.fas.pir.persistence.repositories.base.BRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface IInterventionRepository extends BRepository<Intervention, Long, QIntervention> {
	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QIntervention root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.chapter.chapter).as("chapter").withDefaultBinding();
		bindings.bind(root.chapter.version).as("chapter_version").withDefaultBinding();
		bindings.bind(root.chapter.valid).as("chapter_status").withDefaultBinding();
		bindings.bind(root.chapter.title).as("chapter_title").withDefaultBinding();
		bindings.bind(root.chapter.uuid).as("chapter_id").withDefaultBinding();
		bindings.excluding(
				root.id,
				root.description,
				root.activity,
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