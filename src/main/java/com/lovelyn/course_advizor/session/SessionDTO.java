package com.lovelyn.course_advizor.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SessionDTO {

    private Long id;

    @JsonProperty("started_at")
    private Integer startedAt;

    @JsonProperty("ended_at")
    private Integer endedAt;
}
