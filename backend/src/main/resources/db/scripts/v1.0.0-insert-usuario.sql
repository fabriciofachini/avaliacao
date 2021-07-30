--liquibase formatted sql

--changeset fabriciofachini:1 endDelimiter:/
insert into public.TB_USUARIO (ID_USUARIO, DS_LOGIN, DS_SENHA, NO_USUARIO, ID_PERFIL) values (1, 'admin', '$2a$10$c1DZiE02f3C8M7TFYV25wu2dEA8UCelPejKzGzMp6WiQ6zt2PS5Iu', 'José da Silva', 'AD');
insert into public.TB_USUARIO (ID_USUARIO, DS_LOGIN, DS_SENHA, NO_USUARIO, ID_PERFIL) values (2, 'comum', '$2a$10$c1DZiE02f3C8M7TFYV25wu2dEA8UCelPejKzGzMp6WiQ6zt2PS5Iu', 'José da Silva', 'CO');
COMMIT;
/
