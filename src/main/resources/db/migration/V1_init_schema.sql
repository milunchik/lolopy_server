CREATE SEQUENCE IF NOT EXISTS users_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS profiles_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS trips_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS public.users (
    id BIGINT PRIMARY KEY DEFAULT nextval('users_sequence'),
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.profiles (
    id BIGINT PRIMARY KEY DEFAULT nextval('profiles_sequence'),
    name VARCHAR(255),
    phone VARCHAR(20),
    passport VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS public.trips (
    id BIGINT PRIMARY KEY DEFAULT nextval('trips_sequence'),
    country VARCHAR(100),
    price VARCHAR(50),
    category VARCHAR(50),
    short_description TEXT,
    long_description TEXT,
    capacity VARCHAR(10),
    food_place VARCHAR(100),
    transport VARCHAR(100),
    accomodation VARCHAR(100),
    date VARCHAR(50)
);
