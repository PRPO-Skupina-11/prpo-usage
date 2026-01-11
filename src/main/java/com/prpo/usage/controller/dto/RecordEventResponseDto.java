package com.prpo.usage.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RecordEventResponseDto(
    Boolean accepted,
    String eventId,
    Boolean deduplicated
) {}
