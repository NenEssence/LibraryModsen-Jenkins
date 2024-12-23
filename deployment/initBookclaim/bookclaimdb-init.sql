CREATE SEQUENCE IF NOT EXISTS public.bookclaims_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.bookclaims (
    id integer NOT NULL DEFAULT nextval('bookclaims_id_seq'::regclass),
    book_id integer NOT NULL,
    claim_date timestamp with time zone,
    return_date timestamp with time zone,
    CONSTRAINT bookclaims_pkey PRIMARY KEY (id),
    CONSTRAINT unique_book_id UNIQUE (book_id)
    );

INSERT INTO public.bookclaims (book_id, claim_date, return_date) VALUES
(1, NOW() - INTERVAL '10 days', NOW() - INTERVAL '5 days'),
(2, NOW() - INTERVAL '20 days', NOW() - INTERVAL '10 days'),
(3, NOW() - INTERVAL '30 days', NOW() - INTERVAL '15 days')
ON CONFLICT DO NOTHING;