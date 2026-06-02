CREATE TABLE accounts
(
    id UUID PRIMARY KEY,

    platform VARCHAR(50) NOT NULL,

    external_account_id VARCHAR(255),

    handle VARCHAR(255) NOT NULL,

    display_name VARCHAR(255),

    profile_url TEXT,

    niche VARCHAR(255),

    subniche VARCHAR(255),

    followers BIGINT,

    avg_views BIGINT,

    language VARCHAR(50),

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX ux_accounts_platform_external
ON accounts(platform, external_account_id);