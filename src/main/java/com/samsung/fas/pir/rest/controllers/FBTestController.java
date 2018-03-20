package com.samsung.fas.pir.rest.controllers;

import com.google.gson.JsonObject;
import com.samsung.fas.pir.rest.services.firebase.FCMHelper;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Api(value = "Firebase Test", description = "Firebase Test Controller", tags = "FIREBASE")
@Controller
@RequestMapping("/rest/firebase")
@ResponseBody
public class FBTestController {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		FCMHelper		fcmHelper;

	@Autowired
	public FBTestController(FCMHelper helper) {
		setFcmHelper(helper);
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() throws IOException {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("test", "test");
		return fcmHelper.sendTopicData("test", jsonObject);
	}
}
