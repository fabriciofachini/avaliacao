--liquibase formatted sql

--changeset fabriciofachini:1
CREATE TABLE public.TB_USUARIO
(
    ID_USUARIO INTEGER NOT NULL CONSTRAINT PK_USUARIO PRIMARY KEY,
    DS_LOGIN VARCHAR(50) NOT NULL UNIQUE,
    DS_SENHA VARCHAR(255),
    NO_USUARIO VARCHAR(255) NOT NULL,
    ID_PERFIL VARCHAR(2) NOT NULL
);
--rollback DROP TABLE public.TB_USUARIO;

--changeset fabriciofachini:2
CREATE SEQUENCE IF NOT EXISTS public.SEQ_USUARIO
    MINVALUE 1
    NO MAXVALUE
    INCREMENT BY 1
    START WITH 1
    NO CYCLE;
--rollback DROP SEQUENCE public.SEQ_USUARIO;

--changeset fabriciofachini:3
CREATE TABLE public.TB_CLIENTE
(
    ID_CLIENTE INTEGER NOT NULL CONSTRAINT PK_CLIENTE PRIMARY KEY,
    NO_CLIENTE VARCHAR(100) NOT NULL,
    NU_CPF VARCHAR(11) NOT NULL,
    NU_CEP VARCHAR(8) NOT NULL,
    DS_LOGRADOURO VARCHAR(255) NOT NULL,
    DS_COMPLEMENTO VARCHAR(255),
    DS_BAIRRO VARCHAR(255) NOT NULL,
    NO_CIDADE VARCHAR(255) NOT NULL,
    SG_UF VARCHAR(2) NOT NULL,
    ID_USUARIO INTEGER NOT NULL CONSTRAINT FK_USUARIO_CLIENTE REFERENCES public.TB_USUARIO(ID_USUARIO),
    DH_ALTERACAO TIMESTAMP NOT NULL
);
--rollback DROP TABLE public.TB_CLIENTE;

--changeset fabriciofachini:4
CREATE SEQUENCE IF NOT EXISTS public.SEQ_CLIENTE
    MINVALUE 1
    NO MAXVALUE
    INCREMENT BY 1
    START WITH 1
    NO CYCLE;
--rollback DROP SEQUENCE public.SEQ_CLIENTE;

--changeset fabriciofachini:5
CREATE TABLE public.TB_TELEFONE
(
    ID_TELEFONE INTEGER NOT NULL CONSTRAINT PK_TELEFONE PRIMARY KEY,
    ID_CLIENTE INTEGER NOT NULL CONSTRAINT FK_CLIENTE_TELEFONE REFERENCES public.TB_CLIENTE(ID_CLIENTE),
    NU_TELEFONE VARCHAR(11) NOT NULL,
    ID_TIPO_TELEFONE VARCHAR(2) NOT NULL,
    ID_USUARIO INTEGER NOT NULL CONSTRAINT FK_USUARIO_TELEFONE REFERENCES public.TB_USUARIO(ID_USUARIO),
    DH_ALTERACAO TIMESTAMP NOT NULL
);
--rollback DROP TABLE public.TB_TELEFONE;

--changeset fabriciofachini:6
CREATE SEQUENCE IF NOT EXISTS public.SEQ_TELEFONE
    MINVALUE 1
    NO MAXVALUE
    INCREMENT BY 1
    START WITH 1
    NO CYCLE;
--rollback DROP SEQUENCE public.SEQ_TELEFONE;

--changeset fabriciofachini:7
CREATE TABLE public.TB_EMAIL
(
    ID_EMAIL INTEGER NOT NULL CONSTRAINT PK_EMAIL PRIMARY KEY,
    ID_CLIENTE INTEGER NOT NULL CONSTRAINT FK_CLIENTE_EMAIL REFERENCES public.TB_CLIENTE(ID_CLIENTE),
    DS_EMAIL VARCHAR(255) NOT NULL,
    ID_USUARIO INTEGER NOT NULL CONSTRAINT FK_USUARIO_EMAIL REFERENCES public.TB_USUARIO(ID_USUARIO),
    DH_ALTERACAO TIMESTAMP NOT NULL
);
--rollback DROP TABLE public.TB_EMAIL;

--changeset fabriciofachini:8
CREATE SEQUENCE IF NOT EXISTS public.SEQ_EMAIL
    MINVALUE 1
    NO MAXVALUE
    INCREMENT BY 1
    START WITH 1
    NO CYCLE;
--rollback DROP SEQUENCE public.SEQ_EMAIL;