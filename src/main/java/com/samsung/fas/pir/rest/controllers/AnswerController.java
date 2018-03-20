package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.AnswerDAO;
import com.samsung.fas.pir.persistence.models.entity.Answer;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.AnswerDTO;
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

@Api(value = "Chapter Conclusion Question Answers", description = "REST Controller for Chapter Conclusion Question Answers", tags = "CHAPTER CONCLUSION QUESTION ANSWERS")
@Controller
@RequestMapping(value = "/rest/chapters/conclusion/question/answer", produces = MediaType.APPLICATION_JSON)
@ResponseBody
public class AnswerController extends BController<Answer, AnswerDTO, AnswerDAO> {
	@Autowired
	public AnswerController(BService<Answer, AnswerDTO, AnswerDAO, Long> service) {
		super(service);
	}

	@RequestMapping(method=RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<AnswerDTO>> search(@QuerydslPredicate(root = Answer.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method=RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<AnswerDTO>> search(@QuerydslPredicate(root = Answer.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity delete(@PathVariable("id") UUID codedid) {
		service.delete(codedid);
		return ResponseEntity.ok(null);
	}
}
