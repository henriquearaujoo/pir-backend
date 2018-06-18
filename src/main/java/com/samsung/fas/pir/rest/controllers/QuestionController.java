package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.Question;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.QuestionDTO;
import com.samsung.fas.pir.rest.services.QuestionBO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;
import java.util.UUID;

@Api(value = "Chapter Conclusion Questions", description = "REST Controller for Chapter Conclusion Questions", tags = "CHAPTER CONCLUSION QUESTIONS")
@RequestMapping(value = "/rest/chapters/conclusion/questions", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class QuestionController extends BController<QuestionBO, QuestionDTO> {
	@Autowired
	public QuestionController(QuestionBO service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<QuestionDTO>> search(@QuerydslPredicate(root = Question.class) Predicate predicate, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<QuestionDTO>> search(@QuerydslPredicate(root = Question.class) Predicate predicate, Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, device, details));
	}

	@RequestMapping(method= RequestMethod.DELETE, value="/{id}")
	public ResponseEntity delete(@PathVariable("id") UUID codedid) {
		return ResponseEntity.ok(getService().delete(codedid));
	}
}
