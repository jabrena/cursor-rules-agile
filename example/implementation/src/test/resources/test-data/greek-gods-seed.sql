-- Test data seed file for Greek Gods
-- Contains 20 Greek god names for testing the API

DELETE FROM greek_god;

INSERT INTO greek_god (id, name) VALUES 
(1, 'Zeus'),
(2, 'Hera'), 
(3, 'Poseidon'),
(4, 'Demeter'),
(5, 'Athena'),
(6, 'Apollo'),
(7, 'Artemis'),
(8, 'Ares'),
(9, 'Aphrodite'),
(10, 'Hephaestus'),
(11, 'Hermes'),
(12, 'Dionysus'),
(13, 'Hades'),
(14, 'Persephone'),
(15, 'Hestia'),
(16, 'Hebe'),
(17, 'Hecate'),
(18, 'Iris'),
(19, 'Nike'),
(20, 'Tyche'); 