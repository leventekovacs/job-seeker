DROP TABLE IF EXISTS position;

CREATE TABLE position(
    id		    INT 		    AUTO_INCREMENT PRIMARY KEY,
    name	    VARCHAR(50)	    NOT NULL,
    location	VARCHAR(50) 	NOT NULL
);

INSERT INTO position VALUES (1, 'Backend Developer', 'Berlin');
INSERT INTO position VALUES (2, 'DevOps Engineer', 'Szeged');
INSERT INTO position VALUES (3, 'Java Developer', 'Budapest');
INSERT INTO position VALUES (4, 'Full Stack Developer', 'Debrecen');
INSERT INTO position VALUES (5, 'C# Developer', 'Debrecen');
INSERT INTO position VALUES (6, 'DevOps Engineer', 'Budapest');
INSERT INTO position VALUES (7, 'Senior Java Developer', 'Budapest');
INSERT INTO position VALUES (8, 'Senior PHP Developer', 'Budapest');
INSERT INTO position VALUES (9, 'Senior PHP Developer', 'Berlin');

