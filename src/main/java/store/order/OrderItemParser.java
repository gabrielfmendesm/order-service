package store.order;

import store.order.OrderItem;
import store.order.OrderItemIn;
import store.order.OrderItemOut;
import store.product.ProductOut;

public class OrderItemParser {

    public static OrderItem to(OrderItemIn in) {
        return in == null ? null :
            OrderItem.builder()
                .product(
                    ProductOut.builder()
                        .id(in.idProduct())
                        .build()
                )
                .quantity(in.quantity())
                .build();
    }

    public static OrderItemOut to(OrderItem item) {
        return item == null ? null :
            OrderItemOut.builder()
                .id(item.id())
                .product(item.product())
                .quantity(item.quantity())
                .total(item.total())
                .build();
    }

}
