package com.samsung.fas.pir.models.dto.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.dto.rule.RRuleDTO;
import com.samsung.fas.pir.models.entity.Page;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.util.Base64Utils;

import java.util.List;
import java.util.stream.Collectors;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RCompletePageDTO {
	@Getter
	@ApiObjectField(name="id")
	@JsonProperty("id")
	private		String			id;

	@Getter
	@ApiObjectField(name="title")
	@JsonProperty("title")
	private		String			title;

	@Getter
	@ApiObjectField(name="route")
	@JsonProperty("route")
	private		String			path;

	@Getter
	@ApiObjectField(name="rules")
	@JsonProperty("rules")
	private List<RRuleDTO> rules;

	private RCompletePageDTO(Page entity) {
		id			= Base64Utils.encodeToUrlSafeString(entity.getGuid().toString().getBytes());
		title		= entity.getTitle();
		path		= entity.getUrl();
		rules		= entity.getRules().stream().map(RRuleDTO::toDTO).collect(Collectors.toList());
	}

	public static RCompletePageDTO toDTO(Page entity) {
		if (entity != null) {
			return new RCompletePageDTO(entity);
		}
		return null;
	}
}
