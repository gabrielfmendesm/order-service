package store.order;

import java.util.ArrayList;
import java.util.Date;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import store.account.AccountOut;

@Entity
@Table(name = "orders")
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
public class OrderModel {

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "id_account")
    private String accountId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_order")
    private Date date;

    @Column(name = "nu_total")
    private Double total;

    public OrderModel(Order order) {
        this.id = order.id();
        this.accountId = order.account().id();
        this.date = order.date();
        this.total = order.total();
    }

    public Order to() {
        return Order.builder()
            .id(this.id)
            .account(
                AccountOut.builder().id(this.accountId).build()
            )
            .date(this.date)
            .total(this.total)
            .items(new ArrayList<>())
            .build();
    }

}
