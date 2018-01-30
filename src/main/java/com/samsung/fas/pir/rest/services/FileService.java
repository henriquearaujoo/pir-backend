package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.models.entity.MDataFile;
import com.samsung.fas.pir.persistence.models.enums.EMediaType;
import com.samsung.fas.pir.persistence.repository.IFileRepository;
import com.samsung.fas.pir.rest.dto.FileDTO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FileService {
	private	final 	String 				UPLOAD_DIR		= "/uploads/";
	private	final	String 				STATIC_DIR		= "static/files";
	private 		IFileRepository 	repository;
	private			HttpServletRequest	request;

	@Autowired
	public FileService(IFileRepository repository, HttpServletRequest request) {
		this.repository		= repository;
		this.request		= request;
	}

	public FileDTO writeFile(String name, EMediaType type, byte[] data) {
		try {
			String				extension		= FilenameUtils.getExtension(name);
			File				fileLocation	= getNewFile(extension);
			FileOutputStream	fos				= new FileOutputStream(fileLocation);
			MDataFile 			metadata		= new MDataFile();

			validateFile(name, type);
			fos.write(data);
			fos.close();
			metadata.setPath(Paths.get(STATIC_DIR, fileLocation.getName()).toString());
			metadata.setExtension(extension);
			metadata.setName(name);
			metadata.setType(type);
			metadata.setCreatedAt(new Date());
			return FileDTO.toDTO(repository.save(metadata));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RESTRuntimeException("file.error");
		}
	}

	private void validateFile(String name, EMediaType type) {
		switch (type) {
			case PICTURE2D:
				break;

			case PICTURE360:
				if(!name.matches("(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$"))
					throw new RESTRuntimeException("file.format.unsupported");
				break;

			case VIDEO2D:
				break;

			case VIDEO360:
				if(!name.matches("(.*/)*.+\\.(mp4|MP4)$"))
					throw new RESTRuntimeException("file.format.unsupported");
				break;
		}
	}

	private File getNewFile(String ext) throws IOException {
		if(ext == null || ext.trim().isEmpty()) {
			ext = ".dat";
		}

		String	name	= String.format("%s.%s", java.util.UUID.randomUUID(), ext);
		File	dir		= new File(getUploadDir(), STATIC_DIR);
		File	file	= new File(dir, name);

		if (!dir.mkdirs() && !file.createNewFile())
			throw new RESTRuntimeException("file.internal.error");

		return file;
	}

	private String getUploadDir() {
		String	uploadPath	=  request.getServletContext().getRealPath(UPLOAD_DIR);

		if(!new File(uploadPath).exists())
			if (!new File(uploadPath).mkdir())
				throw new RESTRuntimeException("file.internal.error");

		return uploadPath;
	}

	public FileDTO getFile(long fileId) {
		return FileDTO.toDTO(repository.findOne(fileId));
	}

	public File getFile(FileDTO file) {
		return new File(getUploadDir(), file.getPath());
	}
}
