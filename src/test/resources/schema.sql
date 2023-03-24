-- Project Name : noname
-- Date/Time    : 2018/02/01 15:42:46
-- Author       : y.kimura
-- RDBMS Type   : Oracle Database
-- Application  : A5:SQL Mk-2

-- category
create table category (
   id serial not null
  , parent integer
  , name character varying(255)
  ,name_all character varying(255)
  , constraint category_PKC primary key (id)
) ;

create unique index category_pki
  on category(id);

create index parent_id_index
  on category(parent);

-- items
create table items (
  id serial not null
  , name character varying(255)
  , condition integer
  , category integer
  , brand character varying(255)
  , price double precision
  , shipping integer
  , description text
  , constraint items_PKC primary key (id)
) ;

create index items_brand_index
  on items(brand);

create index items_category_index
  on items(category);

create unique index items_pki
  on items(id);

-- original
create table original (
  id integer not null
  , name character varying(255)
  , condition_id integer
  , category_name character varying(255)
  , brand character varying(255)
  , price double precision
  , shipping integer
  , description text
  , constraint original_PKC primary key (id)
) ;

create index brand_index
  on original(brand);

create unique index original_pki
  on original(id);

-- users
create table users (
  id serial not null
  , name varchar(255)
  , password varchar(255)
  , authority integer
  , constraint users_PKC primary key (id)
) ;

create unique index users_pki
  on users(id);
