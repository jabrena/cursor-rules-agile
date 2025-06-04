-- Flyway Migration V1: Create greek_god table
-- Description: Initial table creation for storing Greek god names
-- Author: Greek Gods API Team
-- Date: 2024

-- Create the greek_god table
CREATE TABLE greek_god (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Add comments for documentation
COMMENT ON TABLE greek_god IS 'Stores information about Greek gods for API data retrieval';
COMMENT ON COLUMN greek_god.id IS 'Unique identifier for each Greek god record';
COMMENT ON COLUMN greek_god.name IS 'Name of the Greek god, must be unique';

-- Insert initial seed data with 20 Greek gods
INSERT INTO greek_god (name) VALUES
    ('Zeus'),
    ('Hera'),
    ('Poseidon'),
    ('Demeter'),
    ('Athena'),
    ('Apollo'),
    ('Artemis'),
    ('Ares'),
    ('Aphrodite'),
    ('Hephaestus'),
    ('Hermes'),
    ('Dionysus'),
    ('Hades'),
    ('Persephone'),
    ('Hestia'),
    ('Hecate'),
    ('Iris'),
    ('Nemesis'),
    ('Tyche'),
    ('Pan'); 