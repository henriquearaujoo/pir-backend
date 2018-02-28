package com.samsung.fas.pir.rest.dto.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRURuleDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private 	String 			id;

	@Getter
	@Setter
	@JsonProperty("profile_id")
	private		String			profileID;

	@Getter
	@Setter
	@JsonProperty("page_id")
	private		String			page;

	@Getter
	@Setter
	@JsonProperty("create")
	private		boolean			create;

	@Getter
	@Setter
	@JsonProperty("read")
	private		boolean			read;

	@Getter
	@Setter
	@JsonProperty("update")
	private		boolean			update;

	@Getter
	@Setter
	@JsonProperty("delete")
	private		boolean			delete;

	public CRURuleDTO() {
		super();
	}

	public CRURuleDTO(Rule rule) {
		setId(IDCoder.encode(rule.getUuid()));
		setProfileID(IDCoder.encode(rule.getProfile().getUuid()));
		setPage(IDCoder.encode(rule.getPage().getUuid()));
		setCreate(rule.canCreate());
		setRead(rule.canRead());
		setUpdate(rule.canUpdate());
		setDelete(rule.canDelete());
	}

	@JsonIgnore
	public Rule getModel() {
		Rule rule = new Rule();
		rule.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		rule.canCreate(isCreate());
		rule.canRead(isRead());
		rule.canUpdate(isUpdate());
		rule.canDelete(isDelete());
		return rule;
	}
}
