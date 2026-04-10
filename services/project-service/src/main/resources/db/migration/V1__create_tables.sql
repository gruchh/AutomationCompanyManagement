CREATE TABLE locations (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(150) NOT NULL UNIQUE,
                           latitude DOUBLE PRECISION NOT NULL,
                           longitude DOUBLE PRECISION NOT NULL,
                           country VARCHAR(100),
                           city VARCHAR(100)
);

CREATE TABLE projects (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(200) NOT NULL,
                          code VARCHAR(50) UNIQUE,
                          description TEXT,
                          start_date DATE NOT NULL,
                          end_date DATE,
                          status VARCHAR(50) NOT NULL,
                          priority VARCHAR(50),
                          service_type VARCHAR(100) NOT NULL,
                          location_id BIGINT REFERENCES locations(id),
                          project_manager_id BIGINT,
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP NOT NULL,
                          created_by VARCHAR(100),
                          updated_by VARCHAR(100)
);

CREATE TABLE project_employees (
                                   project_id BIGINT NOT NULL REFERENCES projects(id),
                                   employee_id BIGINT
);

CREATE TABLE project_technologies (
                                      project_id BIGINT NOT NULL REFERENCES projects(id),
                                      technology VARCHAR(255)
);