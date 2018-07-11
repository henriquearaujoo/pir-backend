package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Page;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import java.util.Collection;
import java.util.List;

@DTO(Page.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageDTO extends BaseDTO<Page> {
	@Getter
	@Setter
	@JsonProperty("title")
	private		String				title;

	@Getter
	@Setter
	@JsonProperty("route")
	private		String				url;

	@Getter
	@Setter
	@JsonProperty("rules")
	private 	List<RuleDTO> 		rulesDTO;

	public PageDTO() {
		super();
	}

	public PageDTO(Page entity, Device device, boolean detailed) {
		super(entity);
	}
}
