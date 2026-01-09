create schema if not exists usage;

create table if not exists usage.usage_events (
  event_id text primary key,
  request_id text null,
  user_id text not null,
  conversation_id text not null,
  provider_id text not null,
  model_id text not null,
  prompt_tokens integer not null,
  completion_tokens integer not null,
  total_tokens integer not null,
  cost numeric(12,6) not null,
  currency text not null,
  latency_ms integer not null,
  timestamp timestamptz not null
);

create index if not exists usage_events_user_timestamp_idx
  on usage.usage_events (user_id, timestamp desc);

create index if not exists usage_events_provider_timestamp_idx
  on usage.usage_events (provider_id, timestamp desc);
