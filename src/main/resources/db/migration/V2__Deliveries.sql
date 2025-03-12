create table delivery
(
    id         uuid primary key not null,
    course_id  uuid             not null,
    start_date date             not null
);

alter table delivery
    add foreign key (course_id)
        references course (id);

insert into delivery(id, course_id, start_date)
select uuid(), id, current_date + 3 day
from course
where course_id = 'KOTLIN';

insert into delivery(id, course_id, start_date)
select uuid(), id, current_date - 24 day
from course
where course_id = 'SPRINGB';

insert into delivery(id, course_id, start_date)
select uuid(), id, current_date + 7 day
from course
where course_id = 'SPRINGB';

insert into delivery(id, course_id, start_date)
select uuid(), id, current_date + 14 day
from course
where course_id = 'THYMEL';

insert into delivery(id, course_id, start_date)
select uuid(), id, current_date + 18 day
from course
where course_id = 'HTMX';
