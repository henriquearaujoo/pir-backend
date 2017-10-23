package com.samsung.fas.pir.rest.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samsung.fas.pir.dto.CityDTO;
import com.samsung.fas.pir.dto.StateDTO;
import com.samsung.fas.pir.service.StateCityService;

@Controller
@RequestMapping("/states")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin
public class StateCityRest {
	@Autowired
	private		StateCityService	scservice;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<StateDTO>> getAll() {
		return ResponseEntity.ok(scservice.findAllStates());
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}/")
	@ResponseBody
	public ResponseEntity<List<CityDTO>> getAllCitiesByState(@PathVariable("id") long id) {
		return ResponseEntity.ok(scservice.findAllCitiesByState(id));
	}
	
	/***********************************************************************************/
	// Exceptions
	/***********************************************************************************/
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
	    List<FieldError> 	fieldErrors 	= e.getBindingResult().getFieldErrors();
	    Set<String>			errors			= new HashSet<>();
	    fieldErrors.forEach((error) -> errors.add(error.getDefaultMessage()));
	    return ResponseEntity.badRequest().body(errors);
    }
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleException(HttpMessageNotReadableException e) {
		return ResponseEntity.badRequest().body(e.getRootCause().getMessage());
    }
	
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(RuntimeException e) {
		e.printStackTrace();
		return ResponseEntity.badRequest().body(e.getMessage());
    }
}
