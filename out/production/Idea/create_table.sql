CREATE TABLE IF NOT EXISTS connection(
    id uuid DEFAULT gen_random_uuid(),
    firstname varchar(50) NOT NULL,
    connection_datetime timestamp with time zone DEFAULT current_timestamp
);