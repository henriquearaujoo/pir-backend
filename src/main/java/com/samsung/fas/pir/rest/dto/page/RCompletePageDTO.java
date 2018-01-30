package com.samsung.fas.pir.rest.dto.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Page;
import com.samsung.fas.pir.rest.dto.rule.RRuleDTO;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.List;
import java.util.stream.Collectors;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RCompletePageDTO extends RSimplePageDTO {
	@Getter
	@ApiObjectField(name="rules")
	@JsonProperty("rules")
	private List<RRuleDTO> rules;

	private RCompletePageDTO(Page entity) {
		super(entity);
		rules		= entity.getRules().stream().map(RRuleDTO::toDTO).collect(Collectors.toList());
	}

	public static RCompletePageDTO toDTO(Page entity) {
		if (entity != null) {
			return new RCompletePageDTO(entity);
		}
		return null;
	}
}
