# Sentinel API

Sentinel is a market intelligence and content discovery engine.

Its first goal is to automatically collect public content data from platforms such as YouTube, persist discovered accounts and videos, and prepare that data for future analysis, scoring, and content strategy decisions.

The current version focuses on YouTube discovery.

## Current Capabilities

Sentinel can currently:

- Run locally with Spring Boot and PostgreSQL.
- Manage database schema changes with Flyway.
- Store platform accounts.
- Store platform videos.
- Run collection jobs.
- Persist collection job configuration as JSON.
- Persist collection job results as JSON.
- Query the YouTube Data API.
- Collect YouTube videos and channels from a keyword search.
- Persist collected YouTube accounts and videos into PostgreSQL.

## Tech Stack

- Java 21
- Spring Boot
- Maven
- PostgreSQL
- Flyway
- Docker Compose
- YouTube Data API v3
- SDKMAN for Java version management

## Local Requirements

Install:

- Docker
- Java 21
- SDKMAN
- Maven wrapper is included in the project
- A YouTube Data API key

## Java Version

This project uses SDKMAN.

From the project root:

```bash
sdk env
```

Verify:

```bash
java -version
./mvnw -version
```

Both should use Java 21.

## Environment Variables

Create/export the following variable locally:

```bash
export YOUTUBE_API_KEY="your_api_key_here"
```

The local Spring profile reads it from:

```yaml
youtube:
  api-key: ${YOUTUBE_API_KEY}
```

Do not commit API keys.

## Running PostgreSQL Locally

Start PostgreSQL with Docker Compose:

```bash
docker compose up -d
```

Verify the container is running:

```bash
docker ps
```

The database container should be named:

```text
sentinel-postgres
```

## Running the Application

From the project root:

```bash
./mvnw spring-boot:run
```

The API runs on:

```text
http://localhost:8080
```

## Testing the YouTube Debug Endpoints

Search YouTube:

```bash
curl "http://localhost:8080/debug/youtube/search?keyword=dopamine&maxResults=1" | jq .
```

Fetch video details:

```bash
curl "http://localhost:8080/debug/youtube/videos?videoIds=QmOF0crdyRU" | jq .
```

Fetch channel details:

```bash
curl "http://localhost:8080/debug/youtube/channels?channelIds=UC2D2CMWXMOVWx7giW1n3LIg" | jq .
```

These endpoints are temporary debugging endpoints.

## Running a Collection Job

Run a YouTube keyword collection job:

```bash
curl -X POST http://localhost:8080/collection-jobs \
  -H "Content-Type: application/json" \
  -d '{
    "platform": "youtube",
    "keyword": "dopamine",
    "maxResults": 5
  }'
```

Expected result:

```json
{
  "platform": "youtube",
  "keyword": "dopamine",
  "status": "COMPLETED",
  "itemsFound": 5
}
```

The job should:

1. Create a collection job.
2. Build a collection config.
3. Select the YouTube collector.
4. Call YouTube search.
5. Fetch video details.
6. Fetch channel details.
7. Map YouTube data into internal collector models.
8. Persist accounts.
9. Persist videos.
10. Store the collection result in `collection_jobs.result_json`.

## Checking the Database

Enter PostgreSQL:

```bash
docker exec -it sentinel-postgres psql -U sentinel_user -d sentinel
```

List tables:

```sql
\dt
```

Check collected accounts:

```sql
SELECT platform, handle, display_name, followers
FROM accounts
ORDER BY created_at DESC;
```

Check collected videos:

```sql
SELECT platform, external_video_id, title, views, url
FROM videos
ORDER BY created_at DESC;
```

Check latest collection job:

```sql
SELECT
    platform,
    keyword,
    status,
    items_found,
    config_json,
    result_json
FROM collection_jobs
ORDER BY created_at DESC
LIMIT 1;
```

Exit PostgreSQL:

```sql
\q
```

## Current Main Endpoints

Accounts:

```text
GET  /accounts
POST /accounts
```

Videos:

```text
GET  /videos
POST /videos
```

Collection Jobs:

```text
GET  /collection-jobs
POST /collection-jobs
```

YouTube Debug:

```text
GET /debug/youtube/search
GET /debug/youtube/videos
GET /debug/youtube/channels
```

## Current Domain Concepts

### Account

Represents a platform-specific creator/channel/profile.

Rule:

```text
1 Account = 1 platform account
```

For example:

```text
YouTube @hubermanlab     = one account
TikTok @hubermanlab      = another account
Instagram @hubermanlab   = another account
```

### Video

Represents a platform-specific video.

A video belongs to one account.

```text
Account 1 -> N Videos
```

### Collection Job

Represents a single collection execution.

Example:

```text
platform = youtube
keyword = dopamine
maxResults = 5
```

A collection job stores:

- Status
- Config JSON
- Result JSON
- Items found
- Error message, if failed

### Collector

A collector knows how to collect data from a platform.

Current collector:

```text
YoutubeCollector
```

Future collectors:

```text
TikTokCollector
InstagramCollector
RedditCollector
XCollector
```

## Important Notes

- Flyway is the source of truth for database schema changes.
- Hibernate should not create or update tables automatically.
- YouTube API keys must not be committed.
- The current YouTube debug endpoints are temporary.
- The collector layer should remain platform-agnostic at the system level.
- YouTube-specific DTOs should stay isolated under the YouTube collector package.
