--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

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
-- Name: deletedeptobyusers(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.deletedeptobyusers(fiduser integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
BEGIN
delete from deptobyusers where iduser = fiduser;
delete from users where iduser = fiduser;
RETURN 1;
END; $$;


ALTER FUNCTION public.deletedeptobyusers(fiduser integer) OWNER TO postgres;

--
-- Name: updatedepto(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.updatedepto(fiduser integer, fiddepto integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
BEGIN
update deptobyusers set iddepto = fiddepto where iduser = fiduser;
RETURN 1;
END; $$;


ALTER FUNCTION public.updatedepto(fiduser integer, fiddepto integer) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: classifications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.classifications (
    idclassification integer NOT NULL,
    classification character varying(50) NOT NULL,
    description character varying(500)
);


ALTER TABLE public.classifications OWNER TO postgres;

--
-- Name: classifications_idclassification_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.classifications_idclassification_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.classifications_idclassification_seq OWNER TO postgres;

--
-- Name: classifications_idclassification_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.classifications_idclassification_seq OWNED BY public.classifications.idclassification;


--
-- Name: departments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.departments (
    iddepto integer NOT NULL,
    deptoname character varying(75) NOT NULL,
    description character varying(500)
);


ALTER TABLE public.departments OWNER TO postgres;

--
-- Name: departments_iddepto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.departments_iddepto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.departments_iddepto_seq OWNER TO postgres;

--
-- Name: departments_iddepto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.departments_iddepto_seq OWNED BY public.departments.iddepto;


--
-- Name: deptobyusers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.deptobyusers (
    iddepto integer NOT NULL,
    iduser integer NOT NULL
);


ALTER TABLE public.deptobyusers OWNER TO postgres;

--
-- Name: incidencebyreceptor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.incidencebyreceptor (
    idibr integer NOT NULL,
    startdate timestamp without time zone,
    finaldate timestamp without time zone,
    status integer NOT NULL,
    idreceptor integer NOT NULL,
    idincidence integer NOT NULL
);


ALTER TABLE public.incidencebyreceptor OWNER TO postgres;

--
-- Name: incidencebyreceptor_idibr_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.incidencebyreceptor_idibr_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.incidencebyreceptor_idibr_seq OWNER TO postgres;

--
-- Name: incidencebyreceptor_idibr_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.incidencebyreceptor_idibr_seq OWNED BY public.incidencebyreceptor.idibr;


--
-- Name: incidences; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.incidences (
    idincidence integer NOT NULL,
    title character varying(50) NOT NULL,
    description character varying(500) NOT NULL,
    creationday timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    finaldate timestamp without time zone NOT NULL,
    totalcost numeric(16,2) DEFAULT 0.0 NOT NULL,
    priority integer NOT NULL,
    idclassification integer NOT NULL,
    idcreator integer NOT NULL,
    iddepto integer NOT NULL
);


ALTER TABLE public.incidences OWNER TO postgres;

--
-- Name: incidences_idincidence_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.incidences_idincidence_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.incidences_idincidence_seq OWNER TO postgres;

--
-- Name: incidences_idincidence_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.incidences_idincidence_seq OWNED BY public.incidences.idincidence;


--
-- Name: managements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.managements (
    idmanagement integer NOT NULL,
    title character varying(50) NOT NULL,
    description character varying(500) NOT NULL,
    correctionday timestamp without time zone NOT NULL,
    typemanagement integer NOT NULL,
    attachfile character varying(500),
    costmsg numeric(16,2) DEFAULT 0.0 NOT NULL,
    idibr integer NOT NULL
);


ALTER TABLE public.managements OWNER TO postgres;

--
-- Name: managements_idmanagement_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.managements_idmanagement_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.managements_idmanagement_seq OWNER TO postgres;

--
-- Name: managements_idmanagement_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.managements_idmanagement_seq OWNED BY public.managements.idmanagement;


--
-- Name: menus; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menus (
    idmenu integer NOT NULL,
    menu character varying(20) NOT NULL,
    controller character varying(20) NOT NULL,
    idparent integer
);


ALTER TABLE public.menus OWNER TO postgres;

--
-- Name: notes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notes (
    idnota integer NOT NULL,
    description character varying(500) NOT NULL,
    notetype integer NOT NULL,
    idholder integer NOT NULL,
    idincidence integer NOT NULL
);


ALTER TABLE public.notes OWNER TO postgres;

--
-- Name: notes_idnota_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notes_idnota_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.notes_idnota_seq OWNER TO postgres;

--
-- Name: notes_idnota_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notes_idnota_seq OWNED BY public.notes.idnota;


--
-- Name: permissions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permissions (
    idpermissions integer NOT NULL,
    idmenu integer NOT NULL,
    idrole integer NOT NULL
);


ALTER TABLE public.permissions OWNER TO postgres;

--
-- Name: permissions_idpermissions_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.permissions_idpermissions_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.permissions_idpermissions_seq OWNER TO postgres;

--
-- Name: permissions_idpermissions_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.permissions_idpermissions_seq OWNED BY public.permissions.idpermissions;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    idrol integer NOT NULL,
    rolename character varying(20) NOT NULL,
    description character varying(500)
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: roles_idrol_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_idrol_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_idrol_seq OWNER TO postgres;

--
-- Name: roles_idrol_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_idrol_seq OWNED BY public.roles.idrol;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    iduser integer NOT NULL,
    username character varying(20) NOT NULL,
    password character varying(500) NOT NULL,
    firstname character varying(20) NOT NULL,
    lastname character varying(20) NOT NULL,
    email character varying(30) NOT NULL,
    telephone character varying(20) NOT NULL,
    idrole integer NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_iduser_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_iduser_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_iduser_seq OWNER TO postgres;

--
-- Name: users_iduser_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_iduser_seq OWNED BY public.users.iduser;


--
-- Name: classifications idclassification; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.classifications ALTER COLUMN idclassification SET DEFAULT nextval('public.classifications_idclassification_seq'::regclass);


--
-- Name: departments iddepto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments ALTER COLUMN iddepto SET DEFAULT nextval('public.departments_iddepto_seq'::regclass);


--
-- Name: incidencebyreceptor idibr; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incidencebyreceptor ALTER COLUMN idibr SET DEFAULT nextval('public.incidencebyreceptor_idibr_seq'::regclass);


--
-- Name: incidences idincidence; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incidences ALTER COLUMN idincidence SET DEFAULT nextval('public.incidences_idincidence_seq'::regclass);


--
-- Name: managements idmanagement; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.managements ALTER COLUMN idmanagement SET DEFAULT nextval('public.managements_idmanagement_seq'::regclass);


--
-- Name: notes idnota; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notes ALTER COLUMN idnota SET DEFAULT nextval('public.notes_idnota_seq'::regclass);


--
-- Name: permissions idpermissions; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permissions ALTER COLUMN idpermissions SET DEFAULT nextval('public.permissions_idpermissions_seq'::regclass);


--
-- Name: roles idrol; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN idrol SET DEFAULT nextval('public.roles_idrol_seq'::regclass);


--
-- Name: users iduser; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN iduser SET DEFAULT nextval('public.users_iduser_seq'::regclass);


--
-- Data for Name: classifications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.classifications (idclassification, classification, description) FROM stdin;
1	MANTENIMIENTO	En esta clasificacion pertenecen todas las inciencias reportadas por la falta de mnto preventivo o correctivo
2	CAMBIOS DE HORARIOS	Problemas surgidos a partir de un cambio de horarios entre el personas o horarios de acceso a los diferentes departamentos
3	TEMPERATURA	Relacionada a reportes de fallas en los diferentes dispositivos debido a temperaturas inadecuadas al area de trabajo
6	DENEGACION DE SERVICIO	Inconveniente producido por la falta de acceso a un servicio dentro de los cuales no se reconoce la identidad o permisos de una determinada persona
7	ACCESSO NO AUTORIZADO	Denegacion de acceso hacia una area o parte del sistema provocado por no reconocer a una persona como miembro de la empresa
8	PERDIDA DE DATOS	Fuga o perdida de informacion que se requeria para continuar con un proceso establecido
5	INFRAESTRUCTURA	Inconveniente provocado por la falla o zona de peligro de una esructura dentro de la empresa
4	ATAQUE DEL SISTEMA	Clasificacion creada para referencias cualquier tipo de ataque fisico como virtual hacia los mecanismos con los que trabaja la empresa
9	ELEMENTO EN MAL ESTADO	Describe que un elemento, producto o servicio contiene problemas en su funcionamiento normal
\.


--
-- Data for Name: departments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.departments (iddepto, deptoname, description) FROM stdin;
1	Multimedia	Departamento Especializado en la creacion de contenido digital creativo y dinamico
2	Redes	Area de alta importancia, se almacenan dispositivos que contienen informacion importante de la empresa
3	Tecnologia	Serie de dispositivos que se presentan al cliente para la venta o soporte tecnico
4	Diseño	Se desarrollan labores relacionada al marketing digital en todas sus etapas
\.


--
-- Data for Name: deptobyusers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.deptobyusers (iddepto, iduser) FROM stdin;
1	1
1	2
2	3
2	4
2	5
2	6
2	7
2	8
2	9
2	10
2	11
2	12
2	13
3	14
3	15
3	16
3	17
3	18
3	19
3	20
3	21
3	22
3	23
3	24
3	25
\.


--
-- Data for Name: incidencebyreceptor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.incidencebyreceptor (idibr, startdate, finaldate, status, idreceptor, idincidence) FROM stdin;
1	\N	\N	5	5	1
2	\N	\N	5	6	1
3	\N	\N	3	8	1
4	\N	\N	4	4	2
\.


--
-- Data for Name: incidences; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.incidences (idincidence, title, description, creationday, finaldate, totalcost, priority, idclassification, idcreator, iddepto) FROM stdin;
1	Temperatura alta en el DataCenter	Se ha reportado un aumento en la temperatura en el Data Center del Depto de redes, podria afectar el funcionamiento normal de los equipos	2020-01-04 09:29:55.251	2020-01-13 00:00:00	0.00	2	3	3	2
2	Contruccion de nuevos cubiculos	La gerencia ha autorizado el financiamientos para realizar el contrato del personal de construccion para llevar a cabo la reconstruccion de los nuevos cubiculos, debido al accidente ocurrido.	2020-01-04 17:23:02.143	2020-01-31 00:00:00	0.00	1	5	3	2
\.


--
-- Data for Name: managements; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.managements (idmanagement, title, description, correctionday, typemanagement, attachfile, costmsg, idibr) FROM stdin;
1	Pedido Cotizacion de Pieza	He mandado una solicitud al departamento de ventas para que me provean la cotizacion de la pieza que necesito para comenzar con la reparacion	2020-01-04 16:25:28.67	1	\N	0.00	3
2	Autorizacion de compra	He recibido la cotizacion y solo espero autorizacion de mi lider para poder realizar la compra	2020-01-04 16:54:42.057	2	\N	0.00	3
3	Compra de Pieza	Este recibi el paquete que contien las piezas y las instrucciones, se realizo un deposito a la cuenta de la empresa que proporciono la pieza	2020-01-04 16:56:06.797	3	\N	79.56	3
4	Contratacion de Personal	He contactado con una empresa constructora de buena reputacion para iniciar con los diseños de los nuevos espacios para nuestros colegas	2020-01-04 17:34:48.968	1	\N	0.00	4
5	Inicio de construccion	Este dia se comenzaron los primeros prepatativos, los ingenieros presentaron los planos y se procedio a desalojar al personal que se encontraba laborando en esa area	2020-01-04 17:36:30.163	3	\N	0.00	4
6	Compra de material	Este dia los trabajadores se habian quedado sin material, al parecer habia un error en el inventario inicial, debido a esto se realizo una compra de forma imprevista de material	2020-01-04 17:38:38.603	3	\N	109.47	4
7	Envio de informes	Se enviaron los documentos donde se representa los detalles de los gastos de viaticos y demas que se han gastado en las ultimas semanas	2020-01-04 17:40:19.545	1	\N	0.00	4
8	Cierre de obra	Los trabajadores finalizaron la reparacion de los cubiculos, se espera para esta semana se puedan reubicar los empleados	2020-01-04 17:42:09.838	3	\N	0.00	4
\.


--
-- Data for Name: menus; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.menus (idmenu, menu, controller, idparent) FROM stdin;
1	Panel Principal	/Principal	\N
2	Nueva Incidencia	/Incidencias	\N
3	Grupo	/Grupo	\N
4	Empleados	/Empleados	\N
5	Departamentos	/Departamentos	\N
6	Clasificaciones	/Clasificaciones	\N
7	Reportes	/Reportes	\N
\.


--
-- Data for Name: notes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notes (idnota, description, notetype, idholder, idincidence) FROM stdin;
1	Al parecer el fallo se debe a una parte del AC la cual no cuento con los conocimientos tecnicos para poder repararlo	2	5	1
2	Buen dia, informo que no podre tomar dicha incidencia, debido a problemas de salud, presentare mi hoja de consulta firmada y sellada	2	6	1
\.


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.permissions (idpermissions, idmenu, idrole) FROM stdin;
1	1	1
2	2	1
3	4	1
4	5	1
5	6	1
6	7	1
7	1	2
8	2	2
9	3	2
10	7	2
11	1	3
12	3	3
13	7	3
14	1	4
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (idrol, rolename, description) FROM stdin;
1	Gerente	Este rol posee acceso a todos los menus
2	Lider	Tendra a cargo un grupo determinado de personas a su a cargo.
3	Receptor	Son personas tecnicos o capacitados para resolver algun tipo de incidencia o fallo
4	Empleado	Este es el rol por defecto que se le asigna a un nuevo usuario despues de registrarse
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (iduser, username, password, firstname, lastname, email, telephone, idrole) FROM stdin;
1	MCNERIO	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Mario	Nerio	ax.minck@gmail.com	70184751	1
2	CNKBLANCO	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Marvin	Chinque	cnkblanco@gmail.com	74012985	1
3	ROGER85	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Roberto	Moncada	roger85@gmail.com	61254896	2
4	FORC36	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Hector	Fuentes	force@gmail.com	74445893	3
5	ALUX75	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Alejandra	Alvarez	alux75@gmail.com	75521684	3
6	NOVA94	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Nolberto	Jimenez	novai@gmail.com	61234577	3
7	LINN45	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Eugenio	Linares	linares@gmail.com	61452177	3
8	CHARLY3	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Carlos	Hernandez	carlosh@gmail.com	78787952	3
9	MONTZ45	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Amadeo	Monserrat	amadeom@gmail.com	79792215	3
10	ZEUS01	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Armando	Guillen	arm.g@gmail.com	71423345	3
11	JORGEH	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Jorge	Hernandez	jorh@gmail.com	78954584	3
12	AMILCAR	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Amilcar	Eli	milcar@gmail.com	78541235	3
13	ARES9	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Alonso	Elias	ariel@gmail.com	74125366	3
14	VERONIC4R	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Veronica	Rosario	veronica4@gmail.com	64656978	2
15	PEREZ36	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Hugo	Perez	hperez@gmail.com	75121317	3
16	ALE503	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Alex	Marcial	am503@gmail.com	74717127	3
17	MARCO41	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Marco	Serrano	adol.a@gmail.com	65448547	3
18	VICTORZL	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Victor	Mendoza	victormz@gmail.com	69854124	3
19	NAHUM	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Nahum	Tepas	nahumss@gmail.com	66336612	3
20	FADE48	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Jesus	Martinez	fademtz@gmail.com	69686547	3
21	ALEXANDERM	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Felipe	Alexander	flp.ale@gmail.com	78542641	3
22	MONKD41	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Francisco	Manuel	fran.m4@gmail.com	78454648	3
23	ANDY98	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Andy	Marcelino	andy98@gmail.com	71747578	3
24	GNU14	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Adriel	Ovidio	gnuovidio@gmail.com	79658985	3
25	RYDER95	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	Jose	Angel	joseange95@gmail.com	76466694	3
\.


--
-- Name: classifications_idclassification_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.classifications_idclassification_seq', 9, true);


--
-- Name: departments_iddepto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.departments_iddepto_seq', 4, true);


--
-- Name: incidencebyreceptor_idibr_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.incidencebyreceptor_idibr_seq', 4, true);


--
-- Name: incidences_idincidence_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.incidences_idincidence_seq', 2, true);


--
-- Name: managements_idmanagement_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.managements_idmanagement_seq', 8, true);


--
-- Name: notes_idnota_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notes_idnota_seq', 2, true);


--
-- Name: permissions_idpermissions_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.permissions_idpermissions_seq', 14, true);


--
-- Name: roles_idrol_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_idrol_seq', 4, true);


--
-- Name: users_iduser_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_iduser_seq', 25, true);


--
-- Name: deptobyusers pk_depuser; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deptobyusers
    ADD CONSTRAINT pk_depuser PRIMARY KEY (iddepto, iduser);


--
-- Name: classifications pk_idclassfication; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.classifications
    ADD CONSTRAINT pk_idclassfication PRIMARY KEY (idclassification);


--
-- Name: departments pk_iddepto; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT pk_iddepto PRIMARY KEY (iddepto);


--
-- Name: incidencebyreceptor pk_idibr; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incidencebyreceptor
    ADD CONSTRAINT pk_idibr PRIMARY KEY (idibr);


--
-- Name: incidences pk_idincidence; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incidences
    ADD CONSTRAINT pk_idincidence PRIMARY KEY (idincidence);


--
-- Name: managements pk_idmanagement; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.managements
    ADD CONSTRAINT pk_idmanagement PRIMARY KEY (idmanagement);


--
-- Name: menus pk_idmenu; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT pk_idmenu PRIMARY KEY (idmenu);


--
-- Name: notes pk_idnota; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notes
    ADD CONSTRAINT pk_idnota PRIMARY KEY (idnota);


--
-- Name: permissions pk_idpermissions; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT pk_idpermissions PRIMARY KEY (idpermissions);


--
-- Name: roles pk_idrol; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT pk_idrol PRIMARY KEY (idrol);


--
-- Name: users pk_iduser; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT pk_iduser PRIMARY KEY (iduser);


--
-- Name: index_email; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_email ON public.users USING btree (email);


--
-- Name: index_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_user ON public.users USING btree (username);


--
-- Name: incidences fk_depto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incidences
    ADD CONSTRAINT fk_depto FOREIGN KEY (iddepto) REFERENCES public.departments(iddepto);


--
-- Name: incidences fk_idclassification; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incidences
    ADD CONSTRAINT fk_idclassification FOREIGN KEY (idclassification) REFERENCES public.classifications(idclassification);


--
-- Name: incidences fk_idcreator; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incidences
    ADD CONSTRAINT fk_idcreator FOREIGN KEY (idcreator) REFERENCES public.users(iduser);


--
-- Name: deptobyusers fk_iddepto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deptobyusers
    ADD CONSTRAINT fk_iddepto FOREIGN KEY (iddepto) REFERENCES public.departments(iddepto);


--
-- Name: notes fk_idholder; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notes
    ADD CONSTRAINT fk_idholder FOREIGN KEY (idholder) REFERENCES public.users(iduser);


--
-- Name: managements fk_idibr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.managements
    ADD CONSTRAINT fk_idibr FOREIGN KEY (idibr) REFERENCES public.incidencebyreceptor(idibr);


--
-- Name: permissions fk_idmenu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT fk_idmenu FOREIGN KEY (idmenu) REFERENCES public.menus(idmenu);


--
-- Name: menus fk_idparent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT fk_idparent FOREIGN KEY (idparent) REFERENCES public.menus(idmenu);


--
-- Name: incidencebyreceptor fk_idreceptor; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incidencebyreceptor
    ADD CONSTRAINT fk_idreceptor FOREIGN KEY (idreceptor) REFERENCES public.users(iduser);


--
-- Name: users fk_idrol; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_idrol FOREIGN KEY (idrole) REFERENCES public.roles(idrol);


--
-- Name: permissions fk_idrole; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT fk_idrole FOREIGN KEY (idrole) REFERENCES public.roles(idrol);


--
-- Name: deptobyusers fk_iduser; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deptobyusers
    ADD CONSTRAINT fk_iduser FOREIGN KEY (iduser) REFERENCES public.users(iduser);


--
-- Name: notes fk_incidence; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notes
    ADD CONSTRAINT fk_incidence FOREIGN KEY (idincidence) REFERENCES public.incidences(idincidence);


--
-- Name: incidencebyreceptor fk_incidence; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incidencebyreceptor
    ADD CONSTRAINT fk_incidence FOREIGN KEY (idincidence) REFERENCES public.incidences(idincidence);


--
-- PostgreSQL database dump complete
--

