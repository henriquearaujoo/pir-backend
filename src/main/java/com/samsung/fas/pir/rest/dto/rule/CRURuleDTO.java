package com.samsung.fas.pir.rest.dto.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import com.samsung.fas.pir.rest.dto.page.CRUPageDTO;
import com.samsung.fas.pir.rest.dto.profile.CRUProfileDTO;
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

	@Getter
	@Setter
	@JsonProperty("profile")
	private 	CRUProfileDTO 	profile;

	@Getter
	@Setter
	@JsonProperty("page")
	private 	CRUPageDTO 		page;

	public CRURuleDTO() {
		super();
	}

	public CRURuleDTO(Rule rule, boolean detailed) {
		setId(IDCoder.encode(rule.getUuid()));
		setCreate(rule.canCreate());
		setRead(rule.canRead());
		setUpdate(rule.canUpdate());
		setDelete(rule.canDelete());
		setPage(new CRUPageDTO(rule.getPage(), false));
		setProfile(new CRUProfileDTO(rule.getProfile(), false));
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
