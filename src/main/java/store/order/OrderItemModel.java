package store.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import store.order.OrderItem;
import store.product.ProductOut;

@Entity
@Table(name = "orders_item")
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
public class OrderItemModel {

    @Id
    @Column(name = "id_item")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "id_order")
    private String orderId;

    @Column(name = "id_product")
    private String productId;

    @Column(name = "nu_quantity ")
    private Integer quantity;

    @Column(name = "nu_total")
    private Double total;

    public OrderItemModel(OrderItem i) {
        this.id = i.id();
        this.productId = i.product().id();
        this.orderId = i.order().id();
        this.quantity = i.quantity();
        this.total = i.total();
    }

    public OrderItem to() {
        return OrderItem.builder()
            .id(this.id)
            .product(
                ProductOut.builder()
                    .id(this.productId)
                    .build()
            )
            .order(
                Order.builder()
                    .id(this.orderId)
                    .build()
            )
            .quantity(this.quantity)
            .total(this.total)
            .build();
    }

}
