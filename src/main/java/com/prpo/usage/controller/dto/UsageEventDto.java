package com.prpo.usage.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsageEventDto(
    String eventId,
    String requestId,
    String userId,
    String conversationId,
    String providerId,
    String modelId,
    Integer promptTokens,
    Integer completionTokens,
    Integer totalTokens,
    BigDecimal cost,
    String currency,
    Integer latencyMs,
    OffsetDateTime timestamp
) {}
