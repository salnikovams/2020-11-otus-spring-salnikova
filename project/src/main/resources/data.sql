insert into LIM_CURRENCY (name, iso_code, alpha_code) values ('Российский рубль', '643', 'RUB'), ('Евро','978','EUR'), ('Доллар США', '840', '978');

--insert into USERS (ID, USERNAME, PASSWORD, ROLE) VALUES (1, 'Operator', '$2y$12$0./a1TvfPwxj.OTlQLF1HONCpHXHtrcrn7CR5sPE7UHSWLbkPjxZG', 'ROLE_OPERATOR');
--insert into USERS (ID, USERNAME, PASSWORD, ROLE) VALUES (2, 'Controller', '$2y$12$0./a1TvfPwxj.OTlQLF1HONCpHXHtrcrn7CR5sPE7UHSWLbkPjxZG', 'ROLE_CONTROLLER');

insert into USERS (ID, USERNAME, PASSWORD, ROLE) VALUES (1, 'Operator', '100', 'ROLE_OPERATOR');
insert into USERS (ID, USERNAME, PASSWORD, ROLE) VALUES (2, 'Controller', '100', 'ROLE_CONTROLLER');
