package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Rule;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

@DTO(Rule.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleDTO extends BaseDTO<Rule> {
	@Getter
	@Setter
	@JsonProperty("create")
	private		boolean		canCreate;

	@Getter
	@Setter
	@JsonProperty("read")
	private		boolean		canRead;

	@Getter
	@Setter
	@JsonProperty("update")
	private		boolean		canUpdate;

	@Getter
	@Setter
	@JsonProperty("delete")
	private		boolean		canDelete;

	@Getter
	@Setter
	@JsonProperty("profile")
	private		ProfileDTO	profileDTO;

	@Getter
	@Setter
	@JsonProperty("page")
	private 	PageDTO 	pageDTO;

	public RuleDTO() {
		super();
	}

	public RuleDTO(Rule rule, Device device, boolean detailed) {
		super(rule);
		setPageDTO(new PageDTO(rule.getPage(), device, false));
		setProfileDTO(new ProfileDTO(rule.getProfile(), false));
	}
}
