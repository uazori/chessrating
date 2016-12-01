-- Database: chessrating

-- DROP DATABASE chessrating;

CREATE DATABASE chessrating
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;



CREATE SCHEMA public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
IS 'standard public schema';

GRANT ALL ON SCHEMA public TO postgres;

GRANT ALL ON SCHEMA public TO PUBLIC;




CREATE TABLE public.game
(
    game_id bigint NOT NULL DEFAULT nextval('game_game_id_seq'::regclass),
    game_end timestamp without time zone,
    game_result character varying(255) COLLATE pg_catalog."default",
    game_start timestamp without time zone,
    black_player_id bigint,
    white_player_id bigint,
    winner_id bigint,
    CONSTRAINT game_pkey PRIMARY KEY (game_id),
    CONSTRAINT fk_black_player FOREIGN KEY (black_player_id)
    REFERENCES public.player (player_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_winner FOREIGN KEY (winner_id)
    REFERENCES public.player (player_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_wite_player FOREIGN KEY (white_player_id)
    REFERENCES public.player (player_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.game
    OWNER to postgres;



CREATE TABLE public.player
(
    player_id bigint NOT NULL DEFAULT nextval('player_player_id_seq'::regclass),
    player_name character varying(255) COLLATE pg_catalog."default",
    rating integer,
    surname character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT player_pkey PRIMARY KEY (player_id),
    CONSTRAINT player_player_name_surname_key UNIQUE (player_name, surname)
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.player
    OWNER to postgres;
