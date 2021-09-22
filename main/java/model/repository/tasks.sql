--use tasks;
--show tables;
--SELECT * FROM todos;
--SELECT * FROM users WHERE user_name = 'bana';
--UPDATE todos SET userID = 7 WHERE name LIKE '%scris%';
SELECT * FROM (SELECT * FROM todos, users WHERE userID = id) tasks  WHERE user_name='bana';
--INSERT INTO todos (name, checked,userID) VALUES('De scris o formulă',false, (SELECT id FROM users WHERE user_name ='bana'));