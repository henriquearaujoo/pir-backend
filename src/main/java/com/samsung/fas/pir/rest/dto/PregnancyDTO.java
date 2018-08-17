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
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(Pregnancy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PregnancyDTO extends BaseDTO<Pregnancy> {
	@Getter
	@Setter
	@JsonProperty("external_id")
	private		long				externalID;

	@Getter
	@Setter
	@JsonProperty("height")
	private		Short				height;

	@Getter
	@Setter
	@JsonProperty("weight")
	private		Float				weight;

	@Getter
	@Setter
	@JsonProperty("planned")
	private		boolean				planned;

	@Getter
	@Setter
	@JsonProperty("registered_at")
	private 	Date				registeredAt;

	// region Relations
	@Getter
	@Setter
	@JsonProperty("pregnant_id")
	private		UUID				pregnantUUID;

	@Getter
	@Setter
	@JsonProperty("pregnant")
	private 	PregnantDTO			pregnantDTO;

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
	// endregion

	public PregnancyDTO() {
		super();
	}

	public PregnancyDTO(Pregnancy pregnancy, Device device, boolean detailed) {
		super(pregnancy);
		if (!device.isNormal()) {
			setPregnantUUID(pregnancy.getPregnant() != null? pregnancy.getPregnant().getUuid() : null);
			setAnswersDTO(pregnancy.getAnswers().stream().map(item -> new SAnswerDTO(item, device, false)).collect(Collectors.toList()));
			setVisitsDTO(pregnancy.getVisits().stream().map(item -> new VisitDTO(item, device, false)).collect(Collectors.toList()));
		} else {
			setPregnantDTO(detailed && pregnancy.getPregnant() != null? new PregnantDTO(pregnancy.getPregnant(), device, false) : null);
			setAnswersDTO(pregnancy.getAnswers().stream().map(item -> new SAnswerDTO(item, device, false)).collect(Collectors.toList()));
			setVisitsDTO(pregnancy.getVisits().stream().map(item -> new VisitDTO(item, device, false)).collect(Collectors.toList()));
		}
	}

	@JsonIgnore
	@Override
	public Pregnancy getModel() {
		Pregnancy model = new Pregnancy();

		model.setUuid(getUuid());
		model.setExternalID(getExternalID());
		model.setHeight(getHeight());
		model.setWeight(getWeight());
		model.setPlanned(isPlanned());
		model.setRegisteredAt(getRegisteredAt());

		model.setAnswers(getAnswersDTO() != null? getAnswersDTO().stream().map(SAnswerDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		model.setVisits(getVisitsDTO() != null? getVisitsDTO().stream().map(VisitDTO::getModel).collect(Collectors.toList()) : new ArrayList<>());
		return model;
	}
}