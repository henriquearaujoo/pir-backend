package com.samsung.fas.pir.rest.controllers;

import com.samsung.fas.pir.persistence.models.enums.EMediaType;
import com.samsung.fas.pir.rest.dto.FileDTO;
import com.samsung.fas.pir.rest.services.FileService;
import com.samsung.fas.pir.utils.MultipartFileSender;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/file")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET } )
public class FileController {
	@Autowired
	private FileService service;

	@RequestMapping(value="/upload/{mediaType}", method= RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<FileDTO> handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable("mediaType") String mediaType){
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				return new ResponseEntity<>(service.writeFile(file.getOriginalFilename(), EMediaType.parse(mediaType), bytes), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	@RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
	public void download(@PathVariable("fileId") long fileId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		FileDTO file = service.getFile(fileId);
		File entry = service.getFile(file);
		response.setHeader("Access-Control-Allow-Origin", "*");
		EMediaType t = EMediaType.parse(file.getType());
		if(t == EMediaType.VIDEO2D || t == EMediaType.VIDEO360) {
			try {
				MultipartFileSender.fromPath(entry.toPath()).with(request).with(response).serveResource();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if(file.getName().matches("(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$"))
				t = EMediaType.PICTURE2D;
			response.addHeader("Content-Disposition", String.format("inline; filename=\"%s\"", file.getName()));
			response.addHeader("Accept-Ranges", "bytes");
			switch (t) {
				case PICTURE2D:
				case PICTURE360:
					response.setContentType("image/" + file.getExtension());
					break;
				default:
					response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
					break;
			}
			IOUtils.copyLarge(new FileInputStream(entry), response.getOutputStream());
		}
	}

	public static boolean isNumeric(String str)
	{
		for (char c : str.toCharArray())
		{
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}
}
