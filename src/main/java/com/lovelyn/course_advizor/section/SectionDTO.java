package com.lovelyn.course_advizor.section;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SectionDTO {

    private Long id;

    @JsonProperty("started_at")
    private Integer startedAt;

    @JsonProperty("ended_at")
    private Integer endedAt;
}
