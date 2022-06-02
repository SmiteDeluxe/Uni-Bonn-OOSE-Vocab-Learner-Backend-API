--
-- PostgreSQL database dump
--

-- Dumped from database version 12.3 (Debian 12.3-1.pgdg100+1)
-- Dumped by pg_dump version 12.3 (Debian 12.3-1.pgdg100+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: apptest1; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.apptest1 (
    id integer NOT NULL,
    answer character varying(255),
    question character varying(255),
    language character varying(255),
    phase integer
);


ALTER TABLE public.apptest1 OWNER TO root;

--
-- Name: apptest10; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.apptest10 (
    id integer NOT NULL,
    answer character varying(255),
    question character varying(255),
    language character varying(255),
    phase integer
);


ALTER TABLE public.apptest10 OWNER TO root;

--
-- Name: apptest10_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.apptest10_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.apptest10_id_seq OWNER TO root;

--
-- Name: apptest10_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.apptest10_id_seq OWNED BY public.apptest10.id;


--
-- Name: apptest1_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.apptest1_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.apptest1_id_seq OWNER TO root;

--
-- Name: apptest1_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.apptest1_id_seq OWNED BY public.apptest1.id;


--
-- Name: apptest21; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.apptest21 (
    id integer NOT NULL,
    answer character varying(255),
    question character varying(255),
    language character varying(255),
    phase integer
);


ALTER TABLE public.apptest21 OWNER TO root;

--
-- Name: apptest21_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.apptest21_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.apptest21_id_seq OWNER TO root;

--
-- Name: apptest21_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.apptest21_id_seq OWNED BY public.apptest21.id;


--
-- Name: vocab; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vocab (
    email character varying(255) NOT NULL,
    password text NOT NULL,
    tablename character varying(255) NOT NULL,
    ban timestamp without time zone,
    try bigint
);


ALTER TABLE public.vocab OWNER TO postgres;

--
-- Name: apptest1 id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.apptest1 ALTER COLUMN id SET DEFAULT nextval('public.apptest1_id_seq'::regclass);


--
-- Name: apptest10 id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.apptest10 ALTER COLUMN id SET DEFAULT nextval('public.apptest10_id_seq'::regclass);


--
-- Name: apptest21 id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.apptest21 ALTER COLUMN id SET DEFAULT nextval('public.apptest21_id_seq'::regclass);


--
-- Data for Name: apptest1; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.apptest1 (id, answer, question, language, phase) FROM stdin;
3	stock	Aktie	eng	0
2	duck	Ente	eng	2
4	el dinero	das Geld	esp	0
1	banana	Banane	eng	4
5	Kerze	candle	eng	0
\.


--
-- Data for Name: apptest10; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.apptest10 (id, answer, question, language, phase) FROM stdin;
1	app	Anwendung	eng	1
\.


--
-- Data for Name: apptest21; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.apptest21 (id, answer, question, language, phase) FROM stdin;
\.


--
-- Data for Name: vocab; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vocab (email, password, tablename, ban, try) FROM stdin;
apptest@	\\xc30d040703028ede81939502883b60d2350175bf7f93b6e1faee1e3d51ef6ad91c4785b6ca3e7f30409f5351472a97a7d9aaada97f3f171adc8bf2e60a76d8ca835591fcb9ce	apptest1	\N	3
apptest@4	\\xc30d04070302de6e86d909b785ed7ed23501fc93b13ed93b45495eb8ff383396cb4847f29e77a31f49986d5d58080c3ffb34bdd15b97763f829e454387e6529ba025ae719bf6	apptest21	\N	3
apptest@1	\\xc30d0407030249307a4d976979cf69d23501aa9d249b2cd68b8ef55b3467c3c2fc1e8b57545dcf8d5650a5f469529f17e376077e69ccc1724129030d72ce475de441b54a263d	apptest10	\N	3
\.


--
-- Name: apptest10_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.apptest10_id_seq', 1, true);


--
-- Name: apptest1_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.apptest1_id_seq', 5, true);


--
-- Name: apptest21_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.apptest21_id_seq', 1, false);


--
-- Name: apptest10 apptest10_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.apptest10
    ADD CONSTRAINT apptest10_pkey PRIMARY KEY (id);


--
-- Name: apptest1 apptest1_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.apptest1
    ADD CONSTRAINT apptest1_pkey PRIMARY KEY (id);


--
-- Name: apptest21 apptest21_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.apptest21
    ADD CONSTRAINT apptest21_pkey PRIMARY KEY (id);


--
-- Name: TABLE vocab; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.vocab TO root;


--
-- PostgreSQL database dump complete
--

