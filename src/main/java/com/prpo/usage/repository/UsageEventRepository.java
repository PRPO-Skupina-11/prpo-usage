package com.prpo.usage.repository;

import com.prpo.usage.domain.UsageEvent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsageEventRepository extends JpaRepository<UsageEvent, String> {

  @Modifying
  @Query(
      value = """
          INSERT INTO usage.usage_events (
            event_id,
            request_id,
            user_id,
            conversation_id,
            provider_id,
            model_id,
            prompt_tokens,
            completion_tokens,
            total_tokens,
            cost,
            currency,
            latency_ms,
            timestamp
          ) VALUES (
            :eventId,
            :requestId,
            :userId,
            :conversationId,
            :providerId,
            :modelId,
            :promptTokens,
            :completionTokens,
            :totalTokens,
            :cost,
            :currency,
            :latencyMs,
            :timestamp
          )
          ON CONFLICT (event_id) DO NOTHING
          """,
      nativeQuery = true
  )
  int insertIgnore(
      @Param("eventId") String eventId,
      @Param("requestId") String requestId,
      @Param("userId") String userId,
      @Param("conversationId") String conversationId,
      @Param("providerId") String providerId,
      @Param("modelId") String modelId,
      @Param("promptTokens") Integer promptTokens,
      @Param("completionTokens") Integer completionTokens,
      @Param("totalTokens") Integer totalTokens,
      @Param("cost") BigDecimal cost,
      @Param("currency") String currency,
      @Param("latencyMs") Integer latencyMs,
      @Param("timestamp") OffsetDateTime timestamp
  );

  @Query(
        value = """
            SELECT *
            FROM usage.usage_events
            WHERE timestamp >= COALESCE(:from, '-infinity'::timestamptz)
            AND timestamp <= COALESCE(:to,   'infinity'::timestamptz)
            ORDER BY timestamp DESC, event_id DESC
            LIMIT :limit
            """,
        nativeQuery = true
    )
    List<UsageEvent> list(
        @Param("from") OffsetDateTime from,
        @Param("to") OffsetDateTime to,
        @Param("limit") int limit
    );
}
