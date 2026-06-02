CREATE TABLE videos
(
    id UUID PRIMARY KEY,

    account_id UUID NOT NULL,

    platform VARCHAR(50) NOT NULL,

    external_video_id VARCHAR(255) NOT NULL,

    url TEXT NOT NULL,

    title TEXT,

    description TEXT,

    published_at TIMESTAMP,

    duration_seconds INTEGER,

    views BIGINT,

    likes BIGINT,

    comments BIGINT,

    shares BIGINT,

    saves BIGINT,

    hashtags TEXT,

    source_keyword VARCHAR(255),

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_videos_account
        FOREIGN KEY (account_id)
        REFERENCES accounts(id)
        ON DELETE CASCADE
);

CREATE UNIQUE INDEX ux_videos_platform_external
ON videos(platform, external_video_id);

CREATE INDEX idx_videos_account_id
ON videos(account_id);

CREATE INDEX idx_videos_source_keyword
ON videos(source_keyword);