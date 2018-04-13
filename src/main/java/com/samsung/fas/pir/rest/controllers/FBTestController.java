package com.samsung.fas.pir.rest.controllers;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Firebase Test", description = "Firebase Test Controller", tags = "FIREBASE")
@RequestMapping("/rest/firebase")
@RestController
public class FBTestController {
//	@Getter(AccessLevel.PRIVATE)
//	@Setter(AccessLevel.PRIVATE)
//	private		FCMHelper		fcmHelper;
//
//	@Autowired
//	public FBTestController(FCMHelper helper) {
//		setFcmHelper(helper);
//	}
//
//	@RequestMapping(value = "/test", method = RequestMethod.GET)
//	public void test() throws IOException {
//		JsonObject jsonObject = new JsonObject();
//		jsonObject.addProperty("test", "test");
//		fcmHelper.send(null);
//	}
}
