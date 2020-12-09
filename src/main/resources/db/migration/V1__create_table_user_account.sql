CREATE TABLE USER_ACCOUNT (
ID NUMERIC(10) PRIMARY KEY,
USER_NAME VARCHAR(150) NOT NULL UNIQUE,
PASSWORD VARCHAR(255) NOT NULL,
ENABLED BOOLEAN NOT NULL,
LOCKED BOOLEAN NOT NULL,
LOGIN_ATTEMPTS NUMERIC(4) NOT NULL,
CREATED_AT TIMESTAMP NOT NULL,
UPDATED_AT TIMESTAMP NOT NULL
);