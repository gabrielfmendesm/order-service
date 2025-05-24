CREATE TABLE orders_item (
    id_item       VARCHAR(36)    NOT NULL,
    id_order      VARCHAR(36)    NOT NULL,
    id_product    VARCHAR(36)    NOT NULL,
    nu_quantity   INTEGER        NOT NULL,
    nu_total      DECIMAL(10,2)  NOT NULL,
    CONSTRAINT pk_order_item PRIMARY KEY (id_item),
    CONSTRAINT fk_item_order FOREIGN KEY (id_order)
        REFERENCES "order"(id_order),
    CONSTRAINT fk_item_product FOREIGN KEY (id_product)
        REFERENCES product(id_product)
);