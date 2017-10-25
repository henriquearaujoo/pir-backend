package com.samsung.fas.pir.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.Rule;

import lombok.Getter;
import lombok.Setter;

public class RuleDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID			id;
	
	@Getter
	@Setter
	@JsonProperty("profile_id")
	@NotNull(message="rule.profile.missing")
	private		UUID			profile;
	
	@Getter
	@Setter
	@JsonProperty("page_id")
	@NotNull(message="rule.page.missing")
	private		UUID			page;
	
	@Getter
	@Setter
	@JsonProperty("created_by")
	private		UUID			whoCreated;
	
	@Getter
	@Setter
	@JsonProperty("updated_by")
	private		UUID			whoUpdated;
	
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
	
	private RuleDTO(Rule entity) {
		id			= entity.getId();
		profile		= entity.getProfile().getId();
		page		= entity.getPage().getId();
		whoCreated	= entity.getWhoCreated().getId();
		whoUpdated	= entity.getWhoUpdated().getId();
		create		= entity.isCreate();
		read		= entity.isRead();
		update		= entity.isUpdate();
		delete		= entity.isDelete();
	}
	
	public RuleDTO() {
		// JSON
	}
	
	@JsonIgnore
	public Rule getModel() {
		Rule rule = new Rule();
		rule.setId(id);
		rule.setCreate(create);
		rule.setDelete(delete);
		rule.setRead(read);
		rule.setUpdate(update);
		return rule;
	}
	
	public static RuleDTO toDTO(Rule entity) {
		if(entity != null) 
			return new RuleDTO(entity);
		return null;
	}

}
