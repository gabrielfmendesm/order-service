    CREATE TABLE orders (
        id_order      VARCHAR(36)    NOT NULL,
        id_account    VARCHAR(36)    NOT NULL,
        dt_date       TIMESTAMP      NOT NULL,
        nu_total      DECIMAL(10,2)  NOT NULL,
        CONSTRAINT pk_order PRIMARY KEY (id_order),
        CONSTRAINT fk_order_account FOREIGN KEY (id_account)
            REFERENCES account(id_account)
    );