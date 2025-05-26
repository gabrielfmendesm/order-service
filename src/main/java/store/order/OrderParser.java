package store.order;

import java.text.SimpleDateFormat;

public class OrderParser {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Order to(OrderIn in) {
        return in == null ? null :
            Order.builder()
                .items(
                    in.items().stream()
                        .map(OrderItemParser::to)
                        .toList()
                )
                .build();
    }

    public static OrderOut to(Order o) {
        return o == null ? null :
            OrderOut.builder()
                .id(o.id())
                .date(sdf.format(o.date()))
                .items(
                    o.items().stream()
                        .map(OrderItemParser::to)
                        .toList()
                )
                .total(o.total())
                .build();
    }

}
