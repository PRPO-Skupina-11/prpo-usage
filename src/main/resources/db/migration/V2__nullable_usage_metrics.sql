alter table usage.usage_events
  alter column prompt_tokens drop not null,
  alter column completion_tokens drop not null,
  alter column total_tokens drop not null,
  alter column cost drop not null,
  alter column currency drop not null,
  alter column latency_ms drop not null;