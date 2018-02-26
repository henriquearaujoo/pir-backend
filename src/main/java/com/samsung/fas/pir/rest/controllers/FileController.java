package com.samsung.fas.pir.rest.controllers;

import com.samsung.fas.pir.rest.dto.FileDTO;
import com.samsung.fas.pir.rest.services.FileService;
import com.samsung.fas.pir.utils.MultipartFileSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

@Controller
@RequestMapping("/rest/file")
public class FileController {
	private final FileService service;

	@Autowired
	public FileController(FileService service) {
		this.service = service;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile[] files) throws IOException {
		Collection<FileDTO> response = new HashSet<>();

		for (MultipartFile file : files) {
			response.add(service.save(file.getOriginalFilename(), file.getContentType(), file.getBytes()));
		}

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/download/{fileid}", method = RequestMethod.GET)
	public void download(@PathVariable("fileid") long fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FileDTO					metadata		= service.findOne(fileId);
		File 					file			= service.getFile(metadata);

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
