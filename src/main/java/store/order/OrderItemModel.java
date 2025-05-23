package store.order;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "order_item")
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
public class OrderItemModel {

    @Id
    @Column(name = "id_item")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false)
    private OrderModel orderModel;

    @Column(name = "id_product", nullable = false)
    private String productId;

    @Column(name = "nu_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "nu_total", nullable = false)
    private Double total;

    public OrderItemModel(OrderItem it) {
        this.productId = it.productId();
        this.quantity  = it.quantity();
        this.total     = it.total();
    }

    public OrderItem to() {
        return OrderItem.builder()
            .id(this.id)
            .productId(this.productId)
            .quantity(this.quantity)
            .total(this.total)
            .build();
    }

}
