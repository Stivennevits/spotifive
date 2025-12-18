INSERT INTO SONG (title, artist, album, annio, genre) VALUES
('Despacito', 'Luis Fonsi ft. Daddy Yankee', 'Vida', 2017, 'Latin'),
('Shape of You', 'Ed Sheeran', '÷ (Divide)', 2017, 'Pop'),
('God''s Plan', 'Drake', 'Scorpion', 2018, 'Hip-Hop'),
('Bohemian Rhapsody', 'Queen', 'A Night at the Opera', 1975, 'Rock'),
('La Pollera Colorá', 'Los Gaiteros de San Jacinto', 'Cumbia Colombiana', 1960, 'Cumbia'),
('El Cuarto de Tula', 'Grupo Niche', 'Al Pasito', 1981, 'Salsa'),
('Macarena', 'Los Del Río', 'A mí me gusta', 1993, 'Latin'),
('Blinding Lights', 'The Weeknd', 'After Hours', 2019, 'Pop'),
('HUMBLE.', 'Kendrick Lamar', 'DAMN.', 2017, 'Hip-Hop'),
('Imagine', 'John Lennon', 'Imagine', 1971, 'Rock');

INSERT INTO USERS (username, password, email, failed_attempts, status, created_at, updated_at) VALUES
('quipix.admin', '$2a$10$CIttC0fQGaVJRM2yE88hRuH.r7jPFsK5IxWzu0rn7d0h8eNysdTGO', 'spotify@gmail.com', 0, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('user.test', '$2a$10$XWYAWbi3RUjYt/EjRu6H4OifQPEtyG85Der/2nrJvCQmSKikLZ3yS', 'Prueba123@gmail.com', 0, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ;

INSERT INTO MODULES (name, key) VALUES
('Song', 'song'),
('Playlist', 'play_list');

INSERT INTO PRIVILEGES (name, key) VALUES
('Create', 'create'),
('Read', 'read'),
('Update', 'update'),
('Delete', 'delete');

INSERT INTO MODULE_PRIVILEGES (module_id, privilege_id, key) VALUES
(1, 1, 'create_song'),
(1, 2, 'read_song'),
(1, 3, 'update_song'),
(1, 4, 'delete_song'),
(2, 1, 'create_play_list'),
(2, 2, 'read_play_list'),
(2, 3, 'update_play_list'),
(2, 4, 'delete_play_list');

INSERT INTO USER_MODULE_PRIVILEGE (user_id, module_privilege_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(2, 2),
(2, 6);

