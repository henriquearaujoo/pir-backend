package com.samsung.fas.pir.rest.dto.intervention;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.Intervention;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CRUInterventionDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		String			id;

	@Getter
	@Setter
	@JsonProperty("chapter_id")
	private		String			chapterdID;

	@Getter
	@Setter
	@JsonProperty("description")
	@NotBlank(message = "description.missing")
	private 	String			description;

	@Getter
	@Setter
	@JsonProperty("activity")
	@NotBlank(message = "activity.missing")
	private 	String			activity;

	public CRUInterventionDTO() {
		super();
	}

	public CRUInterventionDTO(Intervention intervention, boolean detailed) {
		setId(IDCoder.encode(intervention.getUuid()));
		setDescription(intervention.getDescription());
		setChapterdID(IDCoder.encode(intervention.getChapter().getUuid()));
		setActivity(intervention.getActivity());
	}

	@JsonIgnore
	public Intervention getModel() {
		Intervention model = new Intervention();
		model.setDescription(getDescription());
		model.setActivity(getActivity());
		model.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		return model;
	}
}
