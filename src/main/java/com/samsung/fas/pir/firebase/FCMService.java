package com.samsung.fas.pir.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.samsung.fas.pir.configuration.properties.FCMProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

//@Service
class FCMService {
	private	static		Logger		Log		= LoggerFactory.getLogger(FCMService.class);

	@Autowired
	FCMService(FCMProperties properties) {
		try (InputStream serviceAccount = Files.newInputStream(Paths.get(properties.getServerFile()))) {
			FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
			FirebaseApp.initializeApp(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void pushToTopic(Map<String, String> data, String collapseKey, String tag, String category, String thread, String toTopic, String notificationTitle, String notificationMessage) throws InterruptedException, ExecutionException {
		AndroidConfig	androidConfig	= AndroidConfig.builder().setTtl(Duration.ofDays(90).toMillis()).setCollapseKey(collapseKey).setPriority(AndroidConfig.Priority.HIGH).setNotification(AndroidNotification.builder().setTag(tag).build()).build();
		ApnsConfig		apnsConfig		= ApnsConfig.builder().setAps(Aps.builder().setCategory(category).setThreadId(thread).build()).build();
		Message			message			= Message.builder().putAllData(data).setTopic(toTopic).setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(new Notification(notificationTitle, notificationMessage)).build();
		String			response		= FirebaseMessaging.getInstance().sendAsync(message).get();
		Log.debug(response);
	}

	void pushToDevice(Map<String, String> data, String collapseKey, String tag, String category, String thread, String toClient, String notificationTitle, String notificationMessage) throws InterruptedException, ExecutionException {
		AndroidConfig	androidConfig	= AndroidConfig.builder().setTtl(Duration.ofDays(90).toMillis()).setCollapseKey(collapseKey).setPriority(AndroidConfig.Priority.HIGH).setNotification(AndroidNotification.builder().setTag(tag).build()).build();
		ApnsConfig		apnsConfig		= ApnsConfig.builder().setAps(Aps.builder().setCategory(category).setThreadId(thread).build()).build();
		Message			message			= Message.builder().putAllData(data).setToken(toClient).setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(new Notification(notificationTitle, notificationMessage)).build();
		String			response		= FirebaseMessaging.getInstance().sendAsync(message).get();
		Log.debug(response);
	}
}
