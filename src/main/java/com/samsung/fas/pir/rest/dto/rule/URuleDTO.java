package com.samsung.fas.pir.rest.dto.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.util.Base64Utils;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class URuleDTO {
	@ApiObjectField(name="id", order=0)
	@Getter
	@Setter
	@JsonProperty("id")
	@NotBlank(message = "rule.id.blank")
	@NotEmpty(message = "rule.id.missing")
	private 	String 			id;

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
		rule.setGuid(UUID.fromString(new String(Base64Utils.decodeFromUrlSafeString(id), StandardCharsets.UTF_8)));
		rule.setCreate(create);
		rule.setDelete(delete);
		rule.setRead(read);
		rule.setUpdate(update);
		return rule;
	}
}
