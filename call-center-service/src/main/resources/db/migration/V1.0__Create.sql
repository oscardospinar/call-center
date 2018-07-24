CREATE TABLE employee (
  id character varying(50) NOT NULL,
  priority integer,
  in_a_call boolean,
  CONSTRAINT employee_pkey PRIMARY KEY (id)
)