package com.optimagrowth.license.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//Resource Representation Class
//RepresentationModel that lets you add instances of Link and ensures that they are rendered in json
@Getter @Setter @ToString
public class License extends RepresentationModel<License> {

	@JsonProperty("id")
	private int id;
	@JsonProperty("licenseId")
	private String licenseId;
	@JsonProperty("description")
	private String description;
	@JsonProperty("organizationId")
	private String organizationId;
	@JsonProperty("productName")
	private String productName;
	@JsonProperty("licenseType")
	private String licenseType;

	//@JsonCreator: Signals how Jackson can create an instance of this POJO.
	@JsonCreator
	//@JsonProperty: Marks the field into which Jackson should put this constructor argument.
	public License(@JsonProperty("organizationId") String organizationId) {
		this.organizationId = organizationId;
	}

}