package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.QUser;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository
public interface IUser extends IBaseRepository<User, Long, QUser> {
	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QUser root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
		bindings.bind(root.account.profile.title).as("profile.name").withDefaultBinding();
		bindings.bind(root.account.profile.type).as("profile.type").withDefaultBinding();
		bindings.bind(root.account.enabled).as("status").withDefaultBinding();
		bindings.bind(root.account.profile.uuid).as("profile").withDefaultBinding();
		bindings.bind(root.address.city.name).as("city").withDefaultBinding();
//		bindings.bind(root.registerDate).as("date").all((path, values) -> Optional.ofNullable(path.between(values)));
//		bindings.excluding(
//				root.account.password,
//				root.account.profile.whoCreated,
//				root.account.profile.whoUpdated,
//				root.account.profile.rules
//		);
	}
}