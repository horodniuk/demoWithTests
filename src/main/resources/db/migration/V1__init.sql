-- Create the sequence for users table
CREATE SEQUENCE users_id_seq;

-- Create the sequence for addresses table
CREATE SEQUENCE addresses_id_seq;


create table public.users (
                              id integer primary key not null default nextval('users_id_seq'::regclass),
                              name character varying,
                              email character varying,
                              country character varying,
                              is_deleted boolean
);
create unique index users_id_uindex on users using btree (id);

create table public.addresses (
                                  id bigint primary key not null default nextval('addresses_id_seq'::regclass),
                                  address_has_active boolean,
                                  city character varying(255),
                                  country character varying(255),
                                  street character varying(255),
                                  employee_id integer,
                                  foreign key (employee_id) references public.users (id)
                                      match simple on update no action on delete no action
);



