# Sentinel Architecture

Sentinel is designed as a platform-agnostic market intelligence engine.

The current implementation focuses on YouTube, but the architecture is meant to support many future collectors.

## High-Level Flow

```text
HTTP Request
    ↓
CollectionJobController
    ↓
CollectionJobService
    ↓
CollectorRegistry
    ↓
ContentCollector
    ↓
YoutubeCollector
    ↓
YoutubeClient
    ↓
YouTube Data API
    ↓
Youtube DTOs
    ↓
YoutubeMapper
    ↓
CollectedAccount / CollectedVideo
    ↓
CollectionResult
    ↓
CollectionPersistenceService
    ↓
AccountService / VideoService
    ↓
PostgreSQL
```

## Main Layers

## 1. API Layer

The API layer receives HTTP requests.

Current controllers:

```text
AccountController
VideoController
CollectionJobController
YoutubeDebugController
```

`YoutubeDebugController` is temporary and exists only to test YouTube API calls directly.

## 2. Collection Job Layer

Package:

```text
com.sentinel.collection_job
```

Main classes:

```text
CollectionJobEntity
CollectionJobRepository
CollectionJobService
CollectionJobController
CollectionJobStatus
```

Responsibilities:

- Create collection jobs.
- Store job status.
- Store config JSON.
- Store result JSON.
- Mark jobs as PENDING, RUNNING, COMPLETED, or FAILED.
- Orchestrate collector execution.

A collection job is an execution record.

Example:

```json
{
  "platform": "youtube",
  "keyword": "dopamine",
  "maxResults": 5
}
```

## 3. Collector Abstraction Layer

Package:

```text
com.sentinel.collector
```

Main classes:

```text
ContentCollector
CollectorRegistry
CollectionConfig
CollectionResult
CollectionPersistenceService
```

### ContentCollector

Common interface for all collectors.

```java
public interface ContentCollector {
    String getPlatform();
    CollectionResult collect(CollectionConfig config);
}
```

Every platform collector should implement this interface.

### CollectorRegistry

Finds the correct collector based on platform.

Example:

```text
platform = youtube
↓
YoutubeCollector
```

This allows adding future collectors without changing the job orchestration logic.

### CollectionConfig

Represents the strategy/configuration used to collect data.

Examples:

```text
keyword
maxResults
order
regionCode
language
videoDuration
safeSearch
publishedAfter
publishedBefore
deduplicate
```

This is stored in:

```text
collection_jobs.config_json
```

### CollectionResult

Represents what a collector found.

```text
CollectionResult
├── itemsFound
├── accounts
└── videos
```

This is stored in:

```text
collection_jobs.result_json
```

### CollectionPersistenceService

Persists collected results into real domain tables.

It takes:

```text
CollectionResult
```

and saves:

```text
CollectedAccount -> accounts
CollectedVideo   -> videos
```

## 4. Collector Models

Package:

```text
com.sentinel.collector.model
```

Main classes:

```text
CollectedAccount
CollectedVideo
```

These are internal platform-agnostic models.

They are not database entities.

They are not HTTP DTOs.

They represent normalized content discovered by collectors.

## 5. YouTube Collector Layer

Package:

```text
com.sentinel.collector.youtube
```

Main classes:

```text
YoutubeCollector
YoutubeClient
YoutubeDebugController
```

Subpackages:

```text
dto
mapper
```

### YoutubeClient

Responsible only for calling the YouTube Data API.

Current methods:

```java
searchVideos(keyword, maxResults)
getVideos(videoIds)
getChannels(channelIds)
```

### Youtube DTOs

Package:

```text
com.sentinel.collector.youtube.dto
```

Examples:

```text
YoutubeSearchResponse
YoutubeSearchItem
YoutubeVideoResponse
YoutubeVideoItem
YoutubeChannelResponse
YoutubeChannelItem
```

These mirror YouTube API responses.

They should not leak into the rest of the application.

### YoutubeMapper

Package:

```text
com.sentinel.collector.youtube.mapper
```

Converts YouTube-specific DTOs into platform-agnostic collector models:

```text
YoutubeChannelItem -> CollectedAccount
YoutubeVideoItem   -> CollectedVideo
```

### YoutubeCollector

Coordinates the YouTube collection flow:

```text
1. search.list
2. Extract video IDs and channel IDs
3. videos.list
4. channels.list
5. Map to CollectedAccount and CollectedVideo
6. Return CollectionResult
```

## 6. Account Domain

Package:

```text
com.sentinel.account
```

Main classes:

```text
AccountEntity
AccountRepository
AccountService
AccountController
AccountMapper
```

Rule:

```text
1 Account = 1 platform account
```

For example:

```text
YouTube @hubermanlab    = Account A
TikTok @hubermanlab     = Account B
Instagram @hubermanlab  = Account C
```

Deduplication is based on:

```text
platform + external_account_id
```

Fallback can use:

```text
platform + handle
```

## 7. Video Domain

Package:

```text
com.sentinel.video
```

Main classes:

```text
VideoEntity
VideoRepository
VideoService
VideoController
VideoMapper
```

Relationship:

```text
Account 1 -> N Videos
```

Deduplication is based on:

```text
platform + external_video_id
```

## 8. Database

Database:

```text
PostgreSQL
```

Schema migrations:

```text
Flyway
```

Current important tables:

```text
accounts
videos
collection_jobs
flyway_schema_history
```

Important collection job JSON fields:

```text
config_json
result_json
```

## 9. Local Development Architecture

```text
Mac
├── Spring Boot app
└── Docker Compose
    └── PostgreSQL
```

Spring Boot connects to:

```text
localhost:5432
```

## 10. Production Direction

Recommended future deployment:

```text
AWS ECS / App Runner / EC2
+
AWS RDS PostgreSQL
+
AWS Secrets Manager
+
CloudWatch Logs
```

Local Docker Compose is only for development.

Production should use managed PostgreSQL.

## 11. Design Principles

### Keep platform-specific code isolated

YouTube-specific DTOs and logic should stay under:

```text
collector/youtube
```

### Keep collectors platform-normalized

Collectors should return:

```text
CollectionResult
```

not platform-specific response objects.

### Keep persistence separate from collection

Collectors collect data.

Persistence services save data.

### Keep Flyway as source of truth

Hibernate should not create or update schema automatically.

### Keep configuration auditable

Every collection job should store:

```text
config_json
```

so future analysis can compare search strategies.
