CREATE TABLE orders (
  id_order      VARCHAR(36)    NOT NULL,
  id_account    VARCHAR(36)    NOT NULL,
  date_order    TIMESTAMP      NOT NULL,
  nu_total      DECIMAL(10,2)  NOT NULL,
  CONSTRAINT pk_order PRIMARY KEY (id_order)
);