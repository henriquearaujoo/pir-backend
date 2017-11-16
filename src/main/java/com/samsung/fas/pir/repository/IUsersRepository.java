package com.samsung.fas.pir.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.models.entity.QUser;
import com.samsung.fas.pir.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.samsung.fas.pir.models.entity.QUser.user;

@Repository
public interface IUsersRepository extends JpaRepository<User, UUID>, PagingAndSortingRepository<User, UUID>, JpaSpecificationExecutor<User>, QueryDslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {
	User findByLoginIgnoreCase(String login);
	User findOneByGuid(UUID guid);
	User findByEmail(String email);
	User findByOrganizationCnpj(String cnpj);
	User findByPersonCpf(String cpf);

	List<User> findAll();
	List<User> findByPersonRg(String rg);
	List<User> findByOrganizationIe(String ie);
	List<User> findByProfileGuid(UUID id);

	/*
	 * URL Queries
	 */
	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(user.guid).as("id").withDefaultBinding();
		bindings.bind(user.active).as("status").withDefaultBinding();
		bindings.bind(user.organization).as("pjur").withDefaultBinding();
		bindings.bind(user.person).as("pfis").withDefaultBinding();
		bindings.bind(user.profile.guid).as("profile").withDefaultBinding();
		bindings.bind(user.address.city.name).as("city").withDefaultBinding();
		bindings.bind(user.registerDate).as("date").all((path, collection) -> {
			Iterator<? extends Date> 	iterator 	= collection.iterator();
			Date						current		= null;
			Date						last		= null;
			Date						first		= null;

			while (iterator.hasNext()) {
				current = iterator.next();
				last 	= last == null? current : last;
				first	= first == null? current : first;
				last	= current.compareTo(last) > 0? current : last;
				first	= current.compareTo(first) < 0? current : first;
			}

			assert (last != null);

			Calendar l = Calendar.getInstance();
			l.setTime(last);
			l.set(Calendar.HOUR, 23);
			l.set(Calendar.MINUTE, 59);
			l.set(Calendar.SECOND, 59);
			l.set(Calendar.MILLISECOND, 999);
			last = new Date(l.getTimeInMillis());

			return path.between(first, last);
		});
		bindings.excluding	(	root.id,
								root.password,
								root.profile.id,
								root.profile.whoCreated,
								root.profile.whoUpdated,
								root.profile.rules
							);
	}
}
