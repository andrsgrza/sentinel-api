CREATE TABLE collection_jobs
(
    id UUID PRIMARY KEY,

    platform VARCHAR(50) NOT NULL,

    keyword VARCHAR(255) NOT NULL,

    status VARCHAR(50) NOT NULL,

    max_results INTEGER,

    items_found INTEGER,

    error_message TEXT,

    started_at TIMESTAMP,

    finished_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_collection_jobs_platform
ON collection_jobs(platform);

CREATE INDEX idx_collection_jobs_keyword
ON collection_jobs(keyword);

CREATE INDEX idx_collection_jobs_status
ON collection_jobs(status);