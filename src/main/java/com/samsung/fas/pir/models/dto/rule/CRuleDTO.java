package com.samsung.fas.pir.models.dto.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.entity.Rule;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.NotNull;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRuleDTO {
	@ApiObjectField(name="profile_id", order=1)
	@Getter
	@Setter
	@JsonProperty("profile_id")
	@NotNull(message="rule.profile.missing")
	private		String			profile;

	@ApiObjectField(name="page_id", order=2)
	@Getter
	@Setter
	@JsonProperty("page_id")
	@NotNull(message="rule.page.missing")
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

	@JsonIgnore
	public Rule getModel() {
		Rule rule = new Rule();
		rule.setCreate(create);
		rule.setDelete(delete);
		rule.setRead(read);
		rule.setUpdate(update);
		return rule;
	}
}
