package com.samsung.fas.pir.rest.controllers.base;

import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.base.IBaseDAO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiPathParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public abstract class BController<TEntity, TDTO, TDAO extends IBaseDAO<TEntity, Long>> {
	protected BService<TEntity, TDTO, TDAO, Long> service;

	public BController(BService<TEntity, TDTO, TDAO, Long> service) {
		this.service = service;
	}

	@RequestMapping(method= RequestMethod.GET, path = "/{id}")
	@ResponseBody
	public ResponseEntity<?> findOne(@ApiPathParam @PathVariable("id") String id) {
		return ResponseEntity.ok(service.findOne(IDCoder.decode(id)));
	}

	@RequestMapping(method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@RequestMapping(method= RequestMethod.GET, path = "/page")
	@ResponseBody
	public ResponseEntity<?> findAll(Pageable pageable) {
		return ResponseEntity.ok(service.findAll(pageable));
	}

	@RequestMapping(method= RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> save(@ApiBodyObject @RequestBody @Valid TDTO dto, @AuthenticationPrincipal Account account) {
		return ResponseEntity.ok(service.save(dto, account));
	}

	@RequestMapping(method= RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @RequestBody @Valid TDTO dto, @AuthenticationPrincipal Account account) {
		return ResponseEntity.ok(service.update(dto, account));
	}
}
