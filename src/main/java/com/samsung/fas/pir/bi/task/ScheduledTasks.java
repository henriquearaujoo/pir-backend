package com.samsung.fas.pir.bi.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ScheduledTasks {
	@Scheduled(cron="0 0 0 * * *")
	public void copyData() {
		System.out.println("COPY");
	}
}