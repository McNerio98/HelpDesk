



-- Insertando incidencias 
select * from users;

insert into incidences(title,description,creationday,finaldate,totalcost,priority,idclassification,idcreator,iddepto)values 
('AC Caido','le callo agua','2019-10-01','2019-10-15',0.0,'BAJA',3,2,4),
('Sobrecarga','Muchos libros','2019-05-07','2019-05-13',0.0,'ALTA',3,2,3),
('Sin cables','se rompieron','2019-06-08','2019-06-20',0.0,'MEDIA',1,3,2),
('Cable dañado','lo cortaron','2019-07-09','2019-07-28',0.0,'ALTA',1,2,2),
('Sin material','Se vencio','2019-08-15','2019-08-19',0.0,'BAJA',2,3,3),
('Pago de licencia','Fecha superada','2019-09-11','2019-09-14',0.0,'ALTA',3,1,1);



insert into incidences(title,description,creationday,finaldate,priority,idclassification,idcreator,iddepto)values 
('AC Caido','le callo agua',null,'2019-12-30',1,1,3,2);

select * from users;
-- Creando asignaciones para la parte del control 
insert into incidencebyreceptor(startdate,status,idreceptor,idincidence) values 
(current_timestamp,'Asignada',1,15);

update incidencebyreceptor set status = 'Aceptada' where idincidence = 15;

--Si esta aceptada entonces esta en ejecucion, realizar con una funcion 
select status from incidencebyreceptor where idincidence = 15 and status <> 'Rechazada';
-- Estados que podria devolver n:n ; Asignado, Aceptada, finalizada
-- Luego lo paso por un swtich, aceptada igual a ejecucion




--Como saber si son del mismo departamento 
select * from deptobyusers;
select * from users;

select count(iddepto) from deptobyusers where iddepto =
(select iddepto from deptobyusers where iduser = 1);

select count(iddepto) from deptobyusers where iduser =  or iduser = 2;


select * from deptobyusers where iddepto = 
(select iddepto from deptobyusers where iduser = 2) and iduser = 1;

select iddepto from deptobyusers where iduser = 2;
select count(iddepto) from deptobyusers where iddepto  = 
(select iddepto where iduser = 1 or iduser = 3);

select * from incidences where creationday between '2019-06-01' and '2019-08-20';
select count(*) from incidences where priority = 'BAJA';

select * from users where idrole = 4;




delete from incidencebyreceptor;
delete from incidences;
select * from users;

update users set idrole = 1 where username = 'MCNERIO98';
update users set idrole = 2 where username = 'ROGER85';
update users set idrole = 3 where username = 'ALUX75';
update users set idrole = 3 where username = 'NOVA94';

update users set idrole = 2 where username = 'FORC36';
update users set idrole = 3 where username = 'LINN45';
update users set idrole = 3 where username = 'CHARLY3';

select iduser from users where username = 'LINN45';

select * from departments;
select * from incidences;

select * from deptobyusers where iddepto = (select iddepto from deptobyusers where iduser = 5) and iduser = 7;

select * from        incidences;




-- Verificar esta cuando hallan muchos datos 


select d.deptoname,i.title,i.description, inc.status, i.creationday, i.priority,
u.username, cl.classification, i.totalcost, dp.iddepto, inc.idreceptor from 
incidencebyreceptor inc, incidences i, departments d, users u, classifications cl, 
deptobyusers dp where inc.idincidence = i.idincidence and i.iddepto = d.iddepto 
and u.iduser = i.idcreator and cl.idclassification = i.idclassification and 
dp.iduser = inc.idreceptor and i.idincidence = 18 and inc.idibr = 7;



select ibr.idibr, u.username, ibr.status, i.creationday, ibr.startdate, 
i.finaldate, ibr.finaldate  
from users u, incidencebyreceptor ibr, incidences i
where ibr.idreceptor = u.iduser and 
ibr.idincidence = i.idincidence and ibr.idincidence = 18;


--Extraer notas de una unica incidencia 
select * from notes;
select * from users;

select CONCAT(u.firstname,' ',u.lastname) as fullname,
r.rolename, n.description, n.notetype
from roles r, users u, notes n
where n.idholder = u.iduser and u.idrole = r.idrol and 
n.idincidence = 23;


select count(*) from incidencebyreceptor where status = 3 and idreceptor = 29;


select * from incidences;
select * from incidencebyreceptor;
select * from users;
select * from deptobyusers;
select iddepto from deptobyusers where iduser = 5;
select * from permissions;

select iduser from users where username ='CHARLY3';

insert into incidencebyreceptor(status,idreceptor,idincidence)
values(2,8,1);



select count(*) from pg_stat_activity where client_addr = '127.0.0.1';


select typemanagement,title,description,correctionday,attachfile,costmsg from managements where idibr = 11;

select * from managements;

select typemanagement,title,description,correctionday,attachfile,costmsg,idmanagement from managements where idibr =4 order by idmanagement desc;
