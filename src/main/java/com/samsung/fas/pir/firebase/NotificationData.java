package com.samsung.fas.pir.firebase;

import lombok.Getter;
import lombok.Setter;

public class NotificationData {
	@Getter
	@Setter
	private		String		collapseKey;

	@Getter
	@Setter
	private 	String		tag;

	@Getter
	@Setter
	private 	String		category;

	@Getter
	@Setter
	private 	String		thread;

	@Getter
	@Setter
	private 	String		topicOrDevice;

	@Getter
	@Setter
	private 	String		title;

	@Getter
	@Setter
	private 	String		message;
}
