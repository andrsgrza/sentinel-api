# Sentinel Roadmap

This document tracks the next steps for Sentinel.

## Current Milestone Completed

Sentinel can now:

- Run locally with Spring Boot and PostgreSQL.
- Manage schema with Flyway.
- Store accounts.
- Store videos.
- Store collection jobs.
- Call YouTube Data API.
- Collect real YouTube data.
- Persist collected YouTube accounts and videos.
- Store collection job config and result JSON.

## Phase 1 — Collector Cleanup and Hardening

### 1. Parse YouTube Duration

Current YouTube durations arrive as ISO-8601 duration strings, for example:

```text
PT2H16M32S
```

We need to convert them into seconds and store them in:

```text
videos.duration_seconds
```

### 2. Persist YouTube Tags

YouTube returns tags as an array.

We need to decide whether to:

- Store tags as comma-separated text in the existing field.
- Rename `hashtags` to `tags`.
- Add a new `tags_json` JSONB column.

Recommended direction:

```text
Add tags_json JSONB
```

### 3. Persist Source Keyword

Every collected video should store the keyword/config that discovered it.

Current field:

```text
videos.source_keyword
```

Need to ensure `YoutubeCollector` or persistence layer sets it correctly.

### 4. Improve YouTube Error Handling

Handle cases such as:

- Invalid API key
- Quota exceeded
- Empty results
- YouTube API 4xx/5xx responses
- Missing statistics
- Hidden subscriber count

### 5. Remove or Properly Disable MockYouTubeCollector

The mock collector was useful for validating the collector framework.

Now that the real YouTube collector exists, we should either:

- Remove it.
- Move it to tests.
- Guard it behind a Spring profile.

Recommended direction:

```text
Move mock collector to tests later.
```

## Phase 2 — CollectionConfig Expansion

The collection job request should accept more parameters.

Target request shape:

```json
{
  "platform": "youtube",
  "keyword": "dopamine",
  "maxResults": 10,
  "order": "viewCount",
  "regionCode": "US",
  "language": "en",
  "videoDuration": "short",
  "safeSearch": "none"
}
```

Fields to support:

- `order`
- `regionCode`
- `language`
- `publishedAfter`
- `publishedBefore`
- `videoDuration`
- `safeSearch`
- `deduplicate`

Purpose:

```text
Run different search strategies and compare results.
```

## Phase 3 — Database Improvements

Add fields needed for intelligence and ranking.

### Videos

Potential fields:

```text
thumbnail_url
tags_json
category_id
definition
caption_available
raw_youtube_payload
```

### Accounts

Potential fields:

```text
country
total_views
video_count
thumbnail_url
description
raw_youtube_payload
```

### Collection Jobs

Already added:

```text
config_json
result_json
```

Potential future fields:

```text
execution_time_ms
collector_version
```

## Phase 4 — Ranking and Scoring

Create simple ranking logic.

Example video score:

```text
video_score =
views_score
+ engagement_score
+ recency_score
+ account_opportunity_score
```

Possible signals:

- Views
- Likes
- Comments
- Like rate
- Comment rate
- Account subscriber count
- Views-to-subscriber ratio
- Recency
- Keyword
- Duration

Initial endpoints:

```text
GET /videos/top
GET /accounts/top
GET /collection-jobs/{id}
```

## Phase 5 — Analysis Engine

Once enough videos are collected, add AI analysis.

Target outputs:

```text
hook
hook_type
topic
subtopic
emotional_trigger
content_structure
replicability_score
monetization_score
ai_compatibility_score
```

Potential table:

```text
video_analyses
```

## Phase 6 — Multi-Platform Collectors

Add more collectors using the same abstraction.

Potential collectors:

```text
TikTokCollector
InstagramCollector
RedditCollector
XCollector
PodcastCollector
```

Each collector should return:

```text
CollectionResult
├── accounts
└── videos/content items
```

The rest of the system should not depend on platform-specific DTOs.

## Phase 7 — Dashboard

Build a UI for:

- Running collection jobs
- Viewing top videos
- Viewing top accounts
- Comparing collection configs
- Inspecting result JSON
- Reviewing trends
- Manually selecting promising content ideas

Possible frontend:

```text
React
```

## Phase 8 — Intelligence Loop

Long-term goal:

```text
Collect data
Analyze patterns
Score opportunities
Generate content strategies
Test outputs
Learn from results
```

Sentinel should become the research and intelligence engine for automated content generation.
