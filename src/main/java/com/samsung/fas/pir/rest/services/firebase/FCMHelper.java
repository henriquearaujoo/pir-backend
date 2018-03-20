package com.samsung.fas.pir.rest.services.firebase;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.JsonObject;
import com.samsung.fas.pir.configuration.properties.FCMProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FCMHelper {
	@Setter(AccessLevel.PRIVATE)
	@Getter(AccessLevel.PRIVATE)
	private			FCMProperties		properties;

	@Setter(AccessLevel.PRIVATE)
	@Getter(AccessLevel.PRIVATE)
	private 		ApplicationContext	context;

	@Autowired
	public FCMHelper(FCMProperties properties, ApplicationContext context) {
		setProperties(properties);
		setContext(context);
	}

	public String sendNotification(EReceiverType type, String typeParameter, JsonObject notificationObject) throws IOException {
		return sendNotifictaionAndData(type, typeParameter, notificationObject, null);
	}

	public String sendData(EReceiverType type, String typeParameter, JsonObject dataObject) throws IOException {
		return sendNotifictaionAndData(type, typeParameter, null, dataObject);
	}

	public String sendNotifictaionAndData(EReceiverType type, String typeParameter, JsonObject notificationObject, JsonObject dataObject) throws IOException {
		JsonObject sendObject = new JsonObject();
		sendObject.addProperty(type.getValue(), typeParameter);
		return sendFcmMessage(sendObject, notificationObject, dataObject);
	}

	public String sendTopicData(String topic, JsonObject dataObject) throws IOException{
		return sendData(EReceiverType.UNIQUE, "/topics/" + topic, dataObject);
	}

	public String sendTopicNotification(String topic, JsonObject notificationObject) throws IOException{
		return sendNotification(EReceiverType.UNIQUE, "/topics/" + topic, notificationObject);
	}

	public String sendTopicNotificationAndData(String topic, JsonObject notificationObject, JsonObject dataObject) throws IOException{
		return sendNotifictaionAndData(EReceiverType.UNIQUE, "/topics/" + topic, notificationObject, dataObject);
	}

	private String sendFcmMessage(JsonObject sendObject, JsonObject notificationObject, JsonObject dataObject) throws IOException {
		HttpPost				httpPost		= new HttpPost(getProperties().getServerUrl());
		HttpClient 				httpClient		= HttpClientBuilder.create().build();
		BasicResponseHandler	responseHandler = new BasicResponseHandler();

		httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());

		if (notificationObject != null)
			sendObject.add("notification", notificationObject);

		if (dataObject != null)
			sendObject.add("data", dataObject);

		String			data	= sendObject.toString();
		StringEntity	entity	= new StringEntity(data);

		httpPost.setEntity(entity);

		return httpClient.execute(httpPost, responseHandler);
	}

	private String getAccessToken() throws IOException {
		InputStream			input		= getContext().getResource("classpath:firebase-admin.json").getInputStream();
		GoogleCredential 	credential	= GoogleCredential.fromStream(input).createScoped(Collections.singletonList("https://www.googleapis.com/auth/firebase.messaging"));
		credential.refreshToken();
		return credential.getAccessToken();
	}
}
