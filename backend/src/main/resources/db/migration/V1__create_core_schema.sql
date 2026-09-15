CREATE TABLE projects (
    id UUID PRIMARY KEY,
    name VARCHAR(160) NOT NULL,
    description TEXT,
    type VARCHAR(30) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    initial_budget NUMERIC(14, 2) NOT NULL CHECK (initial_budget >= 0),
    start_date DATE,
    estimated_end_date DATE,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE stages (
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    title VARCHAR(160) NOT NULL,
    description TEXT,
    display_order INTEGER NOT NULL CHECK (display_order >= 0),
    planned_start_date DATE,
    planned_end_date DATE,
    planned_budget NUMERIC(14, 2) NOT NULL CHECK (planned_budget >= 0),
    progress INTEGER NOT NULL CHECK (progress BETWEEN 0 AND 100),
    status VARCHAR(30) NOT NULL,
    rejection_comment TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uq_stages_project_order UNIQUE (project_id, display_order)
);

CREATE TABLE expenses (
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    stage_id UUID REFERENCES stages(id) ON DELETE SET NULL,
    label VARCHAR(160) NOT NULL,
    amount NUMERIC(14, 2) NOT NULL CHECK (amount > 0),
    expense_date DATE NOT NULL,
    category VARCHAR(30) NOT NULL,
    provider VARCHAR(160),
    reference VARCHAR(100),
    payment_status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE documents (
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    stage_id UUID REFERENCES stages(id) ON DELETE SET NULL,
    original_name VARCHAR(255) NOT NULL,
    storage_name VARCHAR(255) NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    size BIGINT NOT NULL CHECK (size > 0),
    local_path VARCHAR(500) NOT NULL,
    type VARCHAR(30) NOT NULL,
    added_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE activities (
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    stage_id UUID REFERENCES stages(id) ON DELETE SET NULL,
    type VARCHAR(40) NOT NULL,
    description TEXT NOT NULL,
    occurred_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_stages_project_id ON stages(project_id);
CREATE INDEX idx_expenses_project_id ON expenses(project_id);
CREATE INDEX idx_documents_project_id ON documents(project_id);
CREATE INDEX idx_activities_project_occurred_at ON activities(project_id, occurred_at DESC);