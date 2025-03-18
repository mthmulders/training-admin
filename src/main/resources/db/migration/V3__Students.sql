create table student
(
    id         uuid primary key not null,
    student_id bigint           not null generated by default as identity(start with 1000 increment by 1),
    last_name  varchar          not null,
    first_name varchar          not null,
    email      varchar          not null
);

alter table student
    add constraint student_id_uq unique (student_id);

insert into student(id, last_name, first_name, email)
values
    (uuid(), 'Lockman', 'Shelli', 'shelli.lockman@gleason-parisian.name'),
    (uuid(), 'Cruickshank', 'Kathline', 'kathline.cruickshank@muller-erdman.name'),
    (uuid(), 'Wolf', 'Luke', 'luke.wolf@schmitt-pollich.io'),
    (uuid(), 'Witting', 'Aaron', 'aaron.witting@schroederritchieandquitzon.org'),
    (uuid(), 'Lynch', 'Oneida', 'oneida.lynch@christiansenwillmsandsipes.net'),
    (uuid(), 'Orn', 'Dean', 'dean.orn@dickiandsons.com'),
    (uuid(), 'Wyman', 'Kim', 'kim.wyman@nitzscheschinnerandkris.org'),
    (uuid(), 'Carroll', 'Bari', 'bari.carroll@crona-kris.org'),
    (uuid(), 'Lockman', 'Judson', 'judson.lockman@nicolasschneiderandbergnaum.com'),
    (uuid(), 'Streich', 'Craig', 'craig.streich@weberllc.info'),
    (uuid(), 'Schinner', 'Carson', 'carson.schinner@fritschinc.net'),
    (uuid(), 'Rempel', 'Jamar', 'jamar.rempel@abernathyhowellandcole.net'),
    (uuid(), 'Upton', 'Osvaldo', 'osvaldo.upton@lockman-rath.info'),
    (uuid(), 'Wehner', 'Bert', 'bert.wehner@marksgreenholtandwehner.net'),
    (uuid(), 'Raynor', 'Simone', 'simone.raynor@kuphalgroup.net'),
    (uuid(), 'Gislason', 'Rolando', 'rolando.gislason@kertzmann-moore.biz'),
    (uuid(), 'Ward', 'Lovetta', 'lovetta.ward@shanahan-bradtke.biz'),
    (uuid(), 'Collins', 'Tatiana', 'tatiana.collins@gottlieb-prohaska.info'),
    (uuid(), 'Wilderman', 'Kayce', 'kayce.wilderman@mayergroup.io'),
    (uuid(), 'Christiansen', 'Kit', 'kit.christiansen@bednarrogahnandondricka.biz'),
    (uuid(), 'Kuphal', 'Angelyn', 'angelyn.kuphal@braun-strosin.info'),
    (uuid(), 'Bednar', 'Bernie', 'bernie.bednar@lebsack-nitzsche.io'),
    (uuid(), 'Amore', 'Lloyd', 'lloyd.amore@johnston-pagac.info'),
    (uuid(), 'Herman', 'Danika', 'danika.herman@johns-legros.co'),
    (uuid(), 'Senger', 'Mickie', 'mickie.senger@berge-gottlieb.net');