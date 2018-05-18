package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.*;
import com.samsung.fas.pir.persistence.models.FileData;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@DTO(FileData.class)
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
	@JsonProperty(value="content")
	private		String		content;

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

	public FileDTO(FileData entity) {
		id			= entity.getId();
		path		= entity.getPath();
		name		= entity.getName();
		extension	= entity.getExtension();
		creation	= entity.getCreatedAt();
		content		= entity.getContent();
	}

	@JsonIgnore
	public FileData getModel() {
		FileData model = new FileData();
		model.setId(id);
		model.setName(name);
		model.setExtension(extension);
		model.setPath(path);
		model.setCreatedAt(creation);
		model.setContent(content);
		return model;
	}
}
