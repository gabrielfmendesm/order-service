package store.order;

import java.util.Date;
import java.util.List;
import store.product.ProductController;

public class OrderParser {

    public static Order toDomain(OrderIn in, String accountId, ProductController prodClient) {
        Date now = new Date();
        List<OrderItem> items = in.items().stream()
            .map(i -> {
                var prod = prodClient.findById(i.idProduct()).getBody();
                double lineTotal = prod.price() * i.quantity();
                return OrderItem.builder()
                    .productId(i.idProduct())
                    .quantity(i.quantity())
                    .total(lineTotal)
                    .build();
            })
            .toList();
        double total = items.stream().mapToDouble(OrderItem::total).sum();
        return Order.builder()
            .accountId(accountId)
            .date(now)
            .total(total)
            .items(items)
            .build();
    }

    public static OrderOut toOut(Order o) {
        var itemsOut = o.items().stream()
            .map(it -> OrderItemOut.builder()
                .id(it.id())
                .product(new ProductRef(it.productId()))
                .quantity(it.quantity())
                .total(it.total())
                .build()
            )
            .toList();

        return OrderOut.builder()
            .id(o.id())
            .date(o.date())
            .items(itemsOut)
            .total(o.total())
            .build();
    }

}
