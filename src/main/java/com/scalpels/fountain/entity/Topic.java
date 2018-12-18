package com.scalpels.fountain.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    private Long id;

    private String title;

    private String description;

    private String createdBy;

    private Date createdOn;

    private String lastModifiedBy;

    private Date lastModifiedOn;
}