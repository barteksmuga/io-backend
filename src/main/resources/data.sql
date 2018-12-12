INSERT INTO user(email,is_active,password) values('bartek.smuga@o2.pl', 1,'{bcrypt}$2a$04$WyX30HkyWh6uvvmJffvNYevwmSYmY5q9P5RxjB19Lin7nLYngk3p6');
INSERT INTO user(email,is_active,password) values('pawel.slonina1@o2.pl', 1,'{bcrypt}$2a$04$WyX30HkyWh6uvvmJffvNYevwmSYmY5q9P5RxjB19Lin7nLYngk3p6');
INSERT INTO user(email,is_active,password) values('asmolare@o2.pl', 1,'{bcrypt}$2a$04$WyX30HkyWh6uvvmJffvNYevwmSYmY5q9P5RxjB19Lin7nLYngk3p6');
insert into role(authorities, user_id) values('USER', 1);
insert into role(authorities, user_id) values('USER', 2);
insert into role(authorities, user_id) values('PROFESSOR', 2);
insert into role(authorities, user_id) values('USER', 3);
insert into role(authorities, user_id) values('ADMIN', 3);