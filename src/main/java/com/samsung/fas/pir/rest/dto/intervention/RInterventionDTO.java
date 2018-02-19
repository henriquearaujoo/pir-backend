package com.samsung.fas.pir.rest.dto.intervention;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Intervention;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RInterventionDTO {
	@ApiObjectField(name="id", order=0)
	@JsonProperty("id")
	@Getter
	@Setter
	private		String			id;

	@ApiObjectField(name="descritpion", order=1)
	@JsonProperty("description")
	@Getter
	@Setter
	private 	String			description;

	@ApiObjectField(name="activity", order=2)
	@JsonProperty("activity")
	@Getter
	@Setter
	private 	String			activity;

	@ApiObjectField(name="chapter", order=3)
	@JsonProperty("chapter")
	@Getter
	@Setter
	private 	String			chapter;

	private RInterventionDTO(Intervention e) {
		setId(IDCoder.encode(e.getUuid()));
		setDescription(e.getDescription());
		setChapter(IDCoder.encode(e.getChapter().getUuid()));
		setActivity(e.getActivity());
	}

	public static RInterventionDTO toDTO(Intervention e) {
		if (e != null)
			return new RInterventionDTO(e);
		return null;
	}
}