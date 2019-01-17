INSERT INTO users(username,is_active,password) values('bartek.smuga@o2.pl', 1,'{bcrypt}$2a$10$.P9EX9rm6F9Qdrl3iN9cnOUfJVvzcD0mVb6xaGvOvbc6NPGMSgwVO');
INSERT INTO users(username,is_active,password) values('pawel.slonina1@o2.pl', 1,'{bcrypt}$2a$10$.P9EX9rm6F9Qdrl3iN9cnOUfJVvzcD0mVb6xaGvOvbc6NPGMSgwVO');
INSERT INTO users(username,is_active,password) values('asmolare@o2.pl', 1,'{bcrypt}$2a$10$.P9EX9rm6F9Qdrl3iN9cnOUfJVvzcD0mVb6xaGvOvbc6NPGMSgwVO');
insert into role(authorities, user_id) values('USER', 1);
insert into role(authorities, user_id) values('USER', 2);
insert into role(authorities, user_id) values('PROFESSOR', 2);
insert into role(authorities, user_id) values('USER', 3);
insert into role(authorities, user_id) values('ADMIN', 3);

-- haslo podrecznik