package com.prpo.usage.controller;

import com.prpo.usage.api.EventsApi;
import com.prpo.usage.logic.UsageEventsService;
import com.prpo.usage.model.ListEventsResponse;
import com.prpo.usage.model.RecordEventResponse;
import com.prpo.usage.model.UsageEvent;
import java.time.OffsetDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsageEventsController implements EventsApi {

  private final UsageEventsService usageEventsService;

  public UsageEventsController(UsageEventsService usageEventsService) {
    this.usageEventsService = usageEventsService;
  }

  @Override
  public ResponseEntity<RecordEventResponse> recordUsageEvent(UsageEvent usageEvent) {
    RecordEventResponse response = usageEventsService.recordEvent(usageEvent);
    return ResponseEntity.accepted().body(response);
  }

  @Override
  public ResponseEntity<ListEventsResponse> listUsageEvents(
      OffsetDateTime from,
      OffsetDateTime to,
      Integer limit,
      String cursor
  ) {
    ListEventsResponse response =
        usageEventsService.listEvents(from, to, limit, cursor);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<UsageEvent> getUsageEvent(String id) {
    UsageEvent event = usageEventsService.getEvent(id);
    return ResponseEntity.ok(event);
  }
}
