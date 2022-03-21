package com.lovelyn.course_advizor.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentDTO {

    private Long id;

    private String name;

    private String abbreviation;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}
