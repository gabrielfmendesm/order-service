package store.order;

import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "order")
@NoArgsConstructor
@Data 
@Accessors(fluent = true)
public class OrderModel {

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "id_account", nullable = false)
    private String accountId;

    @Column(name = "dt_date", nullable = false)
    private Date date;

    @Column(name = "nu_total", nullable = false)
    private Double total;

    @OneToMany(
        mappedBy = "orderModel",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<OrderItemModel> items;

    public OrderModel(Order o) {
        this.accountId = o.accountId();
        this.date      = o.date();
        this.total     = o.total();
        this.items     = o.items().stream().map(it -> {
            var m = new OrderItemModel(it);
            m.orderModel(this);
            return m;
        }).toList();
    }

    public Order to() {
        return Order.builder()
            .id(this.id)
            .accountId(this.accountId)
            .date(this.date)
            .total(this.total)
            .items(this.items.stream().map(OrderItemModel::to).toList())
            .build();
    }

}
