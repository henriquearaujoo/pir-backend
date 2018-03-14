package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.QuestionTBDAO;
import com.samsung.fas.pir.persistence.models.entity.QuestionTB;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.FormQuestionTBDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Controller
@RequestMapping(value = "/rest/forms/questions/btype", produces = MediaType.APPLICATION_JSON)
@Api(value = "Form Questions", description = "REST Controller for Form Questions", tags = "FORM QUESTIONS TYPE B")
public class QuestionTBController extends BController<QuestionTB, FormQuestionTBDTO, QuestionTBDAO> {
	@Autowired
	public QuestionTBController(BService<QuestionTB, FormQuestionTBDTO, QuestionTBDAO, Long> service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<Collection<FormQuestionTBDTO>> search(@QuerydslPredicate(root = QuestionTB.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<FormQuestionTBDTO>> search(@QuerydslPredicate(root = QuestionTB.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}
}
