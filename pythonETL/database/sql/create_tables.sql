CREATE TABLE IF NOT exists requests (
    request_id SERIAL PRIMARY KEY,
    request_latitude DECIMAL(10, 6) NOT NULL,
    request_longitude DECIMAL(10, 6) NOT NULL,
    request_city VARCHAR(100) NOT NULL,
    request_city_uf VARCHAR(2) NOT NULL,
    request_temperature_abbrev VARCHAR(10) NOT NULL,
    request_generated TIMESTAMP NOT null
);

CREATE TABLE IF NOT exists hourlies (
    hourly_id SERIAL PRIMARY KEY,
    hourly_fk_request_id INTEGER NOT NULL,
    hourly_time TIMESTAMP NOT NULL,
    hourly_temperature INTEGER NOT NULL,
    CONSTRAINT fk_request_hourly
    FOREIGN KEY (hourly_fk_request_id)
    REFERENCES requests(request_id)
    ON DELETE CASCADE
);


CREATE TABLE IF NOT exists dailies (
    daily_id SERIAL PRIMARY KEY,
    daily_fk_request_id INTEGER NOT NULL,
    daily_sunrise TIMESTAMP NOT NULL,
    daily_sunset TIMESTAMP NOT NULL,
    CONSTRAINT fk_request_daily
    FOREIGN KEY (daily_fk_request_id)
    REFERENCES requests(request_id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT exists currents (
    current_id SERIAL PRIMARY KEY,
    current_fk_request_id INTEGER NOT NULL,
    current_day TIMESTAMP NOT NULL,
    current_temperature INTEGER NOT NULL,
    CONSTRAINT fk_request_current
    FOREIGN KEY (current_fk_request_id)
    REFERENCES requests(request_id)
    ON DELETE CASCADE
);