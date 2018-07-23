package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.Pregnancy;
import com.samsung.fas.pir.persistence.models.Pregnant;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.PregnancyDTO;
import com.samsung.fas.pir.rest.dto.PregnantDTO;
import com.samsung.fas.pir.rest.services.PregnancyBO;
import com.samsung.fas.pir.rest.services.PregnantBO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "Pregnant", description = "REST Controller for Pregnant", tags = "PREGNANT")
@RequestMapping(value = "/rest/pregnant", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class PregnantController extends BController<PregnantBO, PregnantDTO> {
	@Autowired
	public PregnantController(PregnantBO service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search")
	public ResponseEntity<?> search(@QuerydslPredicate(root = Pregnant.class) Predicate predicate, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search/page")
	public ResponseEntity<?> search(@QuerydslPredicate(root = Pregnant.class) Predicate predicate, Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, device, details));
	}
}
