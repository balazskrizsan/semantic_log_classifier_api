CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE vector_models
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE vector_store_384
(
    id              BIGSERIAL,
    vector_model_id BIGINT       NOT NULL REFERENCES vector_models (id) ON DELETE CASCADE,
    context_info    JSONB, -- {type=jira_issue, company=company_id, board=board_id, srpint=sprint_id} {type=question}
    embedding       VECTOR(384) NOT NULL
);

CREATE INDEX idx_vector_store_384_context_info_gin ON vector_store_384 USING GIN (context_info);
