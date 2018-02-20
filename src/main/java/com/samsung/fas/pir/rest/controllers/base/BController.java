package com.samsung.fas.pir.rest.controllers.base;

import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.base.IBaseDAO;
import com.samsung.fas.pir.rest.dto.IReadDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(name = "Common Services", description = "Common methods for REST API", group = "Common", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
public abstract class BController<TEntity, TCreate, TRead extends IReadDTO<TEntity, TRead>, TUpdate, TDAO extends IBaseDAO<TEntity, Long>> {
	protected BService<TEntity, TCreate, TRead, TUpdate, TDAO, Long> service;

	public BController(BService<TEntity, TCreate, TRead, TUpdate, TDAO, Long> service) {
		this.service = service;
	}

	@ApiMethod(description="GET Detailed Data")
	@RequestMapping(method= RequestMethod.GET, path = "/{id}")
	@ResponseBody
	public ResponseEntity<?> findOne(@ApiPathParam @PathVariable("id") String id) {
		return ResponseEntity.ok(service.findOne(IDCoder.decode(id)));
	}

	@ApiMethod(description="GET All Data")
	@RequestMapping(method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@ApiMethod(description="GET All Data (Pageable)")
	@RequestMapping(method= RequestMethod.GET, path = "/page")
	@ResponseBody
	public ResponseEntity<?> findAll(Pageable pageable) {
		return ResponseEntity.ok(service.findAll(pageable));
	}

	@ApiMethod(description="CREATE Data")
	@RequestMapping(method= RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> save(@ApiBodyObject @RequestBody @Valid TCreate dto, @AuthenticationPrincipal Account account) {
		return ResponseEntity.ok(service.save(dto, account));
	}

	@ApiMethod(description="UPDATE Data")
	@RequestMapping(method= RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@ApiBodyObject @RequestBody @Valid TUpdate dto, @AuthenticationPrincipal Account account) {
		return ResponseEntity.ok(service.update(dto, account));
	}
}
