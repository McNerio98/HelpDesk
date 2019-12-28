--Insertar dos usuarios con los siguientes userName para luego acenderlos 
--MCNERIO98	ax.minck@gmail.com		Mario Nerio			Redes
--ROGER85 	jm.roger@gmail.com 		Jorge Mundo   		Redes
--ALUX75 	ale.75@hotmail.com 		Alejandro Eduardo 	Tecnologia
--NOVA94 	nov.rar@hotmail.com 	Santiago Smith 		Diseño

--Asendiendo 
update users set idrole = 1 where username = 'ROGER85'; --Gerente 
update users set idrole = 2 where username = 'ALUX75'; -- Lider 
update users set idrole = 3 where username = 'NOVA94'; -- Receptor 
update users set idrole = 4 where username = 'MCNERIO98'; --Empleado

--ARREGLAR ESTAS INSERCIONES AL AGREGAR SERIAL Y OMITIR ID EXPLICITO 
--Creando algunas Clasificaciones, arrelgar y poner el serial y quitar las primarys de aqui 
insert into classifications values(1,'FALLA DE RED','intercambio de datos');
insert into classifications values(2,'ROBO','Perdida de patrimonio que afecta el proceso');
insert into classifications values(3,'VULNERABILIDAD','Acceso vulnerable a datos');
insert into classifications values(4,'ENERGIA','Falla en suministro de electricidad');


-- Esto ya estubo, ignorar 
alter table incidences add column idDepto int;
alter table incidences add constraint fk_depto foreign key(iddepto) references departments(iddepto);

-- Insertando incidencias 
select * from users;

insert into incidences(title,description,creationday,finaldate,totalcost,priority,idclassification,idcreator,iddepto)values 
('AC Caido','le callo agua','2019-10-01','2019-10-15',0.0,'BAJA',3,2,4),
('Sobrecarga','Muchos libros','2019-05-07','2019-05-13',0.0,'ALTA',3,2,3),
('Sin cables','se rompieron','2019-06-08','2019-06-20',0.0,'MEDIA',1,3,2),
('Cable dañado','lo cortaron','2019-07-09','2019-07-28',0.0,'ALTA',1,2,2),
('Sin material','Se vencio','2019-08-15','2019-08-19',0.0,'BAJA',2,3,3),
('Pago de licencia','Fecha superada','2019-09-11','2019-09-14',0.0,'ALTA',3,1,1);



insert into incidences(title,description,finaldate,totalcost,priority,idclassification,idcreator,iddepto)values 
('AC Caido','le callo agua','2019-12-30',0.0,'BAJA',3,12,4);

select * from incidences;
-- Creando asignaciones para la parte del control 
insert into incidencebyreceptor(startdate,status,idreceptor,idincidence) values 
(current_timestamp,'Asignada',1,15);

update incidencebyreceptor set status = 'Aceptada' where idincidence = 15;

--Si esta aceptada entonces esta en ejecucion, realizar con una funcion 
select status from incidencebyreceptor where idincidence = 15 and status <> 'Rechazada';
-- Estados que podria devolver n:n ; Asignado, Aceptada, finalizada
-- Luego lo paso por un swtich, aceptada igual a ejecucion





select * from incidences;
select * from incidences where creationday between '2019-06-01' and '2019-08-20';
select count(*) from incidences where priority = 'BAJA';

select * from users where idrole = 4;





