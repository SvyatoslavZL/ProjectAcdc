DROP TABLE IF EXISTS player;
DROP TABLE role;


CREATE TABLE role
(
    value varchar(50) PRIMARY KEY
);

INSERT INTO role(value)
VALUES ('ADMIN'),
       ('USER'),
       ('GUEST');

CREATE TABLE player
(

    id                BIGSERIAL PRIMARY KEY,
    login             varchar(64) UNIQUE NOT NULL,
    password          varchar(256),
    role              varchar(50) REFERENCES role (value),
    registration_date DATE,
    game_point        int

);

INSERT INTO player(login, password, role, registration_date, game_point)
VALUES ('Ivan', 'qwerty', 'ADMIN', '2024-02-2', 10),
       ('Alex', 'qwerty', 'GUEST', '2023-02-12', 8),
       ('Inna', 'qwerty', 'USER', '2022-02-15', 5),
       ('Oleg', 'qwerty', 'GUEST', '2021-02-12', 3),
       ('Joe', 'qwerty', 'GUEST', '2020-02-12', 3),
       ('Elon', 'qwerty', 'USER', '2024-03-13', 1),
       ('Jack', 'qwerty', 'USER', '2022-02-1', 2),
       ('Vova', 'qwerty', 'USER', '2020-01-03', 4),
       ('Olga', 'qwerty', 'GUEST', '2020-12-12', 9);

--update
UPDATE player
   SET game_point = game_point + 100
 WHERE role = 'ADMIN';

UPDATE player
   SET role='ADMIN'
 WHERE game_point > 8;

SELECT EXTRACT(YEAR FROM registration_date) yyy
  FROM player;

UPDATE player
   SET registration_date=CURRENT_DATE
 WHERE EXTRACT(YEAR FROM registration_date) = EXTRACT(YEAR FROM CURRENT_DATE);

DELETE
  FROM player
 WHERE game_point = 4
   AND LOWER(role) = 'user';

SELECT login l, password p, role r, game_point g
  FROM player
 WHERE game_point IN (5, 2, 3, 8)
 ORDER BY role, player.login DESC;

SELECT role, MAX(game_point)
  FROM player
 GROUP BY role;

SELECT login,
       role,
       game_point,
       CASE
           WHEN game_point >= 5
               THEN 'Winner'
           ELSE 'Loser'
           END AS player_category
  FROM player;
;

SELECT login,
       role,
       game_point,
       (SELECT AVG(player.game_point)
          FROM player) - game_point delta
  FROM player;


SELECT role, count(role) N FROM (SELECT role FROM player) GROUP BY role;

SELECT  p.role, sum(p.game_point) points FROM player p
WHERE game_point>0
GROUP BY role
HAVING sum(p.game_point)>10
ORDER BY points DESC ;

