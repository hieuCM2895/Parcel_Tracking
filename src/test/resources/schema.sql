CREATE TABLE IF NOT EXISTS guest (
    id VARCHAR(255) PRIMARY KEY,
    full_name VARCHAR(255),
    check_in_time TIMESTAMP,
    check_out_time TIMESTAMP,
    checked_in BOOLEAN
);

CREATE TABLE IF NOT EXISTS parcel (
    id VARCHAR(255) PRIMARY KEY,
    picked_up BOOLEAN NOT NULL,
    picked_up_time TIMESTAMP,
    received_at TIMESTAMP,
    guest_id VARCHAR(255),
    FOREIGN KEY (guest_id) REFERENCES guest(id)
);
