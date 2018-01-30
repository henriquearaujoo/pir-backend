package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.*;
import com.samsung.fas.pir.persistence.models.entity.MDataFile;
import com.samsung.fas.pir.persistence.models.enums.EMediaType;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;

import java.util.Date;

@ApiObject(name = "FileDTO", group = "Files", description = "Read a uploaded file metadata")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDTO {
	@Getter
	@Setter
	@JsonProperty(value="id")
	private		long		id;

	@Getter
	@Setter
	@JsonProperty(value="path")
	private		String		path;

	@Getter
	@Setter
	@JsonProperty(value="name")
	private		String		name;

	@Getter
	@Setter
	@JsonProperty(value="extension")
	private		String		extension;

	@Getter
	@Setter
	@JsonProperty(value="type")
	private		String		type;

	@Getter
	@Setter
	@JsonProperty(value="media_type")
	private		String		media;

	@Getter
	@Setter
	@JsonProperty(value="storage_type")
	private		String		storage;

	@Getter
	@Setter
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@JsonProperty(value="created_at")
	private 	Date 		creation;

	public FileDTO() {
		super();
	}

	private FileDTO(MDataFile entity) {
		id			= entity.getId();
		path		= entity.getPath();
		name		= entity.getName();
		extension	= entity.getExtension();
		creation	= entity.getCreatedAt();
		type		= entity.getType().toString();
	}

	@JsonIgnore
	public MDataFile getModel() {
		MDataFile model = new MDataFile();
		model.setId(id);
		model.setName(name);
		model.setExtension(extension);
		model.setPath(path);
		model.setCreatedAt(creation);
		model.setType(EMediaType.parse(type));
		return model;
	}

	public static FileDTO toDTO(MDataFile entity) {
		if (entity != null)
			return new FileDTO(entity);
		return null;
	}
}
