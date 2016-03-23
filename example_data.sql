INSERT INTO users (name, surname, dob, teacher, username, password_hash)
    VALUES ('Peter', 'Smith', '1999-01-08', true, 'psmith', '$2a$12$TsZmzdWW5RW9yMSVwrLUkeQVqen2/1lcR9Ji1tGP0ekI.riW0IUy.');

INSERT INTO users (name, surname, dob, username, password_hash)
    VALUES ('John', 'Doe', '1999-01-08', 'asdsad222', 'aaaaaaa');

INSERT INTO users (name, surname, dob, programme_id, username, password_hash)
    VALUES ('Yousef', 'Alam', '1996-04-01', 1, 'yousef', '$2a$12$Utm/VvGkZYwI6NdCGMjdTeUVyIt7eApSQEXnXkr5PyOQqU.ylAJ62');


INSERT INTO programmes (name, leader_id, active, startDate, duration)
    VALUES ('Computing with Evil', 1, true, '2016-06-08', 13);

INSERT INTO courses (name, leader_id, programme_id, active, startDate, duration, credits)
    VALUES ('Assembly', 1, 1, true, '2016-06-08', 13, 15);

INSERT INTO courses (name, leader_id, programme_id, active, startDate, duration, credits)
    VALUES ('Waves', 1, 1, true, '2016-06-08', 13, 15);

# Search courses by programme name
SELECT courses.name, courses.active, courses.startDate, programmes.name AS programme_name, users.name AS programme_leader
    FROM programmes, courses, users
    WHERE programmes.name = 'Computing with Evil' AND programmes.leader_id = users.id;

# Search courses by leader name
SELECT courses.name, courses.active, courses.startDate, programmes.name AS programme_name, users.name AS programme_leader
    FROM programmes, courses, users
    WHERE programmes.leader_id = users.id AND users.name = 'Peter';

# Search users by course name
SELECT users.name, users.surname, users.permreg
    FROM users, courses, programmes
    WHERE users.programme_id = programmes.id AND courses.name = 'Assembly';
