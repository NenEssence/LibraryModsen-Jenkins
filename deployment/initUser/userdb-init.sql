CREATE SEQUENCE IF NOT EXISTS public.user_credentials_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.user_credentials (
id integer NOT NULL DEFAULT nextval('user_credentials_id_seq'::regclass),
username character varying(255) COLLATE pg_catalog."default" NOT NULL,
email character varying(255) COLLATE pg_catalog."default" NOT NULL,
password character varying(255) COLLATE pg_catalog."default" NOT NULL,
CONSTRAINT user_credentials_pkey PRIMARY KEY (id),
CONSTRAINT user_credentials_username_key UNIQUE (username)
);