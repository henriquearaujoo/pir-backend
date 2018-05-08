package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.Rule;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID		uuid;

	@Getter
	@Setter
	@JsonProperty("create")
	private		boolean		create;

	@Getter
	@Setter
	@JsonProperty("read")
	private		boolean		read;

	@Getter
	@Setter
	@JsonProperty("update")
	private		boolean		update;

	@Getter
	@Setter
	@JsonProperty("delete")
	private		boolean		delete;

	@Getter
	@Setter
	@JsonProperty("profile")
	private		ProfileDTO	profile;

	@Getter
	@Setter
	@JsonProperty("page")
	private 	PageDTO 	page;

	public RuleDTO() {
		super();
	}

	public RuleDTO(Rule rule, boolean detailed) {
		setUuid(rule.getUuid());
		setCreate(rule.canCreate());
		setRead(rule.canRead());
		setUpdate(rule.canUpdate());
		setDelete(rule.canDelete());
		setPage(new PageDTO(rule.getPage(), false));
		setProfile(new ProfileDTO(rule.getProfile(), false));
	}

	@JsonIgnore
	public Rule getModel() {
		Rule rule = new Rule();
		rule.setUuid(getUuid());
		rule.canCreate(isCreate());
		rule.canRead(isRead());
		rule.canUpdate(isUpdate());
		rule.canDelete(isDelete());
		return rule;
	}
}
