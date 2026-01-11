package com.prpo.usage.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsageEventSummaryItemDto(
    String eventId,
    String userId,
    String providerId,
    String modelId,
    Integer tokens,
    BigDecimal cost,
    OffsetDateTime timestamp
) {}
