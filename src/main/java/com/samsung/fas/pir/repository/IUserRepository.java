package com.samsung.fas.pir.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.models.entity.QUser;
import com.samsung.fas.pir.models.entity.User;
import ext.java.util.QDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID>, PagingAndSortingRepository<User, UUID>, QueryDslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {
	User findByLoginUsernameIgnoreCase(String login);
	User findOneByGuid(UUID guid);
	User findByEmail(String email);
	User findByLegalPersonCnpj(String cnpj);
	User findByIndividualPersonCpf(String cpf);

	List<User> findByIndividualPersonRg(String rg);
	List<User> findByLegalPersonIe(String ie);

	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.guid).as("id").withDefaultBinding();
		bindings.bind(root.login.active).as("status").withDefaultBinding();
		bindings.bind(root.legalPerson).as("pjur").withDefaultBinding();
		bindings.bind(root.individualPerson).as("pfis").withDefaultBinding();
		bindings.bind(root.profile.guid).as("profile").withDefaultBinding();
		bindings.bind(root.address.city.name).as("city").withDefaultBinding();
		bindings.bind(root.registerDate).as("date").all(QDate::between);
		bindings.excluding	(
								root.login.password,
								root.profile.whoCreated,
								root.profile.whoUpdated,
								root.profile.rules
							);
	}
}
