package com.samsung.fas.pir.rest.controllers;

import com.samsung.fas.pir.rest.controllers.utils.MultipartFileSender;
import com.samsung.fas.pir.rest.dto.FileDTO;
import com.samsung.fas.pir.rest.services.FileBO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

@Api(value = "FILES", description = "REST Controller for FILES", tags = "FILES")
@RequestMapping("/rest/file")
@RestController
public class FileController {
	private final FileBO service;

	@Autowired
	public FileController(FileBO service) {
		this.service = service;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> findAll() throws IOException {
		return ResponseEntity.ok(service.findAll());
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile[] files) throws IOException {
		Collection<FileDTO> response = new HashSet<>();

		for (MultipartFile file : files) {
			response.add(service.save(file.getOriginalFilename(), file.getContentType(), false, false, file.getBytes()));
		}

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/download/{fileid}", method = RequestMethod.GET)
	public void download(@PathVariable("fileid") long fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FileDTO metadata		= service.findOne(fileId);
		File 	file			= service.getFile(metadata);

		response.setContentType(metadata.getContent());
		response.setHeader("Content-Disposition", "inline; filename=" + metadata.getName());
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Access-Control-Allow-Origin", "*");
		MultipartFileSender.fromPath(file.toPath()).with(request).with(response).serveResource();
	}

	@RequestMapping(value = "/delete/{fileid}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("fileid") long fileId, HttpServletRequest request, HttpServletResponse response) {
		service.delete(fileId);
	}
}
