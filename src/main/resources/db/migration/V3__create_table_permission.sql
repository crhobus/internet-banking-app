CREATE TABLE PERMISSION (
ID NUMERIC(10) PRIMARY KEY,
PERMISSION VARCHAR(50) NOT NULL UNIQUE,
CREATED_AT TIMESTAMP NOT NULL,
UPDATED_AT TIMESTAMP NOT NULL
);