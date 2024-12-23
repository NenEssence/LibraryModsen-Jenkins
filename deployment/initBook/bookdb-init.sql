CREATE SEQUENCE IF NOT EXISTS public.books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.books (
                                            id integer NOT NULL DEFAULT nextval('books_id_seq'::regclass),
                                            isbn character varying(13) COLLATE pg_catalog."default" NOT NULL,
                                            title character varying(255) COLLATE pg_catalog."default" NOT NULL,
                                            genre character varying(100) COLLATE pg_catalog."default",
                                            description text COLLATE pg_catalog."default",
                                            author character varying(255) COLLATE pg_catalog."default" NOT NULL,
                                            CONSTRAINT books_pkey PRIMARY KEY (id),
                                            CONSTRAINT books_isbn_key UNIQUE (isbn)
);

INSERT INTO public.books (isbn, title, genre, description, author) VALUES
('9781234567890', 'Spring in Action', 'Fiction', 'Description of Book 1', 'Author 1'),
('9781234567891', 'Java Start', 'Non-Fiction', 'Description of Book 2', 'Author 2'),
('9781234567892', 'MicroArch', 'Science', 'Description of Book 3', 'Author 3')
ON CONFLICT DO NOTHING;