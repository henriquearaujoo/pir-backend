package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.QuestionDAO;
import com.samsung.fas.pir.persistence.models.entity.Question;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.question.CRUQuestionDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.UUID;

@Controller
@RequestMapping(value = "/rest/chapters/conclusion/questions", produces = MediaType.APPLICATION_JSON)
@Api(value = "Chapter Conclusion Questions", description = "REST Controller for Chapter Conclusion Questions", tags = "CHAPTER CONCLUSION QUESTIONS")
public class QuestionController extends BController<Question, CRUQuestionDTO, QuestionDAO> {
	@Autowired
	public QuestionController(BService<Question, CRUQuestionDTO, QuestionDAO, Long> service) {
		super(service);
	}

	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ResponseEntity<Collection<CRUQuestionDTO>> search(@QuerydslPredicate(root = Question.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	@ResponseBody
	public ResponseEntity<Page<CRUQuestionDTO>> search(@QuerydslPredicate(root = Question.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	@ResponseBody
	public ResponseEntity delete(@PathVariable("id") UUID codedid) {
		service.delete(codedid);
		return ResponseEntity.ok(null);
	}
}
