
create table publisher (
   id bigint generated by default as identity,
    created timestamp not null,
    updated timestamp not null,
    uuid varchar(36) not null,
    email varchar(255) not null,
    name varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
)

create table question (
   id bigint generated by default as identity,
    created timestamp not null,
    updated timestamp not null,
    uuid varchar(36) not null,
    question_options varchar(2048),
    position integer not null,
    question varchar(255) not null,
    required boolean not null,
    survey_id bigint,
    primary key (id)
)

create table question_response (
   id bigint generated by default as identity,
    created timestamp not null,
    updated timestamp not null,
    uuid varchar(36) not null,
    question varchar(255) not null,
    question_uuid varchar(36) not null,
    response varchar(255),
    survey_response_id bigint,
    primary key (id)
)

create table survey (
   id bigint generated by default as identity,
    created timestamp not null,
    updated timestamp not null,
    uuid varchar(36) not null,
    description varchar(1024),
    title varchar(255) not null,
    publisher_id bigint,
    primary key (id)
)

create table survey_response (
   id bigint generated by default as identity,
    created timestamp not null,
    updated timestamp not null,
    uuid varchar(36) not null,
    email varchar(255) not null,
    name varchar(255) not null,
    survey_id bigint,
    primary key (id)
)
create index IDXtq31gshjc2w4bjif7cw51o25 on publisher (email)

alter table publisher
   add constraint UK_ne08vnrxg9vglclnd8edu96uo unique (uuid)

alter table publisher
   add constraint UK_tq31gshjc2w4bjif7cw51o25 unique (email)

alter table question
   add constraint UK_2e56bvolny4xsvkxy9m6fj8u unique (uuid)

alter table question_response
   add constraint UK_38a8627nx6hl9m03sir6faq2 unique (uuid)

alter table survey
   add constraint UK_o3oogw7eyjdqguyirk8mrtoet unique (uuid)

alter table survey_response
   add constraint UK_3idkjkjofxst9p3bwxwbfm12h unique (uuid)

alter table question
   add constraint FK65ro96jafjvulbqu8ia0pb254
   foreign key (survey_id)
   references survey

alter table question_response
   add constraint FKjpfnewfr9h3p3kye2o3hep65v
   foreign key (survey_response_id)
   references survey_response

alter table survey
   add constraint FKe5gclgcvi39ats108upv3qvgc
   foreign key (publisher_id)
   references publisher

alter table survey_response
   add constraint FK84qtox6878n0fh337ent4mwgo
   foreign key (survey_id)
   references survey
