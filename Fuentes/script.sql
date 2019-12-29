CREATE DATABASE HELPDESK;

CREATE TABLE ROLES(
	IDROL SERIAL NOT NULL,
	ROLENAME VARCHAR(20) NOT NULL,
	DESCRIPTION VARCHAR(500),
	CONSTRAINT PK_IDROL PRIMARY KEY (IDROL)
);

CREATE TABLE MENUS(
	IDMENU INT NOT NULL,
	MENU VARCHAR(20) NOT NULL,
	CONTROLLER VARCHAR(20) NOT NULL,
	IDPARENT INT,
	CONSTRAINT PK_IDMENU PRIMARY KEY(IDMENU),
	CONSTRAINT FK_IDPARENT FOREIGN KEY(IDPARENT) REFERENCES MENUS(IDMENU)
);

CREATE TABLE PERMISSIONS(
	IDPERMISSIONS SERIAL NOT NULL,
	IDMENU INT NOT NULL,
	IDROLE INT NOT NULL,
	CONSTRAINT PK_IDPERMISSIONS PRIMARY KEY(IDPERMISSIONS),
	CONSTRAINT FK_IDMENU FOREIGN KEY(IDMENU) REFERENCES MENUS(IDMENU),
	CONSTRAINT FK_IDROLE FOREIGN KEY(IDROLE) REFERENCES ROLES(IDROL)
);

CREATE TABLE USERS(
	IDUSER SERIAL  NOT NULL,
	USERNAME VARCHAR(20)  NOT NULL,
	PASSWORD VARCHAR(500)  NOT NULL,
	FIRSTNAME VARCHAR(20)  NOT NULL,
	LASTNAME VARCHAR(20)  NOT NULL,
	EMAIL VARCHAR(30)  NOT NULL,
	TELEPHONE VARCHAR(20)  NOT NULL,
	IDROLE INT  NOT NULL,
	CONSTRAINT PK_IDUSER PRIMARY KEY(IDUSER),
	CONSTRAINT FK_IDROL FOREIGN KEY(IDROLE) REFERENCES ROLES(IDROL)
);

-- **Creacion de indices en la tabla users
CREATE INDEX INDEX_USER on USERS(USERNAME);
CREATE INDEX INDEX_EMAIL on USERS(EMAIL);

CREATE TABLE DEPARTMENTS(
	IDDEPTO SERIAL  NOT NULL,
	DEPTONAME VARCHAR(75)  NOT NULL,
	DESCRIPTION VARCHAR(500),
	CONSTRAINT PK_IDDEPTO PRIMARY KEY(IDDEPTO)
);


CREATE TABLE DEPTOBYUSERS(
	IDDEPTO INT NOT NULL,
	IDUSER INT NOT NULL,
	CONSTRAINT PK_DEPUSER PRIMARY KEY(IDDEPTO,IDUSER),
	CONSTRAINT FK_IDDEPTO FOREIGN KEY(IDDEPTO) REFERENCES DEPARTMENTS(IDDEPTO),
	CONSTRAINT FK_IDUSER FOREIGN KEY(IDUSER) REFERENCES USERS(IDUSER)
);

CREATE TABLE CLASSIFICATIONS(
	IDCLASSIFICATION SERIAL  NOT NULL,
	CLASSIFICATION VARCHAR(50)  NOT NULL,
	DESCRIPTION VARCHAR(500),
	CONSTRAINT PK_IDCLASSFICATION PRIMARY KEY(IDCLASSIFICATION)
);


CREATE TABLE INCIDENCES(
	IDINCIDENCE SERIAL NOT NULL,
	TITLE VARCHAR(50) NOT NULL,
	DESCRIPTION VARCHAR(500)  NOT NULL,
	CREATIONDAY TIMESTAMP  NOT NULLss,
	FINALDATE TIMESTAMP  NOT NULL,
	TOTALCOST DECIMAL(16,2)  NOT NULL,
	PRIORITY INT NOT NULL,
	IDCLASSIFICATION INT  NOT NULL,
	IDCREATOR INT  NOT NULL,
	IDDEPTO INT NOT NULL,
	CONSTRAINT PK_IDINCIDENCE PRIMARY KEY(IDINCIDENCE),
	CONSTRAINT FK_IDCLASSIFICATION FOREIGN KEY(IDCLASSIFICATION) REFERENCES CLASSIFICATIONS(IDCLASSIFICATION),
	CONSTRAINT FK_IDCREATOR FOREIGN KEY(IDCREATOR) REFERENCES USERS(IDUSER),
	CONSTRAINT FK_DEPTO FOREIGN KEY(IDDEPTO) REFERENCES DEPARTMENTS(IDDEPTO)
);

