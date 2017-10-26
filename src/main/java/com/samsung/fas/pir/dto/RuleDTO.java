package com.samsung.fas.pir.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.Rule;

import lombok.Getter;
import lombok.Setter;

@ApiObject
public class RuleDTO {
	@ApiObjectField(name="id", required=false)
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID			id;
	
	@ApiObjectField(name="profile_id")
	@Getter
	@Setter
	@JsonProperty("profile_id")
	@NotNull(message="rule.profile.missing")
	private		UUID			profile;
	
	@ApiObjectField(name="page_id")
	@Getter
	@Setter
	@JsonProperty("page_id")
	@NotNull(message="rule.page.missing")
	private		UUID			page;
	
	@ApiObjectField(name="created_by")
	@Getter
	@Setter
	@JsonProperty("created_by")
	private		UUID			whoCreated;
	
	@ApiObjectField(name="updated_by")
	@Getter
	@Setter
	@JsonProperty("updated_by")
	private		UUID			whoUpdated;
	
	@ApiObjectField(name="create")
	@Getter
	@Setter
	@JsonProperty("create")
	private		boolean			create;
	
	@ApiObjectField(name="read")
	@Getter
	@Setter
	@JsonProperty("read")
	private		boolean			read;
	
	@ApiObjectField(name="update")
	@Getter
	@Setter
	@JsonProperty("update")
	private		boolean			update;
	
	@ApiObjectField(name="delete")
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
