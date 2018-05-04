package com.samsung.fas.pir.rest.controllers.base;

import com.samsung.fas.pir.rest.services.base.IBaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
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

	@RequestMapping(method= RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> findOne(@PathVariable("id") UUID id, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findOne(id, details));
	}

	@RequestMapping(method= RequestMethod.GET)
	public ResponseEntity<?> findAll(@ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(details));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/page")
	public ResponseEntity<?> findAll(Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(pageable, details));
	}

	@RequestMapping(method= RequestMethod.POST)
	public ResponseEntity<DTO> save(@RequestBody @Valid DTO dto, @ApiIgnore @AuthenticationPrincipal UserDetails account) {
		return ResponseEntity.ok(service.save(dto, account));
	}

	@RequestMapping(method= RequestMethod.PUT)
	public ResponseEntity<DTO> update(@RequestBody @Valid DTO dto, @ApiIgnore @AuthenticationPrincipal UserDetails account) {
		return ResponseEntity.ok(service.update(dto, account));
	}

	@RequestMapping(method= RequestMethod.POST, path = "/collection")
	public ResponseEntity<Collection<DTO>> save(@RequestBody @Valid Collection<DTO> collection, @ApiIgnore @AuthenticationPrincipal UserDetails account) {
		return ResponseEntity.ok(service.save(collection, account));
	}

	@RequestMapping(method= RequestMethod.PUT, path = "/collection")
	public ResponseEntity<Collection<DTO>> update(@RequestBody @Valid Collection<DTO> collection, @ApiIgnore @AuthenticationPrincipal UserDetails account) {
		return ResponseEntity.ok(service.update(collection, account));
	}
}

