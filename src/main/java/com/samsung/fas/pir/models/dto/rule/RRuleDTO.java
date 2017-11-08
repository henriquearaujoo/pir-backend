package com.samsung.fas.pir.models.dto.rule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Rule;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RRuleDTO {
	@ApiObjectField(name="id", order=0)
	@Getter
	@JsonProperty("id")
	private 	String 			id;

	@ApiObjectField(name="profile_id", order=1)
	@Getter
	@JsonProperty("profile_id")
	private		String			profile;

	@ApiObjectField(name="page_id", order=2)
	@Getter
	@JsonProperty("page_id")
	private		String			page;

	@ApiObjectField(name="create", order=5)
	@Getter
	@JsonProperty("create")
	private		boolean			create;

	@ApiObjectField(name="read", order=6)
	@Getter
	@JsonProperty("read")
	private		boolean			read;

	@ApiObjectField(name="update", order=7)
	@Getter
	@JsonProperty("update")
	private		boolean			update;

	@ApiObjectField(name="delete", order=8)
	@Getter
	@JsonProperty("delete")
	private		boolean			delete;

	private RRuleDTO(Rule entity) {
		id			= IDCoder.encode(entity.getGuid());
		profile		= IDCoder.encode(entity.getProfile().getGuid());
		page		= IDCoder.encode(entity.getPage().getGuid());
		create		= entity.isCreate();
		read		= entity.isRead();
		update		= entity.isUpdate();
		delete		= entity.isDelete();
	}

	public static RRuleDTO toDTO(Rule entity) {
		if(entity != null)
			return new RRuleDTO(entity);
		return null;
	}
}
