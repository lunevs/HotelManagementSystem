--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 16.0

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

DROP DATABASE IF EXISTS hotel_db;
--
-- Name: hotel_db; Type: DATABASE; Schema: -; Owner: hotel_db_admin
--

CREATE DATABASE hotel_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'C';


ALTER DATABASE hotel_db OWNER TO hotel_db_admin;

\connect hotel_db

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
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    deleted boolean NOT NULL,
    role_id integer,
    id bigint NOT NULL,
    email character varying(255),
    name character varying(255),
    password character varying(255)
);


ALTER TABLE public.accounts OWNER TO hotel_db_admin;

--
-- Name: accounts_bookings; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.accounts_bookings (
    account_id bigint NOT NULL,
    bookings_id bigint NOT NULL
);


ALTER TABLE public.accounts_bookings OWNER TO hotel_db_admin;

--
-- Name: accounts_hotels; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.accounts_hotels (
    account_id bigint NOT NULL,
    hotels_id bigint NOT NULL
);


ALTER TABLE public.accounts_hotels OWNER TO hotel_db_admin;

--
-- Name: accounts_id_seq; Type: SEQUENCE; Schema: public; Owner: hotel_db_admin
--

CREATE SEQUENCE public.accounts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.accounts_id_seq OWNER TO hotel_db_admin;

--
-- Name: accounts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: hotel_db_admin
--

ALTER SEQUENCE public.accounts_id_seq OWNED BY public.accounts.id;


--
-- Name: amenity; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.amenity (
    price real,
    type smallint,
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255),
    CONSTRAINT amenity_type_check CHECK (((type >= 0) AND (type <= 1)))
);


ALTER TABLE public.amenity OWNER TO hotel_db_admin;

--
-- Name: amenity_id_seq; Type: SEQUENCE; Schema: public; Owner: hotel_db_admin
--

CREATE SEQUENCE public.amenity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.amenity_id_seq OWNER TO hotel_db_admin;

--
-- Name: amenity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: hotel_db_admin
--

ALTER SEQUENCE public.amenity_id_seq OWNED BY public.amenity.id;


--
-- Name: booking; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.booking (
    booking_status smallint,
    deleted boolean NOT NULL,
    number_of_guests integer NOT NULL,
    account_id bigint,
    end_date timestamp(6) without time zone NOT NULL,
    id bigint NOT NULL,
    room_id bigint,
    start_date timestamp(6) without time zone NOT NULL,
    CONSTRAINT booking_booking_status_check CHECK (((booking_status >= 0) AND (booking_status <= 4)))
);


ALTER TABLE public.booking OWNER TO hotel_db_admin;

--
-- Name: booking_id_seq; Type: SEQUENCE; Schema: public; Owner: hotel_db_admin
--

CREATE SEQUENCE public.booking_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.booking_id_seq OWNER TO hotel_db_admin;

--
-- Name: booking_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: hotel_db_admin
--

ALTER SEQUENCE public.booking_id_seq OWNED BY public.booking.id;


--
-- Name: hotels; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.hotels (
    deleted boolean NOT NULL,
    hotel_admin_id bigint,
    id bigint NOT NULL,
    city character varying(255),
    description character varying(255),
    name character varying(255)
);


ALTER TABLE public.hotels OWNER TO hotel_db_admin;

--
-- Name: hotels_amenities; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.hotels_amenities (
    amenity_id bigint NOT NULL,
    hotel_id bigint NOT NULL
);


ALTER TABLE public.hotels_amenities OWNER TO hotel_db_admin;

--
-- Name: hotels_id_seq; Type: SEQUENCE; Schema: public; Owner: hotel_db_admin
--

CREATE SEQUENCE public.hotels_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.hotels_id_seq OWNER TO hotel_db_admin;

--
-- Name: hotels_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: hotel_db_admin
--

ALTER SEQUENCE public.hotels_id_seq OWNED BY public.hotels.id;


--
-- Name: hotels_images; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.hotels_images (
    hotel_id bigint NOT NULL,
    images_id bigint NOT NULL
);


ALTER TABLE public.hotels_images OWNER TO hotel_db_admin;

--
-- Name: images; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.images (
    image_type smallint,
    id bigint NOT NULL,
    image_title character varying(255),
    image_bogy oid,
    CONSTRAINT images_image_type_check CHECK (((image_type >= 0) AND (image_type <= 1)))
);


