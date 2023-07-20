CREATE TABLE IF NOT EXISTS connection(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    firstname varchar(50) NOT NULL UNIQUE,
    connection_datetime timestamp with time zone DEFAULT current_timestamp
);