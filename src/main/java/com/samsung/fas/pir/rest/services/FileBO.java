package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.ServiceException;
import com.samsung.fas.pir.persistence.enums.EMediaType;
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
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FileBO {
	private			IFile				repository;

	@Autowired
	public FileBO(IFile repository) {
		this.repository		= repository;
	}

	public FileDTO save(String name, String contentType, boolean song, boolean resource, byte[] data) {
		try {
			String				extension		= FilenameUtils.getExtension(name);
			File 				fileLocation	= getNewFile();
			FileOutputStream 	fos				= new FileOutputStream(fileLocation);
			FileData 			fileData		= new FileData();

//			validateFile(name, type);

			fos.write(data);
			fos.close();

			fileData.setPath(Paths.get("", fileLocation.getName()).toString());
			fileData.setExtension(extension);
			fileData.setName(name);
			fileData.setContent(contentType);
			fileData.setCreatedAt(new Date());
			fileData.setSong(song);
			fileData.setResource(resource);
			return new FileDTO(repository.save(fileData));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("file.error");
		}
	}

	private void validateFile(String name, EMediaType type) {
		switch (type) {
			case PICTURE2D:
				break;

			case PICTURE360:
				if(!name.matches("(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$"))
					throw new ServiceException("file.format.unsupported");
				break;

			case VIDEO2D:
				break;

			case VIDEO360:
				if(!name.matches("(.*/)*.+\\.(mp4|MP4)$"))
					throw new ServiceException("file.format.unsupported");
				break;
		}
	}

	private File getNewFile() throws IOException {
		String	name	= String.format("%s", java.util.UUID.randomUUID());
		File	dir		= new File(getUploadDir());
		File	file	= new File(dir, name);

		if (!dir.mkdirs() && !file.createNewFile())
			throw new ServiceException("file.internal.error");

		return file;
	}

	private String getUploadDir() {
//		String uploadPath = new File("/files/pir").getAbsolutePath();
		String uploadPath = new File(System.getProperty("user.home") + "/PIRService", "/Data").getAbsolutePath();

		if(!new File(uploadPath).exists())
			if (!new File(uploadPath).mkdir())
				throw new ServiceException("file.internal.error");

		return uploadPath;
	}

	public FileDTO findOne(long fileId) {
		return new FileDTO(repository.findById(fileId).orElseThrow(() -> new ServiceException("not.found")));
	}

	public File getFile(FileDTO file) {
		return new File(getUploadDir(), file.getPath());
	}

	public void delete(long fileId) {
		repository.deleteById(fileId);
	}

	public Collection<FileDTO> findAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).map(FileDTO::new).collect(Collectors.toList());
	}

	public Collection<FileDTO> findAllSongs() {
		return repository.findAllBySong(true).stream().map(FileDTO::new).collect(Collectors.toList());
	}

	public Collection<FileDTO> findAllResources() {
		return repository.findAllByResource(true).stream().map(FileDTO::new).collect(Collectors.toList());
	}
}
