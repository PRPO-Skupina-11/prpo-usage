package com.prpo.usage.logic;

import com.prpo.usage.domain.UsageEvent;
import com.prpo.usage.model.ListEventsResponse;
import com.prpo.usage.model.RecordEventResponse;
import com.prpo.usage.model.UsageEventSummaryItem;
import com.prpo.usage.repository.UsageEventRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsageEventsService {

  private static final int DEFAULT_LIMIT = 100;

  private final UsageEventRepository usageEventRepository;

  public UsageEventsService(UsageEventRepository usageEventRepository) {
    this.usageEventRepository = usageEventRepository;
  }

  @Transactional
  public RecordEventResponse recordEvent(com.prpo.usage.model.UsageEvent dto) {
    int inserted = usageEventRepository.insertIgnore(
        dto.getEventId(),
        dto.getRequestId(),
        dto.getUserId(),
        dto.getConversationId(),
        dto.getProviderId(),
        dto.getModelId(),
        dto.getPromptTokens(),
        dto.getCompletionTokens(),
        dto.getTotalTokens(),
        dto.getCost(),
        dto.getCurrency(),
        dto.getLatencyMs(),
        dto.getTimestamp()
    );

    boolean deduplicated = inserted == 0;

    RecordEventResponse response = new RecordEventResponse();
    response.setAccepted(true);
    response.setEventId(dto.getEventId());
    if (deduplicated) {
      response.setDeduplicated(true);
    }
    return response;
  }

  @Transactional(readOnly = true)
  public ListEventsResponse listEvents(
      OffsetDateTime from,
      OffsetDateTime to,
      Integer limit,
      String cursor
  ) {
    int effectiveLimit = limit != null ? limit : DEFAULT_LIMIT;

    List<UsageEvent> events =
        usageEventRepository.list(from, to, effectiveLimit);

    List<UsageEventSummaryItem> items =
        events.stream()
            .map(this::toSummaryModel)
            .collect(Collectors.toList());

    ListEventsResponse response = new ListEventsResponse();
    response.setItems(items);
    response.setTotal(null);
    response.setNextCursor(null);
    return response;
  }

  @Transactional(readOnly = true)
  public com.prpo.usage.model.UsageEvent getEvent(String eventId) {
    Optional<UsageEvent> event =
        usageEventRepository.findById(eventId);

    return event.map(this::toModel).orElse(null);
  }

  private com.prpo.usage.model.UsageEvent toModel(UsageEvent event) {
    com.prpo.usage.model.UsageEvent dto = new com.prpo.usage.model.UsageEvent();
    dto.setEventId(event.getEventId());
    dto.setRequestId(event.getRequestId());
    dto.setUserId(event.getUserId());
    dto.setConversationId(event.getConversationId());
    dto.setProviderId(event.getProviderId());
    dto.setModelId(event.getModelId());
    dto.setPromptTokens(event.getPromptTokens());
    dto.setCompletionTokens(event.getCompletionTokens());
    dto.setTotalTokens(event.getTotalTokens());
    dto.setCost(event.getCost());
    dto.setCurrency(event.getCurrency());
    dto.setLatencyMs(event.getLatencyMs());
    dto.setTimestamp(event.getTimestamp());
    return dto;
  }

  private UsageEventSummaryItem toSummaryModel(UsageEvent event) {
    UsageEventSummaryItem item = new UsageEventSummaryItem();
    item.setEventId(event.getEventId());
    item.setUserId(event.getUserId());
    item.setProviderId(event.getProviderId());
    item.setModelId(event.getModelId());
    item.setTokens(event.getTotalTokens());
    item.setCost(event.getCost());
    item.setTimestamp(event.getTimestamp());
    return item;
  }
}
