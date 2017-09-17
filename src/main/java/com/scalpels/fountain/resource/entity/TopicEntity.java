package com.scalpels.fountain.resource.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class TopicEntity {
	private Long id;

	@NotBlank
	private String title;

	@Length(max = 10, min = 5)
	private String description;

	private Date createdOn;

	private String createdBy;

	private Date lastModifiedOn;

	private String lastModifiedBy;

}