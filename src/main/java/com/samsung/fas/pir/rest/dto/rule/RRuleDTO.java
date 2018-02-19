package com.samsung.fas.pir.rest.dto.rule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RRuleDTO {
	@ApiObjectField(name="id", order=0)
	@Getter
	@Setter
	@JsonProperty("id")
	private 	String 			id;

	@ApiObjectField(name="profile_id", order=1)
	@Getter
	@Setter
	@JsonProperty("profile_id")
	private		String			profile;

	@ApiObjectField(name="page_id", order=2)
	@Getter
	@Setter
	@JsonProperty("page_id")
	private		String			page;

	@ApiObjectField(name="create", order=5)
	@Getter
	@Setter
	@JsonProperty("create")
	private		boolean			create;

	@ApiObjectField(name="read", order=6)
	@Getter
	@Setter
	@JsonProperty("read")
	private		boolean			read;

	@ApiObjectField(name="update", order=7)
	@Getter
	@Setter
	@JsonProperty("update")
	private		boolean			update;

	@ApiObjectField(name="delete", order=8)
	@Getter
	@Setter
	@JsonProperty("delete")
	private		boolean			delete;

	private RRuleDTO(Rule entity) {
		setId(IDCoder.encode(entity.getUuid()));
		setProfile(IDCoder.encode(entity.getProfile().getUuid()));
		setPage(IDCoder.encode(entity.getPage().getUuid()));
		setCreate(entity.isCreate());
		setRead(entity.isRead());
		setUpdate(entity.isUpdate());
		setDelete(entity.isDelete());
	}

	public static RRuleDTO toDTO(Rule entity) {
		if(entity != null)
			return new RRuleDTO(entity);
		return null;
	}
}
