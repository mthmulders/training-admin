create table course
(
    id        uuid primary key not null,
    course_id varchar          not null,
    name      varchar          not null
);

alter table course
    add constraint course_id_uq unique (course_id);

insert into course(id, course_id, name)
values (uuid(), 'SPRINGB', 'Spring Boot'),
       (uuid(), 'THYMEL', 'Thymeleaf'),
       (uuid(), 'HTMX', 'htmx'),
       (uuid(), 'KOTLIN', 'Kotlin');
