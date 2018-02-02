package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.models.entity.MDataFile;
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
	@Autowired
	private			HttpServletRequest	request;

	@Autowired
	private 		IFileRepository 	repository;

	public FileDTO save(String name, String contentType, byte[] data) {
		try {
			String				extension		= FilenameUtils.getExtension(name);
			File 				fileLocation	= getNewFile(extension);
			FileOutputStream 	fos				= new FileOutputStream(fileLocation);
			MDataFile 			metadata		= new MDataFile();

			fos.write(data);
			fos.close();
			metadata.setPath(Paths.get("/", fileLocation.getName()).toString());
			metadata.setExtension(extension);
			metadata.setName(name);
			metadata.setContent(contentType);
			metadata.setCreatedAt(new Date());
			return FileDTO.toDTO(repository.save(metadata));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private File getNewFile(String ext) throws IOException {
		String	name	= java.util.UUID.randomUUID().toString();
		File	dir		= new File(getUploadDir(), "/");
		File	file	= new File(dir, name);

		if (!dir.mkdirs() && !file.createNewFile())
			throw new RESTRuntimeException("file.internal.error");

		return file;
	}

	private String getUploadDir() {
		String	uploadPath	=  request.getServletContext().getRealPath("data/files");

		if(!new File(uploadPath).exists())
			if (!new File(uploadPath).mkdirs())
				throw new RESTRuntimeException("file.internal.error");

		return uploadPath;
	}

	public boolean delete(long fileid) {
		try {
			MDataFile	metadata	= repository.findOne(fileid);
			boolean 	deleted 	= new File(getUploadDir(), metadata.getPath()).delete();

			if (deleted)
				repository.delete(metadata.getId());

			return deleted;
		} catch (Exception e) {
			return false;
		}
	}

	public FileDTO findOne(long fileid) {
		return FileDTO.toDTO(repository.findOne(fileid));
	}

	public File getFile(FileDTO file) {
		return new File(getUploadDir(), file.getPath());
	}
}
