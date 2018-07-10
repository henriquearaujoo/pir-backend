package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Pregnancy;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@DTO(Pregnancy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PregnancyDTO extends BaseDTO<Pregnancy> {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long				mobileId;

	@Getter
	@Setter
	@JsonProperty("registered_at")
	private 	Date				registeredAt;

	@Getter
	@Setter
	@JsonProperty("mother")
	private		ResponsibleDTO		pregnantDTO;

	@Getter
	@Setter
	@JsonProperty(value = "agent", access = JsonProperty.Access.READ_ONLY)
	private 	UserDTO				agentDTO;

	@Getter
	@Setter
	@JsonProperty("answers")
	@Valid
	private		List<SAnswerDTO>	answersDTO;

	@Getter
	@Setter
	@JsonProperty("visits")
	@Valid
	private		List<VisitDTO>		visitsDTO;

	public PregnancyDTO() {
		super();
	}

	public PregnancyDTO(Pregnancy entity, Device device, boolean detailed) {
		super(entity);
		setAnswersDTO(entity.getAnswers().stream().map(item -> new SAnswerDTO(item, device, false)).collect(Collectors.toList()));
		setVisitsDTO(entity.getVisits().stream().map(item -> new VisitDTO(item, device, false)).collect(Collectors.toList()));
		setPregnantDTO(detailed? new ResponsibleDTO(entity.getPregnant(), device, false) : null);
//		setAgent(entity.getAgent() != null? new UserDTO(entity.getAgent(), device, false) : null);
	}

	@JsonIgnore
	@Override
	public Pregnancy getModel() {
		Pregnancy model = super.getModel();
		model.setAnswers(getAnswersDTO() != null? getAnswersDTO().stream().map(SAnswerDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setVisits(getVisitsDTO() != null? getVisitsDTO().stream().map(VisitDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}