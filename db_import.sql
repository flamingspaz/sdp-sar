SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET default_tablespace = '';

CREATE TABLE users (
    id serial primary key NOT NULL,
    name character varying(255) NOT NULL,
    surname character varying(255) NOT NULL,
    dob date NOT NULL,
    photograph bytea,
    tempreg boolean DEFAULT true NOT NULL,
    permreg boolean DEFAULT false NOT NULL,
    programme_id integer,
    teacher boolean DEFAULT false NOT NULL,
    leader boolean DEFAULT false NOT NULL,
    username character varying(60) NOT NULL,
    password_hash character varying(64) NOT NULL
);

CREATE TABLE programmes (
    id serial primary key NOT NULL,
    name character varying(255) NOT NULL,
    leader_id integer NOT NULL references users(id),
    active boolean DEFAULT false NOT NULL,
    startDate date,
    duration integer
);

ALTER TABLE users
    ADD FOREIGN KEY (programme_id)
    REFERENCES users(id);

CREATE TABLE courses (
    id serial primary key NOT NULL,
    name character varying(255) NOT NULL,
    leader_id integer NOT NULL references users(id),
    programme_id integer NOT NULL references programmes(id),
    active boolean DEFAULT false NOT NULL,
    startDate date,
    duration integer,
    credits integer
);

CREATE TABLE session_types (
    id serial primary key NOT NULL,
    name character varying(10) NOT NULL
);

CREATE TABLE locations (
    id serial primary key NOT NULL,
    room character varying(32) NOT NULL,
    size integer NOT NULL
);

CREATE TABLE sessions (
    id serial primary key NOT NULL,
    teacher_id integer NOT NULL references users(id),
    course_id integer NOT NULL references courses(id),
    session_type_id integer NOT NULL references session_types(id),
    cancelled boolean DEFAULT false NOT NULL,
    sessionDate timestamp NOT NULL,
    location_id integer NOT NULL references locations(id),
    duration integer NOT NULL
);

CREATE TABLE attendances (
    id serial primary key NOT NULL,
    student_id integer NOT NULL references users(id),
    session_id integer NOT NULL references sessions(id),
    attended boolean DEFAULT false NOT NULL
);

ALTER SEQUENCE attendances_id_seq OWNED BY attendances.id;
