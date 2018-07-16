package com.samsung.fas.pir.rest.controllers.base;

import com.samsung.fas.pir.rest.services.base.IBaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

public abstract class BController<BO extends IBaseBO<?, DTO, Long>, DTO> {
	@Getter(AccessLevel.PROTECTED)
	private	final	BO	service;

	public BController(BO service) {
		this.service 	= service;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> findOne(@PathVariable("id") UUID id, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findOne(id, device, details));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAll(Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(device, details));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/page")
	public ResponseEntity<?> findAll(Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(pageable, device, details));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DTO> save(@RequestBody @Valid DTO dto, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails account) {
		return ResponseEntity.ok(service.save(dto, device, account));
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DTO> update(@RequestBody @Valid DTO dto, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails account) {
		return ResponseEntity.ok(service.update(dto, device, account));
	}

	@RequestMapping(method = RequestMethod.POST, path = "/collection", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<DTO>> save(@RequestBody @Valid Collection<DTO> collection, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails account) {
		return ResponseEntity.ok(service.save(collection, device, account));
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/collection", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<DTO>> update(@RequestBody @Valid Collection<DTO> collection, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails account) {
		return ResponseEntity.ok(service.update(collection, device, account));
	}
}

