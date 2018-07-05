package com.samsung.fas.pir.firebase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.fas.pir.rest.controllers.ExceptionController;
import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FCMPushService {
	private		static		Logger				Log			= LoggerFactory.getLogger(FCMPushService.class);

	@Getter(AccessLevel.PUBLIC)
	private		final		FCMService			service;

	@Autowired
	public FCMPushService(FCMService service) {
		this.service		= service;
	}

	@SuppressWarnings("unchecked")
	public void post(NotificationData notification, Object data, EDestination destination) {
		try {
			switch (destination) {
				case GROUP:
					getService().pushToTopic(new ObjectMapper().convertValue(data, Map.class), notification.getCollapseKey(), notification.getTag(), notification.getCategory(), notification.getThread(), notification.getTopicOrDevice(), notification.getTitle(), notification.getMessage());
					break;
				case DEVICE:
					getService().pushToDevice(new ObjectMapper().convertValue(data, Map.class), notification.getCollapseKey(), notification.getTag(), notification.getCategory(), notification.getThread(), notification.getTopicOrDevice(), notification.getTitle(), notification.getMessage());
					break;
				default:
				Log.error("Invalid Destination");
			}
		} catch (InterruptedException | ExecutionException e) {
			Log.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
