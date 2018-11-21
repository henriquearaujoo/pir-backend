package com.samsung.fas.pir.configuration.initialization;

import com.google.common.hash.Hashing;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.dao.*;
import com.samsung.fas.pir.persistence.enums.EProfileType;
import com.samsung.fas.pir.persistence.models.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class Database {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		UserDAO				userDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			ProfileDAO			profileDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			RuleDAO				ruleDAO;


	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			PageDAO				pageDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			StateDAO			stateDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			CityDAO				cityDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			PasswordEncoder		encoder;

	@Autowired
	public Database(UserDAO userDAO, ProfileDAO profileDAO, StateDAO stateDAO, CityDAO cityDAO, PageDAO pageDAO, RuleDAO ruleDAO, PasswordEncoder encoder) {
		setStateDAO(stateDAO);
		setCityDAO(cityDAO);
		setUserDAO(userDAO);
		setRuleDAO(ruleDAO);
		setProfileDAO(profileDAO);
		setPageDAO(pageDAO);
		setEncoder(encoder);

	}

	@EventListener(ApplicationStartedEvent.class)
	public void initialize() {
		initializeState();
		initializeCity();
		initializeProfile();
		initializePage();
		initializeRule();
		initializeUser();
	}

	private void initializeState() {
		if (getStateDAO().findAll().isEmpty()) {
			try {
				File				file			= new ClassPathResource("data/states.txt").getFile();
				BufferedReader		reader			= new BufferedReader(new FileReader(file));
				List<State>			states			= new ArrayList<>();
				String				line;

				while ((line = reader.readLine()) != null) {
					State			state		= new State();
					String[]		data		= line.split("\\|");

					state.setAbbreviation(data[0]);
					state.setName(data[1]);
					states.add(state);
				}

				getStateDAO().save(states);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void initializeCity() {
		if (getCityDAO().findAll().isEmpty()) {
			try {
				File				file			= new ClassPathResource("data/cities.txt").getFile();
				BufferedReader		reader			= new BufferedReader(new FileReader(file));
				List<State>			states			= (List<State>) getStateDAO().findAll();
				List<City>			cities			= new ArrayList<>();
				String				line;

				while ((line = reader.readLine()) != null) {
					City			city		= new City();
					String[]		data		= line.split("\\|");
					State			state		= states.stream().filter(item -> item.getAbbreviation().equalsIgnoreCase(data[1])).findAny().orElse(null);

					if (state != null) {
						city.setName(data[0]);
						city.setState(state);
						cities.add(city);
					}
				}

				getCityDAO().save(cities);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void initializePage() {
		if (getPageDAO().findAll().isEmpty()) {
			try {
				File				file			= new ClassPathResource("data/pages.txt").getFile();
				BufferedReader		reader			= new BufferedReader(new FileReader(file));
				List<Page>			pages			= new ArrayList<>();
				String				line;

				while ((line = reader.readLine()) != null) {
					Page			page		= new Page();
					String[]		data		= line.split("\\|");

					page.setTitle(data[0]);
					page.setUrl(data[1]);
					pages.add(page);
				}

				getPageDAO().save(pages);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void initializeRule() {
		if (getRuleDAO().findAll().isEmpty()) {
			Profile			profile			= getProfileDAO().findOne(1L);
			List<Page>		pages			= (List<Page>) getPageDAO().findAll();
			List<Rule>		rules			= new ArrayList<>();

			for (Page page : pages) {
				Rule		rule			= new Rule();

				rule.setCanCreate(true);
				rule.setCanDelete(true);
				rule.setCanRead(true);
				rule.setCanUpdate(true);
				rule.setPage(page);
				rule.setProfile(profile);
				rules.add(rule);
			}

			getRuleDAO().save(rules);
		}
	}

	private void initializeProfile() {
		if (getProfileDAO().findAll().isEmpty()) {
			Profile			profile			= new Profile();

			profile.setActive(true);
			profile.setDescription("Administrador do Sistema");
			profile.setTitle("Administrador");
			profile.setType(EProfileType.ADMIN);

			getProfileDAO().save(profile);
		}
	}

	private void initializeUser() {
		if (getUserDAO().findAll().isEmpty()) {
			User			user			= new User();
			Account			account			= new Account();
			LegalEntity		entity			= new LegalEntity();
			Address			address			= new Address();
			Profile			profile			= getProfileDAO().findOne(1L);
			City			city			= getCityDAO().findOne("Manaus", "AM");

			account.setAccountNonExpired(true);
			account.setAccountNonLocked(true);
			account.setCredentialsNonExpired(true);
			account.setEnabled(true);
			account.setUsername("admin");
			account.setPassword(encoder.encode(Hashing.sha256().hashString(Hashing.sha256().hashString("admin123", StandardCharsets.UTF_8).toString(), StandardCharsets.UTF_8).toString()));
			account.setProfile(profile);
			account.setUser(user);

			address.setCity(city);
			address.setNeighborhoodAddress("Parque 10");
			address.setNumberAddress("351");
			address.setStreetAddress("Rua A");
			address.setPostalCode("69055-660");
			address.setUser(user);

			entity.setCnpj("09351359000188");
			entity.setFantasyName("Fundação Amazonas Sustentável");
			entity.setSocialName("Fundação Amazonas Sustentável");
			entity.setUser(user);

			user.setName("FAS - Fundação Amazonas Sustentável");
			user.setAccount(account);
			user.setEntity(entity);
			user.setAddress(address);

			getUserDAO().save(user);
		}
	}
}
