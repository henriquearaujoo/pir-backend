package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.ChildDAO;
import com.samsung.fas.pir.persistence.models.entity.Child;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.child.CRUChildDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.MediaType;

@Controller
@RequestMapping(value = "/rest/child", produces = MediaType.APPLICATION_JSON)
public class ChildController extends BController<Child, CRUChildDTO, CRUChildDTO, CRUChildDTO, ChildDAO> {

	@Autowired
	public ChildController(BService<Child, CRUChildDTO, CRUChildDTO, CRUChildDTO, ChildDAO, Long> service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search")
	@ResponseBody
	public ResponseEntity<?> search(@QuerydslPredicate(root = Child.class) Predicate predicate) {
		return ResponseEntity.ok(service.findAll(predicate));
	}

	@RequestMapping(method= RequestMethod.GET, path = "/search/page")
	@ResponseBody
	public ResponseEntity<?> search(@QuerydslPredicate(root = Child.class) Predicate predicate, Pageable pageable) {
		return ResponseEntity.ok(service.findAll(predicate, pageable));
	}
}
