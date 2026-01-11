package com.prpo.usage.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListEventsResponseDto(
    List<UsageEventSummaryItemDto> items,
    Integer total,
    String nextCursor
) {}
