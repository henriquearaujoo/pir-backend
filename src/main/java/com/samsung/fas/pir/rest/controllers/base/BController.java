package com.samsung.fas.pir.rest.controllers.base;

import com.samsung.fas.pir.persistence.dao.base.IBaseDAO;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

public abstract class BController<TEntity, TDTO, TDAO extends IBaseDAO<TEntity, Long>> {
	protected final BService<TEntity, TDTO, TDAO, Long> service;

	public BController(BService<TEntity, TDTO, TDAO, Long> service) {
		this.service = service;
	}

	@RequestMapping(method= RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> findOne(@PathVariable("id") String id, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findOne(IDCoder.decode(id), details));
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
	public ResponseEntity<?> save(@RequestBody @Valid TDTO dto, @ApiIgnore @AuthenticationPrincipal Account account) {
		return ResponseEntity.ok(service.save(dto, account));
	}

	@RequestMapping(method= RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody @Valid TDTO dto, @ApiIgnore @AuthenticationPrincipal Account account) {
		return ResponseEntity.ok(service.update(dto, account));
	}
}
