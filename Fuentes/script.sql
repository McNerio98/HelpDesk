DROP DATABASE IF EXISTS HELPDESK;
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
	DESCRIPTION VARCHAR(500),
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
--Se crea indice ya que el campo username sera frecuentemente utilizado
create index indexUser on users(username);
create index index_Email on users(email);


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
	IDCLASSIFICATION INT  NOT NULL,
	CLASSIFICATION VARCHAR(20)  NOT NULL,
	DESCRIPTION VARCHAR(500),
	CONSTRAINT PK_IDCLASSFICATION PRIMARY KEY(IDCLASSIFICATION)
);
CREATE TYPE PRIORYTY AS ENUM('BAJA', 'MEDIA', 'ALTA');
CREATE TABLE INCIDENCES(
	IDINCIDENCE SERIAL NOT NULL,
	TITLE VARCHAR(50) NOT NULL,
	DESCRIPTION VARCHAR(500)  NOT NULL,
	CREATIONDAY TIMESTAMP  NOT NULL,
	FINALDATE TIMESTAMP  NOT NULL,
	TOTALCOST DECIMAL(16,2)  NOT NULL,
	PRIORITY PRIORYTY,
	IDCLASSIFICATION INT  NOT NULL,
	IDCREATOR INT  NOT NULL,
	CONSTRAINT PK_IDINCIDENCE PRIMARY KEY(IDINCIDENCE),
	CONSTRAINT FK_IDCLASSIFICATION FOREIGN KEY(IDCLASSIFICATION) REFERENCES CLASSIFICATIONS(IDCLASSIFICATION),
	CONSTRAINT FK_IDCREATOR FOREIGN KEY(IDCREATOR) REFERENCES USERS(IDUSER)
);
CREATE TYPE NOTETYPE AS ENUM('Observacion', 'Rechazo');
CREATE TABLE NOTES(
	IDNOTA SERIAL  NOT NULL,
	DESCRIPTION VARCHAR(500)  NOT NULL,
	TYPE NOTETYPE NOT NULL,
	IDHOLDER INT  NOT NULL,
	IDINCIDENCE INT  NOT NULL,
	CONSTRAINT PK_IDNOTA PRIMARY KEY(IDNOTA),
	CONSTRAINT FK_IDHOLDER FOREIGN KEY(IDHOLDER) REFERENCES USERS(IDUSER),
	CONSTRAINT FK_INCIDENCE FOREIGN KEY(IDINCIDENCE) REFERENCES INCIDENCES(IDINCIDENCE)
); 
CREATE TYPE STATUS AS ENUM('Asignada','Aceptada','Rechazada','Finalizada');
CREATE TABLE INCIDENCEBYRECEPTOR(
	IDIBR SERIAL  NOT NULL,
	STARTDATE TIMESTAMP,
	FINALDATE TIMESTAMP,
	STATUS STATUS   NOT NULL,
	IDRECEPTOR INT  NOT NULL,
	IDINCIDENCE INT  NOT NULL,
	CONSTRAINT PK_IDIBR PRIMARY KEY(IDIBR),
	CONSTRAINT FK_IDRECEPTOR FOREIGN KEY(IDRECEPTOR) REFERENCES USERS(IDUSER),
	CONSTRAINT FK_INCIDENCE FOREIGN KEY(IDINCIDENCE) REFERENCES INCIDENCES(IDINCIDENCE)
);
CREATE TYPE MANAGEMENTTYPE AS ENUM('Correo','Adjunto','Procedimiento');
CREATE TABLE MANAGEMENT(
	IDMANAGEMENT SERIAL  NOT NULL,
	TITLE VARCHAR(50)  NOT NULL,
	DESCRIPTION VARCHAR(500)  NOT NULL,
	CORRECTIONDAY TIMESTAMP  NOT NULL,
	TYPE MANAGEMENTTYPE  NOT NULL,
	ATTACH VARCHAR(500),
	COST DECIMAL(16,2),
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

-- Correciones de la base de datos, quitar si se corrigen en las propias definciones
alter table incidences add column idDepto int;
alter table incidences add constraint fk_Depto foreign key(idDepto) references departments(iddepto);

-- Creacionde Menus 
insert into menus values(1,'Panel Principal',null,'/Principal',null);
insert into menus values(2,'Grupo',null,'/Grupo',null);
insert into menus values(3,'Empleados',null,'/Empleados',null);
insert into menus values(4,'Departamentos',null,'/Departamentos',null);
insert into menus values(5,'Clasificaciones',null,'/Clasificaciones',null);
insert into menus values(6,'Reportes',null,'/Reportes',null);
insert into menus values(7,'Todas',null,'/Incidencias',1);
insert into menus values(8,'En proceso',null,'/Incidencias',1);
insert into menus values(9,'Urgentes',null,'/Incidencias',1);
insert into menus values(10,'Finalizadas',null,'/Incidencias',1);

-- Asignado Permisos sobre roles por menus 
-- Menus sobre el Gerente idrol 1
insert into permissions(idmenu,idrole) select idMenu, 1 from menus
where idmenu in (1,3,4,5,6,7,8,9,10);
-- Menus sobre el Lider idrol 2 
insert into permissions(idmenu,idrole) select idMenu, 2 from menus
where idmenu in (1,2,6,8,9,10,11);
-- Menus sobre el Receptor idrol 3 
insert into permissions(idmenu,idrole) select idMenu, 3 from menus
where idmenu in (1,2,6,8,10,11);
-- Menus sobre el Empleado idrol 4 
insert into permissions(idmenu,idrole) values (1,4);

select * from permissions;
select * from users;

-- Consultar permisos de Menus por roles 
select * from menus where idMenu in (select idmenu from permissions where idrole = 4);

update users set idrole = 2 where username = 'MCNERIO98';


 




