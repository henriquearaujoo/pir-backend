package com.samsung.fas.pir.tasks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Tasks {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private CacheManager manager;

	@Autowired
	public Tasks(CacheManager manager) {
		setManager(manager);
	}

	@Scheduled(fixedDelay = 1200 * 1000, initialDelay = 1200 * 1000)
	public void inactivateTechnicals() {
		getManager().getCacheNames().parallelStream().forEach(item -> Objects.requireNonNull(getManager().getCache(item)).clear());
	}
}
