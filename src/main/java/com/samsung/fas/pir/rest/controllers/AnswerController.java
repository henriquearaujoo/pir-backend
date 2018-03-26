package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.AnswerDAO;
import com.samsung.fas.pir.persistence.models.Answer;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.AnswerDTO;
import com.samsung.fas.pir.rest.services.AnswerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;
import java.util.UUID;

@Api(value = "Chapter Conclusion Question Answers", description = "REST Controller for Chapter Conclusion Question Answers", tags = "CHAPTER CONCLUSION QUESTION ANSWERS")
@RequestMapping(value = "/rest/chapters/conclusion/question/answer", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AnswerController extends BController<Answer, AnswerDTO, AnswerDAO> {
	@Autowired
	public AnswerController(AnswerService service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<AnswerDTO>> search(@QuerydslPredicate(root = Answer.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<AnswerDTO>> search(@QuerydslPredicate(root = Answer.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}

	@RequestMapping(method= RequestMethod.DELETE, value="/{id}")
	public ResponseEntity delete(@PathVariable("id") UUID codedid) {
		return ResponseEntity.ok(service.delete(codedid));
	}
}