ALTER TABLE public.images OWNER TO hotel_db_admin;

--
-- Name: images_id_seq; Type: SEQUENCE; Schema: public; Owner: hotel_db_admin
--

CREATE SEQUENCE public.images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.images_id_seq OWNER TO hotel_db_admin;

--
-- Name: images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: hotel_db_admin
--

ALTER SEQUENCE public.images_id_seq OWNED BY public.images.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.roles OWNER TO hotel_db_admin;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: hotel_db_admin
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO hotel_db_admin;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: hotel_db_admin
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- Name: rooms; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.rooms (
    capacity integer,
    deleted boolean NOT NULL,
    price numeric(38,2),
    hotel_id bigint,
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.rooms OWNER TO hotel_db_admin;

--
-- Name: rooms_amenities; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.rooms_amenities (
    amenity_id bigint NOT NULL,
    room_id bigint NOT NULL
);


ALTER TABLE public.rooms_amenities OWNER TO hotel_db_admin;

--
-- Name: rooms_id_seq; Type: SEQUENCE; Schema: public; Owner: hotel_db_admin
--

CREATE SEQUENCE public.rooms_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.rooms_id_seq OWNER TO hotel_db_admin;

--
-- Name: rooms_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: hotel_db_admin
--

ALTER SEQUENCE public.rooms_id_seq OWNED BY public.rooms.id;


--
-- Name: rooms_images; Type: TABLE; Schema: public; Owner: hotel_db_admin
--

CREATE TABLE public.rooms_images (
    images_id bigint NOT NULL,
    room_id bigint NOT NULL
);


ALTER TABLE public.rooms_images OWNER TO hotel_db_admin;

--
-- Name: accounts id; Type: DEFAULT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.accounts ALTER COLUMN id SET DEFAULT nextval('public.accounts_id_seq'::regclass);


--
-- Name: amenity id; Type: DEFAULT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.amenity ALTER COLUMN id SET DEFAULT nextval('public.amenity_id_seq'::regclass);


--
-- Name: booking id; Type: DEFAULT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.booking ALTER COLUMN id SET DEFAULT nextval('public.booking_id_seq'::regclass);


--
-- Name: hotels id; Type: DEFAULT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.hotels ALTER COLUMN id SET DEFAULT nextval('public.hotels_id_seq'::regclass);


--
-- Name: images id; Type: DEFAULT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.images ALTER COLUMN id SET DEFAULT nextval('public.images_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- Name: rooms id; Type: DEFAULT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.rooms ALTER COLUMN id SET DEFAULT nextval('public.rooms_id_seq'::regclass);


--
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.accounts (deleted, role_id, id, email, name, password) FROM stdin;
f	1	1	admin@gmail.com	admin	{bcrypt}$2a$12$ZJxPYIUg0al2/qf0AwqI1./QfF.WAVrYXt/GcRDMJB2CKFcSx9ksO
f	2	2	user@gmail.com	user	{bcrypt}$2a$12$ZJxPYIUg0al2/qf0AwqI1./QfF.WAVrYXt/GcRDMJB2CKFcSx9ksO
\.


--
-- Data for Name: accounts_bookings; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.accounts_bookings (account_id, bookings_id) FROM stdin;
\.


--
-- Data for Name: accounts_hotels; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.accounts_hotels (account_id, hotels_id) FROM stdin;
\.


--
-- Data for Name: amenity; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.amenity (price, type, id, description, name) FROM stdin;
10	1	1	какое-то описание сейфа	Сейф
0	1	2	небольшой холодильник в номере	Холодильник
0	1	3	позволяющий обогревать и охлаждать номер	Кондиционер
0	0	4	описание нашего классного бассейна	Бассейн
100	0	5	сауна и термальный комплекс	Сауна
100	0	6	описание аквапарка	Аквапарк
\.


--
-- Data for Name: booking; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.booking (booking_status, deleted, number_of_guests, account_id, end_date, id, room_id, start_date) FROM stdin;
\.


--
-- Data for Name: hotels; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.hotels (deleted, hotel_admin_id, id, city, description, name) FROM stdin;
f	1	1	Milan	описание нашего классного отеля в горах	Отель в горах
f	1	2	Paris	описание нашего отеля в городе, что рядом и т.д.	Городские апартаменты
f	1	3	Barcelona	первая линия, лучшие пляжи и все такое	Морской берег
\.


--
-- Data for Name: hotels_amenities; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.hotels_amenities (amenity_id, hotel_id) FROM stdin;
4	1
5	1
6	2
5	2
4	3
6	3
\.


--
-- Data for Name: hotels_images; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.hotels_images (hotel_id, images_id) FROM stdin;
\.


--
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.images (image_type, id, image_title, image_bogy) FROM stdin;
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.roles (id, name) FROM stdin;
1	ROLE_ADMIN
2	ROLE_USER
3	ROLE_MODERATOR
\.


--
-- Data for Name: rooms; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.rooms (capacity, deleted, price, hotel_id, id, name) FROM stdin;
5	f	50.00	1	1	семейный номер
2	f	23.00	1	2	Сингл номер
4	f	43.00	1	3	Дабл номер
2	f	12.00	2	4	Маленький номер
4	f	24.00	2	5	Большой номер
1	f	150.00	3	6	Одиночный номер
2	f	220.00	3	7	Двойной номер
\.


--
-- Data for Name: rooms_amenities; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.rooms_amenities (amenity_id, room_id) FROM stdin;
1	1
2	1
2	2
3	2
1	3
2	3
3	3
1	4
1	5
2	5
3	5
3	6
1	7
2	7
3	7
\.


--
-- Data for Name: rooms_images; Type: TABLE DATA; Schema: public; Owner: hotel_db_admin
--

COPY public.rooms_images (images_id, room_id) FROM stdin;
\.


--
-- Name: accounts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: hotel_db_admin
--

SELECT pg_catalog.setval('public.accounts_id_seq', 2, true);


--
-- Name: amenity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: hotel_db_admin
--

SELECT pg_catalog.setval('public.amenity_id_seq', 6, true);


--
-- Name: booking_id_seq; Type: SEQUENCE SET; Schema: public; Owner: hotel_db_admin
--

SELECT pg_catalog.setval('public.booking_id_seq', 1, false);


--
-- Name: hotels_id_seq; Type: SEQUENCE SET; Schema: public; Owner: hotel_db_admin
--

SELECT pg_catalog.setval('public.hotels_id_seq', 3, true);


--
-- Name: images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: hotel_db_admin
--

SELECT pg_catalog.setval('public.images_id_seq', 1, false);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: hotel_db_admin
--

SELECT pg_catalog.setval('public.roles_id_seq', 3, true);


--
-- Name: rooms_id_seq; Type: SEQUENCE SET; Schema: public; Owner: hotel_db_admin
--

SELECT pg_catalog.setval('public.rooms_id_seq', 7, true);


--
-- Name: accounts_bookings accounts_bookings_bookings_id_key; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.accounts_bookings
    ADD CONSTRAINT accounts_bookings_bookings_id_key UNIQUE (bookings_id);


--
-- Name: accounts accounts_email_key; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_email_key UNIQUE (email);


--
-- Name: accounts_hotels accounts_hotels_hotels_id_key; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.accounts_hotels
    ADD CONSTRAINT accounts_hotels_hotels_id_key UNIQUE (hotels_id);


--
-- Name: accounts accounts_name_key; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_name_key UNIQUE (name);


--
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- Name: amenity amenity_pkey; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.amenity
    ADD CONSTRAINT amenity_pkey PRIMARY KEY (id);


--
-- Name: booking booking_pkey; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_pkey PRIMARY KEY (id);


--
-- Name: hotels_images hotels_images_images_id_key; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.hotels_images
    ADD CONSTRAINT hotels_images_images_id_key UNIQUE (images_id);


--
-- Name: hotels hotels_name_key; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.hotels
    ADD CONSTRAINT hotels_name_key UNIQUE (name);


--
-- Name: hotels hotels_pkey; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.hotels
    ADD CONSTRAINT hotels_pkey PRIMARY KEY (id);


--
-- Name: images images_pkey; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT images_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: rooms_images rooms_images_images_id_key; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.rooms_images
    ADD CONSTRAINT rooms_images_images_id_key UNIQUE (images_id);


--
-- Name: rooms rooms_pkey; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (id);


--
-- Name: amenity ukeix17m64aa8tk3asyk4kptbdy; Type: CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.amenity
    ADD CONSTRAINT ukeix17m64aa8tk3asyk4kptbdy UNIQUE (type, name);


--
-- Name: booking fk59gn88y0g1vuxgutxw1e0bn08; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT fk59gn88y0g1vuxgutxw1e0bn08 FOREIGN KEY (account_id) REFERENCES public.accounts(id);


--
-- Name: accounts_hotels fk6ncsn0scmh5gbdixuuuvwimka; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.accounts_hotels
    ADD CONSTRAINT fk6ncsn0scmh5gbdixuuuvwimka FOREIGN KEY (hotels_id) REFERENCES public.hotels(id);


--
-- Name: accounts_bookings fk8cxi4vtmco9xtbwkdt6hdarnb; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.accounts_bookings
    ADD CONSTRAINT fk8cxi4vtmco9xtbwkdt6hdarnb FOREIGN KEY (account_id) REFERENCES public.accounts(id);


--
-- Name: rooms_images fka07du3d2uclcxxaqg4m47pevm; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.rooms_images
    ADD CONSTRAINT fka07du3d2uclcxxaqg4m47pevm FOREIGN KEY (room_id) REFERENCES public.rooms(id);


--
-- Name: accounts_bookings fkak26ma9lroldjy7e4srpooxqn; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.accounts_bookings
    ADD CONSTRAINT fkak26ma9lroldjy7e4srpooxqn FOREIGN KEY (bookings_id) REFERENCES public.booking(id);


--
-- Name: booking fkb6jhn9s6v53eb2cd9l7uhcd0m; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT fkb6jhn9s6v53eb2cd9l7uhcd0m FOREIGN KEY (room_id) REFERENCES public.rooms(id);


--
-- Name: hotels_images fkd5pauav3b72li0ocfei3xfld0; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.hotels_images
    ADD CONSTRAINT fkd5pauav3b72li0ocfei3xfld0 FOREIGN KEY (hotel_id) REFERENCES public.hotels(id);


--
-- Name: hotels_amenities fkjrhnvo3tawk0ae0gf50q0uu0k; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.hotels_amenities
    ADD CONSTRAINT fkjrhnvo3tawk0ae0gf50q0uu0k FOREIGN KEY (amenity_id) REFERENCES public.amenity(id);


--
-- Name: hotels_amenities fkkjp93s5ohwdfd05rewjxn4n59; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.hotels_amenities
    ADD CONSTRAINT fkkjp93s5ohwdfd05rewjxn4n59 FOREIGN KEY (hotel_id) REFERENCES public.hotels(id);


--
-- Name: rooms_amenities fkl21rvmb1h8n7ay7k08tb81dlc; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.rooms_amenities
    ADD CONSTRAINT fkl21rvmb1h8n7ay7k08tb81dlc FOREIGN KEY (amenity_id) REFERENCES public.amenity(id);


--
-- Name: hotels_images fkmcn2hf3scalqjwqr5he126jae; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.hotels_images
    ADD CONSTRAINT fkmcn2hf3scalqjwqr5he126jae FOREIGN KEY (images_id) REFERENCES public.images(id);


--
-- Name: rooms_amenities fkn9cmoxbyq6b4imw95jw2noliu; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.rooms_amenities
    ADD CONSTRAINT fkn9cmoxbyq6b4imw95jw2noliu FOREIGN KEY (room_id) REFERENCES public.rooms(id);


--
-- Name: rooms_images fknscn8yx2tb9ubxtxm674f1yy3; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.rooms_images
    ADD CONSTRAINT fknscn8yx2tb9ubxtxm674f1yy3 FOREIGN KEY (images_id) REFERENCES public.images(id);


--
-- Name: rooms fkp5lufxy0ghq53ugm93hdc941k; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT fkp5lufxy0ghq53ugm93hdc941k FOREIGN KEY (hotel_id) REFERENCES public.hotels(id);


--
-- Name: accounts fkt3wava8ssfdspnh3hg4col3m1; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT fkt3wava8ssfdspnh3hg4col3m1 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: accounts_hotels fktlugwbmidsek5ufi40qexp9ct; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.accounts_hotels
    ADD CONSTRAINT fktlugwbmidsek5ufi40qexp9ct FOREIGN KEY (account_id) REFERENCES public.accounts(id);


--
-- Name: hotels fktlxfte4bttm0vijlaqcrgtcha; Type: FK CONSTRAINT; Schema: public; Owner: hotel_db_admin
--

ALTER TABLE ONLY public.hotels
    ADD CONSTRAINT fktlxfte4bttm0vijlaqcrgtcha FOREIGN KEY (hotel_admin_id) REFERENCES public.accounts(id);


--
-- PostgreSQL database dump complete
--

