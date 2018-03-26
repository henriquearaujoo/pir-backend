package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.FormQuestionDAO;
import com.samsung.fas.pir.persistence.models.FormQuestion;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.FormQuestionDTO;
import com.samsung.fas.pir.rest.services.FormQuestionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;

@Api(value = "Form Questions", description = "REST Controller for Form Questions", tags = "FORM QUESTIONS")
@RequestMapping(value = "/rest/forms/questions", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class FormQuestionController extends BController<FormQuestion, FormQuestionDTO, FormQuestionDAO> {
	@Autowired
	public FormQuestionController(FormQuestionService service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<FormQuestionDTO>> search(@QuerydslPredicate(root = FormQuestion.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<FormQuestionDTO>> search(@QuerydslPredicate(root = FormQuestion.class) Predicate predicate, @PageableDefault(sort = {"id"}) Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}
}
