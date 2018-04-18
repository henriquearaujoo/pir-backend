package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.models.FileData;
import com.samsung.fas.pir.persistence.repositories.IFile;
import com.samsung.fas.pir.rest.dto.FileDTO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FileBO {
	private			final IFile repository;
	private static	final 	String				path			= System.getProperty("user.home") + "/RDService/Data";

	@Autowired
	public FileBO(IFile repository) {
		this.repository	= repository;
	}

	public FileDTO save(String name, String contentType, byte[] data) {
		try {
			String				extension		= FilenameUtils.getExtension(name);
			File 				fileLocation	= getNewFile(extension);
			FileOutputStream 	fos				= new FileOutputStream(fileLocation);
			FileData 			fileData		= new FileData();

			fos.write(data);
			fos.close();
			fileData.setPath(Paths.get("", fileLocation.getName()).toString());
			fileData.setExtension(extension);
			fileData.setName(name);
			fileData.setContent(contentType);
			fileData.setCreatedAt(new Date());
			return new FileDTO(repository.save(fileData));
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
			throw new RESTException("file.internal.error");

		return file;
	}

	private String getUploadDir() {
		if(!new File(path).exists())
			if (!new File(path).mkdirs())
				throw new RESTException("file.internal.error");
		return path;
	}

	public boolean delete(long fileid) {
		try {
			FileData	metadata	= repository.findById(fileid).orElseThrow(() -> new RESTException("not.found"));
			boolean 	deleted 	= new File(getUploadDir(), metadata.getPath()).delete();

			if (deleted)
				repository.deleteById(metadata.getId());

			return deleted;
		} catch (Exception e) {
			return false;
		}
	}

	public FileDTO findOne(long fileid) {
		return new FileDTO(repository.findById(fileid).orElseThrow(() -> new RESTException("not.found")));
	}

	public File getFile(FileDTO file) {
		return new File(getUploadDir(), file.getPath());
	}
}