alter table incidences alter column TOTALCOST set default 0.0;
alter table incidences alter column CREATIONDAY set default current_timestamp;

CREATE TABLE NOTES(
	IDNOTA SERIAL  NOT NULL,
	DESCRIPTION VARCHAR(500)  NOT NULL,
	NOTETYPE INT NOT NULL,
	IDHOLDER INT  NOT NULL,
	IDINCIDENCE INT  NOT NULL,
	CONSTRAINT PK_IDNOTA PRIMARY KEY(IDNOTA),
	CONSTRAINT FK_IDHOLDER FOREIGN KEY(IDHOLDER) REFERENCES USERS(IDUSER),
	CONSTRAINT FK_INCIDENCE FOREIGN KEY(IDINCIDENCE) REFERENCES INCIDENCES(IDINCIDENCE)
); 

CREATE TABLE INCIDENCEBYRECEPTOR(
	IDIBR SERIAL  NOT NULL,
	STARTDATE TIMESTAMP,
	FINALDATE TIMESTAMP,
	STATUS INT   NOT NULL,
	IDRECEPTOR INT  NOT NULL,
	IDINCIDENCE INT  NOT NULL,
	CONSTRAINT PK_IDIBR PRIMARY KEY(IDIBR),
	CONSTRAINT FK_IDRECEPTOR FOREIGN KEY(IDRECEPTOR) REFERENCES USERS(IDUSER),
	CONSTRAINT FK_INCIDENCE FOREIGN KEY(IDINCIDENCE) REFERENCES INCIDENCES(IDINCIDENCE)
);

CREATE TABLE MANAGEMENTS(
	IDMANAGEMENT SERIAL  NOT NULL,
	TITLE VARCHAR(50)  NOT NULL,
	DESCRIPTION VARCHAR(500)  NOT NULL,
	CORRECTIONDAY TIMESTAMP  NOT NULL,
	TYPEMANAGEMENT INT  NOT NULL,
	ATTACHFILE VARCHAR(500),
	COSTMSG DECIMAL(16,2) NOT NULL DEFAULT 0.0,
	IDIBR INT  NOT NULL,
	CONSTRAINT PK_IDMANAGEMENT PRIMARY KEY(IDMANAGEMENT),
	CONSTRAINT FK_IDIBR FOREIGN KEY(IDIBR) REFERENCES INCIDENCEBYRECEPTOR(IDIBR)
);

-- Departamentos de por Default 
insert into departments(deptoname,description) values('Multimedia','Departamento Especializado en la creacion de contenido digital creativo');
insert into departments(deptoname,description) values('Redes','Area de alta importancia, se almacenan Servidores que contienen informacion de la empresa');
insert into departments(deptoname,description) values('Tecnologia','Serie de dispositivos que se presentan al cliente para la venta o soporte tecnico');
insert into departments(deptoname,description) values('Diseño','Se desarrollan labores relacionada al marketing en todas sus etapas');

-- Roles por Default 
insert into roles(rolename,description) values ('Gerente','Este rol posee acceso a todos los menus');
insert into roles(rolename,description) values ('Lider','Tendra a cargo un grupo determinado de personas a su a cargo.');
insert into roles(rolename,description) values ('Receptor','Son personas tecnicos o capacitados para resolver algun tipo de incidencia o fallo');
insert into roles(rolename,description) values ('Empleado','Este es el rol por defecto que se le asigna a un nuevo usuario despues de registrarse');


-- Creacionde Menus 
insert into menus values(1,'Panel Principal','/Principal',null);
insert into menus values(2,'Nueva','/Incidencias',null);
insert into menus values(3,'Grupo','/Grupo',null);
insert into menus values(4,'Empleados','/Empleados',null);
insert into menus values(5,'Departamentos','/Departamentos',null);
insert into menus values(6,'Clasificaciones','/Clasificaciones',null);
insert into menus values(7,'Reportes','/Reportes',null);

-- Asignado Permisos sobre roles por menus 
-- Menus sobre el Gerente idrol 1
insert into permissions(idmenu,idrole) select idMenu, 1 from menus
where idmenu in (1,2,4,5,6,7);
-- Menus sobre el Lider idrol 2 
insert into permissions(idmenu,idrole) select idMenu, 2 from menus
where idmenu in (1,2,3,7);
-- Menus sobre el Receptor idrol 3 
insert into permissions(idmenu,idrole) select idMenu, 3 from menus
where idmenu in (1,3,7);
-- Menus sobre el Empleado idrol 4 
insert into permissions(idmenu,idrole) values (1,4);



select * from menus where idMenu in (select idmenu from permissions where idrole = 1);
alter table incidences alter column creationday set not null;
alter table incidences alter column totalcost set default 0.0;
select * from incidencebyreceptor;

 




